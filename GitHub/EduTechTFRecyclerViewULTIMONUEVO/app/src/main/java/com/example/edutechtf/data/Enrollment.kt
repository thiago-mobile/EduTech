package com.example.edutechtf.data

data class Enrollment(
    val id: Long,
    val userId: Long,
    val coursesIds: MutableList<Long>
)
