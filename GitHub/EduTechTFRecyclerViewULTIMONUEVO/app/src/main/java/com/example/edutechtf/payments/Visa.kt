package com.example.edutechtf.payments

import java.time.LocalDateTime
import java.time.LocalTime

class Visa : MedioPago("Visa") {
    override fun calcularMontoTotal(
        montoBase: Double,
        fechaHora: LocalDateTime
    ): Double {
        val hora = fechaHora.toLocalTime()
        val horaInicio = LocalTime.of(15, 0)
        val horaFin = LocalTime.of(22, 30)

        val esHorarioReducido =
            !hora.isBefore(horaInicio) && !hora.isAfter(horaFin)

        val comision = if (esHorarioReducido) {
            0.01
        } else {
            0.03
        }

        return montoBase * (1 + comision)
    }
}