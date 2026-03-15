package com.teamportal.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val email: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    val team: Team? = null,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val activities: List<Activity> = emptyList()
)
