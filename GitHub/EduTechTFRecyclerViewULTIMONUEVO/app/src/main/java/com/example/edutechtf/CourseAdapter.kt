package com.example.edutechtf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.edutechtf.data.Course
import com.example.edutechtf.repositories.CourseRepository
import com.example.edutechtf.repositories.EnrollmentRepository

class CourseAdapter(
    private val listaCursos: List<Course>,
    private val yaInscripto: Boolean = false,
    private val onInscribirseClicked: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreCurso)
        val tvInstructor: TextView = itemView.findViewById(R.id.tvInstructorCurso)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaCurso)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvCapacidad: TextView = itemView.findViewById(R.id.tvCapacidad)
        val btnInscribir: Button = itemView.findViewById(R.id.btnInscribir)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_curso, parent,false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val curso = listaCursos[position]

        holder.tvNombre.text = curso.name
        holder.tvInstructor.text = "Instructor: ${curso.instructor}"
        holder.tvFecha.text = "Fecha: ${curso.date} - ${curso.time}"

        val precioFormateado = String.format("%,.2f", CourseRepository.COSTO_CURSO)
        holder.tvPrecio.text = "\$$precioFormateado"

        val inscriptosActuales = EnrollmentRepository.contarInscriptosPorCurso(curso.id)

        holder.tvCapacidad.text = "Cupos: $inscriptosActuales/${curso.capacity}"

        if (yaInscripto) {
            holder.btnInscribir.text = "Inscripto ✓"
            holder.btnInscribir.isEnabled = false
            holder.btnInscribir.alpha = 0.6f
            holder.btnInscribir.setOnClickListener(null)
        } else if (inscriptosActuales >= curso.capacity) {
            holder.btnInscribir.text = "Cupo Agotado"
            holder.btnInscribir.isEnabled = false
            holder.btnInscribir.alpha = 0.5f
            holder.btnInscribir.setOnClickListener(null)
        } else {
            holder.btnInscribir.text = "Inscribirse"
            holder.btnInscribir.isEnabled = true
            holder.btnInscribir.alpha = 1.0f
            holder.btnInscribir.setOnClickListener {
                onInscribirseClicked(curso)
            }
        }
    }

    override fun getItemCount(): Int {
        return listaCursos.size
    }
}