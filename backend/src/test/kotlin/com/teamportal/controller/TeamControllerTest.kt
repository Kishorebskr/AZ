package com.teamportal.controller

import com.teamportal.service.TeamService
import com.teamportal.service.TeamStatsDTO
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TeamController::class)
class TeamControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var teamService: TeamService

    @Test
    fun `GET team stats returns 200 with stats`() {
        `when`(teamService.getTeamStats("alpha")).thenReturn(
            TeamStatsDTO(members = 5, activeProjects = 3, completedThisMonth = 10, efficiency = 90)
        )

        mockMvc.perform(get("/api/teams/alpha/stats"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.members").value(5))
            .andExpect(jsonPath("$.activeProjects").value(3))
            .andExpect(jsonPath("$.completedThisMonth").value(10))
            .andExpect(jsonPath("$.efficiency").value(90))
    }
}
