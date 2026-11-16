package com.example.edutechtf.repositories

import com.example.edutechtf.data.EnrolledCourse

object EnrolledCourseRepository {
    private val cursosInscriptos = mutableListOf<EnrolledCourse>()
    init {
        cursosInscriptos.add(EnrolledCourse(1L, 4L, 60, "Presencial"))
        cursosInscriptos.add(EnrolledCourse(2L, 3L, 45, "Virtual"))
        cursosInscriptos.add(EnrolledCourse(3L, 1L, 60, "Presencial"))
        cursosInscriptos.add(EnrolledCourse(4L, 5L, 90, "Presencial"))
        cursosInscriptos.add(EnrolledCourse(5L, 2L, 60, "Virtual"))
        cursosInscriptos.add(EnrolledCourse(6L, 6L, 45, "Presencial"))
        cursosInscriptos.add(EnrolledCourse(7L, 7L, 60, "Virtual"))
    }
    fun obtenerTodos(): List<EnrolledCourse> = cursosInscriptos

    fun obtenerPorIdCurso(idCurso: Long): EnrolledCourse? =
        cursosInscriptos.find { it.courseId == idCurso }
}