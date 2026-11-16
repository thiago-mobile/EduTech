package com.example.edutechtf.payments

import java.time.LocalDateTime

abstract class MedioPago(val nombre: String) {
    abstract fun calcularMontoTotal(
        montoBase: Double,
        fechaHora: LocalDateTime = LocalDateTime.now()
    ): Double
}