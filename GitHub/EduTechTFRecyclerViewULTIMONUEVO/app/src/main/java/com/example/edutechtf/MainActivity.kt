package com.example.edutechtf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edutechtf.repositories.CourseRepository
import com.example.edutechtf.repositories.EnrollmentRepository
import com.example.edutechtf.repositories.UserRepository
import com.example.edutechtf.repositories.yaFinalizo

class MainActivity : AppCompatActivity() {

    private lateinit var rvCursosDisponibles: RecyclerView
    private lateinit var tvBienvenida: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvBienvenida = findViewById(R.id.tvBienvenida)
        rvCursosDisponibles = findViewById(R.id.rvCursosDisponibles)
        rvCursosDisponibles.layoutManager = LinearLayoutManager(this)

        val btnNavCursos = findViewById<android.view.View>(R.id.btnNavCursos)
        val btnNavPerfil = findViewById<android.view.View>(R.id.btnNavPerfil)

        btnNavCursos.setOnClickListener {
        }

        btnNavPerfil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        val usuarioActual = UserRepository.usuarioLogueado

        if (usuarioActual != null) {
            tvBienvenida.text = "¡Hola, ${usuarioActual.name}!"
            val todosLosCursos = CourseRepository.obtenerTodos()

            val cursosInscriptos = CourseRepository.obtenerCursosCompletados(usuarioActual.id)
            val idInscriptos = cursosInscriptos.map { it.id }
            val cursosDisponiblesInscribir = todosLosCursos.filter { curso ->
                val noInscripto = curso.id !in idInscriptos
                val noFinalizo = !curso.yaFinalizo()
                val inscriptosActuales = EnrollmentRepository.contarInscriptosPorCurso(curso.id)
                val tieneCupos = inscriptosActuales < curso.capacity

                noInscripto && noFinalizo && tieneCupos
            }

            val adapter = CourseAdapter(cursosDisponiblesInscribir) { cursoSeleccionado ->
                Toast.makeText(this, "Inscribiendo a: ${cursoSeleccionado.name}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PaymentActivity::class.java).apply {
                    putExtra("CURSO_ID", cursoSeleccionado.id)
                }
                startActivity(intent)
            }

            rvCursosDisponibles.adapter = adapter

        } else {
            Toast.makeText(this, "Error: No se encontró sesión activa.", Toast.LENGTH_LONG).show()
        }
    }
}