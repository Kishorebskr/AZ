package com.teamportal.controller

import com.teamportal.service.ActivityDTO
import com.teamportal.service.ActivityService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ActivityController::class)
class ActivityControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var activityService: ActivityService

    @Test
    fun `GET feed returns 200 with activities`() {
        `when`(activityService.getFeedActivities()).thenReturn(
            listOf(ActivityDTO(id = 1, action = "Completed task", timestamp = "1/15/2024, 10:30:00 AM"))
        )

        mockMvc.perform(get("/api/activities/feed"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].action").value("Completed task"))
            .andExpect(jsonPath("$[0].timestamp").value("1/15/2024, 10:30:00 AM"))
    }

    @Test
    fun `GET feed returns empty array when no activities exist`() {
        `when`(activityService.getFeedActivities()).thenReturn(emptyList())

        mockMvc.perform(get("/api/activities/feed"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isEmpty)
    }
}
