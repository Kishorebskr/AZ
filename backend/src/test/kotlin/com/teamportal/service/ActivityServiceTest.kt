package com.teamportal.service

import com.teamportal.model.Activity
import com.teamportal.repository.ActivityRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ActivityServiceTest {

    @Mock
    private lateinit var activityRepository: ActivityRepository

    @InjectMocks
    private lateinit var activityService: ActivityService

    @Test
    fun `getFeedActivities returns mapped DTOs`() {
        val timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0)
        `when`(activityRepository.findAll()).thenReturn(
            listOf(Activity(id = 1, action = "Completed task", timestamp = timestamp))
        )

        val result = activityService.getFeedActivities()

        assertEquals(1, result.size)
        assertEquals(1L, result[0].id)
        assertEquals("Completed task", result[0].action)
        assertEquals("1/15/2024, 10:30:00 AM", result[0].timestamp)
    }

    @Test
    fun `getFeedActivities returns empty list when no activities exist`() {
        `when`(activityRepository.findAll()).thenReturn(emptyList())

        val result = activityService.getFeedActivities()

        assertTrue(result.isEmpty())
    }
}
