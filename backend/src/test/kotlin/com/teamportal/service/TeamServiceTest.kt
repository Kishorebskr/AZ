package com.teamportal.service

import com.teamportal.model.Team
import com.teamportal.repository.TeamRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class TeamServiceTest {

    @Mock
    private lateinit var teamRepository: TeamRepository

    @InjectMocks
    private lateinit var teamService: TeamService

    @Test
    fun `getTeamStats returns stats when team exists`() {
        val team = Team(id = 1, name = "alpha", members = 5, activeProjects = 3, completedThisMonth = 10, efficiency = 90)
        `when`(teamRepository.findByNameIgnoreCase("alpha")).thenReturn(team)

        val stats = teamService.getTeamStats("alpha")

        assertEquals(5, stats.members)
        assertEquals(3, stats.activeProjects)
        assertEquals(10, stats.completedThisMonth)
        assertEquals(90, stats.efficiency)
    }

    @Test
    fun `getTeamStats throws RuntimeException when team not found`() {
        `when`(teamRepository.findByNameIgnoreCase("unknown")).thenReturn(null)

        assertThrows<RuntimeException> {
            teamService.getTeamStats("unknown")
        }
    }
}
