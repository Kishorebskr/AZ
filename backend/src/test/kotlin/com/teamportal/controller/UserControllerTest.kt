package com.teamportal.controller

import com.teamportal.service.ActivityDTO
import com.teamportal.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `GET user activities returns 200 with activities`() {
        `when`(userService.getUserActivities(1L)).thenReturn(
            listOf(ActivityDTO(id = 2, action = "Reviewed PR", timestamp = "3/5/2024, 2:00:00 PM"))
        )

        mockMvc.perform(get("/api/users/1/activities"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(2))
            .andExpect(jsonPath("$[0].action").value("Reviewed PR"))
            .andExpect(jsonPath("$[0].timestamp").value("3/5/2024, 2:00:00 PM"))
    }

    @Test
    fun `GET user activities returns empty array when user has no activities`() {
        `when`(userService.getUserActivities(99L)).thenReturn(emptyList())

        mockMvc.perform(get("/api/users/99/activities"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isEmpty)
    }
}
