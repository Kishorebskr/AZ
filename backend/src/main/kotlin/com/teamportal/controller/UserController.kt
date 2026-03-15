package com.teamportal.controller

import com.teamportal.service.ActivityDTO
import com.teamportal.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}/activities")
    fun getUserActivities(@PathVariable id: Long): List<ActivityDTO> {
        return userService.getUserActivities(id)
    }
}
