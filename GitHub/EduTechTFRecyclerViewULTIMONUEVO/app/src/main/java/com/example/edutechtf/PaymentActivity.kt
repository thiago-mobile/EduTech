package com.example.edutechtf

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.edutechtf.data.Course
import com.example.edutechtf.data.User
import com.example.edutechtf.payments.Mastercard
import com.example.edutechtf.payments.MedioPago
import com.example.edutechtf.payments.MercadoPago
import com.example.edutechtf.payments.Visa
import com.example.edutechtf.repositories.CourseRepository
import com.example.edutechtf.repositories.EnrollmentRepository
import com.example.edutechtf.repositories.UserRepository

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val cursoId = intent.getLongExtra("CURSO_ID", -1L)
        val curso = CourseRepository.obtenerPorId(cursoId)
        if (curso == null) {
            Toast.makeText(this, "ERROR: No se encontró el curso seleccionado", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }

        val ivCurso = findViewById<ImageView>(R.id.ivIconoCurso)
        val tvTitulo = findViewById<TextView>(R.id.tvTituloPago)
        val tvInstructor = findViewById<TextView>(R.id.tvInstructorPago)
        val tvFecha = findViewById<TextView>(R.id.tvFechaPago)
        val tvPrecioBase = findViewById<TextView>(R.id.tvPrecioBase)

        val btnMercadoPago = findViewById<CardView>(R.id.btnPagarMercadoPago)
        val btnVisa = findViewById<CardView>(R.id.btnPagarVisa)
        val btnMastercard = findViewById<CardView>(R.id.btnPagarMastercard)

        tvTitulo.text = curso.name
        tvInstructor.text = "Instructor : ${curso.instructor}"
        tvFecha.text = "Fecha : ${curso.date} - ${curso.time}"
        val montoBase = CourseRepository.COSTO_CURSO
        tvPrecioBase.text = "Precio Base: $montoBase"

        val usuarioActual = UserRepository.usuarioLogueado

        btnMercadoPago.setOnClickListener {
            procesarPagoYInscripcion(usuarioActual, curso, MercadoPago(), montoBase)
        }

        btnVisa.setOnClickListener {
            procesarPagoYInscripcion(usuarioActual,curso, Visa(), montoBase)
        }

        btnMastercard.setOnClickListener {
            procesarPagoYInscripcion(usuarioActual,curso, Mastercard(),montoBase)
        }
    }

    private fun procesarPagoYInscripcion(
        usuario: User?, // acepta nulos (?)
        curso: Course?, // acepta nulos (?)
        medioPago: MedioPago,
        montoBase: Double,
    ) {
        if (usuario == null || curso == null) {
            Toast.makeText(this, "Error, No hay sesion Activa", Toast.LENGTH_SHORT).show()
            return
        }

        val montoFinal = medioPago.calcularMontoTotal(montoBase)

        if (usuario.money >= montoFinal) {
            try {
                EnrollmentRepository.agregarCursoAUsuario(usuario.id, listOf(curso.id))
                usuario.money -= montoFinal
                UserRepository.actualizarUsuario(usuario)
                Toast.makeText(
                    this,
                    "¡Inscripción exitosa con ${medioPago.nombre}! Total abonado: $$montoFinal",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    e.message ?: "Error al procesar inscripción",
                    Toast.LENGTH_LONG
                ).show()
            }
        }else{
            Toast.makeText(
                this,
                "Saldo insuficiente. Tienes \$${usuario.money} y necesitas \$$montoFinal",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}