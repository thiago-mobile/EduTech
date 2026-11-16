package com.example.edutechtf.payments

import java.time.LocalDateTime

class MercadoPago : MedioPago("Mercado Pago") {
    override fun calcularMontoTotal(
        montoBase: Double,
        fechaHora: LocalDateTime
    ): Double {
        val comision = 0.02
        return montoBase * (1 + comision)
    }
}