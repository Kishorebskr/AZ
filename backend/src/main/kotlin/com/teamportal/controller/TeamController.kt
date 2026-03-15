package com.teamportal.controller

import com.teamportal.service.TeamService
import com.teamportal.service.TeamStatsDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/teams")
class TeamController(
    private val teamService: TeamService
) {
    @GetMapping("/{team}/stats")
    fun getTeamStats(@PathVariable team: String): TeamStatsDTO {
        return teamService.getTeamStats(team)
    }
}
