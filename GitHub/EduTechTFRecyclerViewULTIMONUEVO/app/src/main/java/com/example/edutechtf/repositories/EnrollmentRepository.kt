package com.example.edutechtf.repositories

import com.example.edutechtf.data.Enrollment
import com.example.edutechtf.exceptions.CursoLlenoExcepcion

object EnrollmentRepository {
    private val inscripciones = mutableListOf<Enrollment>()
    init {
        inscripciones.add(Enrollment(1L, 1510L, mutableListOf(1L, 3L, 5L)))
        inscripciones.add(Enrollment(2L, 1504L, mutableListOf(2L, 4L, 6L)))
        inscripciones.add(Enrollment(3L, 2802L, mutableListOf(1L, 5L, 7L)))
    }

    fun obtenerPorIdUsuario(idUsuario: Long): Enrollment? =
        inscripciones.find { it.userId == idUsuario }

    fun agregarCursoAUsuario(idUsuario: Long, idsCursos: List<Long>) {
        for (idCurso in idsCursos){
            val curso= CourseRepository.obtenerPorId(idCurso)
            if (curso !=null ){
                val inscriptosActuales= contarInscriptosPorCurso(idCurso)
                if (inscriptosActuales >= curso.capacity){
                    throw CursoLlenoExcepcion("El curso ${curso.name} alcanzo su limite de capacidad")
                }
            }
        }
        val inscripcionUsuario = obtenerPorIdUsuario(idUsuario)
        if (inscripcionUsuario != null) {
            val cursosNuevos = idsCursos.filter { it !in inscripcionUsuario.coursesIds }
            inscripcionUsuario.coursesIds.addAll(cursosNuevos)
        } else {
            val nuevoId = (inscripciones.maxOfOrNull { it.id } ?: 0L) + 1L
            inscripciones.add(Enrollment(nuevoId, idUsuario, idsCursos.distinct().toMutableList()))
        }
    }

    fun contarInscriptosPorCurso(idCurso: Long): Int =
        inscripciones.sumOf { inscripcion ->
            inscripcion.coursesIds.count { it == idCurso }
        }
}