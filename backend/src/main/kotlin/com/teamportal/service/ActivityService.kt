package com.teamportal.service

import com.teamportal.repository.ActivityRepository
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

data class ActivityDTO(
    val id: Long,
    val action: String,
    val timestamp: String,
    val userName: String? = null
)

@Service
class ActivityService(
    private val activityRepository: ActivityRepository
) {
    private val formatter = DateTimeFormatter.ofPattern("M/d/yyyy, h:mm:ss a")

    fun getFeedActivities(): List<ActivityDTO> {
        val activities = activityRepository.findAll()

        return activities.map { activity ->
            ActivityDTO(
                id = activity.id,
                action = activity.action,
                timestamp = activity.timestamp.format(formatter),
                userName = activity.user?.name
            )
        }
    }
}
