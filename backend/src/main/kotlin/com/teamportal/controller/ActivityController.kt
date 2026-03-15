package com.teamportal.controller

import com.teamportal.service.ActivityDTO
import com.teamportal.service.ActivityService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activities")
class ActivityController(
    private val activityService: ActivityService
) {
    @GetMapping("/feed")
    fun getFeedActivities(): List<ActivityDTO> {
        return activityService.getFeedActivities()
    }
}
