package com.teamportal.repository

import com.teamportal.model.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : JpaRepository<Activity, Long> {
    fun findByUserId(userId: Long): List<Activity>
}
