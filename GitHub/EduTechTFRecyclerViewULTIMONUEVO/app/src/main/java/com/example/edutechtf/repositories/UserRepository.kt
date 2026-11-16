package com.example.edutechtf.repositories

import com.example.edutechtf.data.User
import com.example.edutechtf.exceptions.CredencialesInvalidasException

object UserRepository {
    private val usuarios = mutableListOf<User>()

    var usuarioLogueado: User? = null
        private set

    init {
        usuarios.add(
            User(
                1504L,
                "martin_albanesi",
                "1234",
                "Martin",
                "Albanesi",
                3500000.50,
                "2024/05/13"
            )
        )
        usuarios.add(
            User(
                2802L,
                "franco_mazafra",
                "1234",
                "Franco German",
                "Mazafra",
                200000.50,
                "2021/01/20"
            )
        )
        usuarios.add(
            User(
                1510L,
                "agus_maldonado",
                "1234",
                "Agustina Belen",
                "Arce Maldonado",
                120000.0,
                "2018/04/15"
            )
        )
    }

    fun login(nickname: String, password: String): User {
        val nickLimpio = nickname.trim().lowercase()

        val usuarioEncontrado = usuarios.find {
            it.nickname.lowercase() == nickLimpio && it.password == password
        } ?: throw CredencialesInvalidasException("Usuario o contraseña incorrectos.")

        usuarioLogueado = usuarioEncontrado
        return usuarioEncontrado
    }

    fun logout() {
        usuarioLogueado = null
    }

    fun actualizarUsuario(usuarioActualizado: User) {
        val indice = usuarios.indexOfFirst { it.id == usuarioActualizado.id }
        if (indice != -1) {
            usuarios[indice] = usuarioActualizado

            if (usuarioLogueado?.id == usuarioActualizado.id) {
                usuarioLogueado = usuarioActualizado
            }
        }
    }

    fun obtenerPorId(id: Long): User? {
        return usuarios.find { it.id == id }
    }
}