package com.example.edutechtf.repositories

import com.example.edutechtf.data.Course
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Course.yaFinalizo(): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fechaCurso = LocalDate.parse(this.date, formatter)
        val hoy = LocalDate.now()
        fechaCurso.isBefore(hoy)
    } catch (e: Exception) {
        false
    }
}

object CourseRepository {

    const val COSTO_CURSO = 10000.0

    private val cursos = mutableListOf<Course>()

    init {
        cursos.add(Course(1L, "Desarrollo Web", "2026-10-02", "21:00", "Online", "Juan Perez", "https://images.unsplash.com/photo-1498050108023-c5249f4df085?auto=format&fit=crop&w=768&q=80", 20))
        cursos.add(Course(2L, "Programacion con Kotlin", "2026-10-15", "20:00", "Campus Belgrano", "Maria Garcia", "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=768&q=80", 15))
        cursos.add(Course(3L, "Diseno UX/UI", "2026-11-05", "18:00", "Online", "Carlos Sanchez", "https://images.unsplash.com/photo-1561070791-2526d30994b5?auto=format&fit=crop&w=768&q=80", 25))
        cursos.add(Course(4L, "Introduccion a Python", "2026-11-16", "20:00", "Campus Caballito", "Lucia Gomez", "https://images.unsplash.com/photo-1526379095098-d400fd0bf935?auto=format&fit=crop&w=768&q=80", 30))
        cursos.add(Course(5L, "Marketing Digital", "2026-09-21", "19:00", "Online", "Ricardo Fort", "https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=768&q=80", 50))
        cursos.add(Course(6L, "Inteligencia Artificial", "2026-11-09", "21:00", "Campus Palermo", "Elena White", "https://images.unsplash.com/photo-1677442136019-21780ecad995?auto=format&fit=crop&w=768&q=80", 12))
        cursos.add(Course(7L, "Bases de Datos", "2026-12-01", "20:00", "Online", "Arnold Schwarzenegger", "https://images.unsplash.com/photo-1544383835-bda2bc66a55d?auto=format&fit=crop&w=768&q=80", 40))
    }

    fun obtenerTodos(): List<Course> = cursos

    fun obtenerPorId(id: Long): Course? = cursos.find { it.id == id }

    fun obtenerCursosCompletados(idUsuario: Long): List<Course> {
        val inscripciones = EnrollmentRepository.obtenerPorIdUsuario(idUsuario)
            ?: return emptyList()

        return cursos.filter { curso ->
            inscripciones.coursesIds.contains(curso.id)
        }
    }
    fun obtenerCursosActivos(idUsuario: Long): List<Course> {
        val inscripciones = EnrollmentRepository.obtenerPorIdUsuario(idUsuario)
            ?: return emptyList()

        return cursos.filter { curso ->
            inscripciones.coursesIds.contains(curso.id) && !curso.yaFinalizo()
        }
    }
    fun obtenerCursosFinalizados(idUsuario: Long): List<Course> {
        val inscripciones = EnrollmentRepository.obtenerPorIdUsuario(idUsuario)
            ?: return emptyList()

        return cursos.filter { curso ->
            inscripciones.coursesIds.contains(curso.id) && curso.yaFinalizo()
        }
    }
}