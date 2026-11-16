package com.example.edutechtf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edutechtf.repositories.CourseRepository
import com.example.edutechtf.repositories.UserRepository

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val tvSaldo = findViewById<TextView>(R.id.tvSaldoUsuario)
        val rvMisCursos = findViewById<RecyclerView>(R.id.rvMisCursos)
        rvMisCursos.layoutManager = LinearLayoutManager(this)
        val rvCursosFinalizados = findViewById<RecyclerView>(R.id.rvCursosFinalizados)
        rvCursosFinalizados.layoutManager = LinearLayoutManager(this)

        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val usuarioActual = UserRepository.usuarioLogueado

        if (usuarioActual != null) {
            val saldoFormateado = String.format("%,.2f", usuarioActual.money)
            tvSaldo.text = "Saldo disponible: \$$saldoFormateado"

            val cursosActivos = CourseRepository.obtenerCursosActivos(usuarioActual.id)
            val adapterActivos = CourseAdapter(cursosActivos, yaInscripto = true) { curso ->
            }
            rvMisCursos.adapter = adapterActivos

            val cursosFinalizados = CourseRepository.obtenerCursosFinalizados(usuarioActual.id)
            val adapterFinalizados = CourseAdapter(cursosFinalizados, yaInscripto = true) { curso ->
            }
            rvCursosFinalizados.adapter = adapterFinalizados
        }

        btnCerrarSesion.setOnClickListener {
            UserRepository.logout()

            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        val btnNavCursosProfile = findViewById<android.view.View>(R.id.btnNavCursosProfile)
        val btnNavPerfilProfile = findViewById<android.view.View>(R.id.btnNavPerfilProfile)

        btnNavCursosProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnNavPerfilProfile.setOnClickListener {
        }
    }
}