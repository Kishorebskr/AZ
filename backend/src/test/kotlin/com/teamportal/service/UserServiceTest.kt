package com.teamportal.service

import com.teamportal.model.Activity
import com.teamportal.repository.ActivityRepository
import com.teamportal.repository.UserRepository
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
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var activityRepository: ActivityRepository

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `getUserActivities returns mapped DTOs for user`() {
        val timestamp = LocalDateTime.of(2024, 3, 5, 14, 0, 0)
        `when`(activityRepository.findByUserId(1L)).thenReturn(
            listOf(Activity(id = 2, action = "Reviewed PR", timestamp = timestamp))
        )

        val result = userService.getUserActivities(1L)

        assertEquals(1, result.size)
        assertEquals(2L, result[0].id)
        assertEquals("Reviewed PR", result[0].action)
        assertEquals("3/5/2024, 2:00:00 PM", result[0].timestamp)
    }

    @Test
    fun `getUserActivities returns empty list when user has no activities`() {
        `when`(activityRepository.findByUserId(99L)).thenReturn(emptyList())

        val result = userService.getUserActivities(99L)

        assertTrue(result.isEmpty())
    }
}
