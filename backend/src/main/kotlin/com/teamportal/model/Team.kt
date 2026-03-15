package com.teamportal.model

import jakarta.persistence.*

@Entity
@Table(name = "teams")
data class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val members: Int = 0,

    @Column(name = "active_projects", nullable = false)
    val activeProjects: Int = 0,

    @Column(name = "completed_this_month", nullable = false)
    val completedThisMonth: Int = 0,

    @Column(nullable = false)
    val efficiency: Int = 0,

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    val users: MutableList<User> = mutableListOf()
)
