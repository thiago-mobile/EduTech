package com.example.edutechtf.payments

import java.time.DayOfWeek
import java.time.LocalDateTime

class Mastercard : MedioPago("Mastercard") {
    override fun calcularMontoTotal(
        montoBase: Double,
        fechaHora: LocalDateTime
    ): Double {
        val esFinDeSemana =
            fechaHora.dayOfWeek == DayOfWeek.SATURDAY ||
                    fechaHora.dayOfWeek == DayOfWeek.SUNDAY

        val comision = if (esFinDeSemana) {
            0.03
        } else {
            0.0075
        }

        return montoBase * (1 + comision)
    }
}