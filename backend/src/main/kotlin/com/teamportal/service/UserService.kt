package com.teamportal.service

import com.teamportal.repository.ActivityRepository
import com.teamportal.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class UserService(
    private val userRepository: UserRepository,
    private val activityRepository: ActivityRepository
) {
    private val formatter = DateTimeFormatter.ofPattern("M/d/yyyy, h:mm:ss a")

    fun getUserActivities(userId: Long): List<ActivityDTO> {
        val activities = activityRepository.findByUserId(userId)

        return activities.map { activity ->
            ActivityDTO(
                id = activity.id,
                action = activity.action,
                timestamp = activity.timestamp.format(formatter)
            )
        }
    }
}
