package com.teamportal.repository

import com.teamportal.model.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository : JpaRepository<Team, Long> {
    fun findByNameIgnoreCase(name: String): Team?
}
