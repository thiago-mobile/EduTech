package com.example.edutechtf

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.edutechtf.exceptions.CredencialesInvalidasException
import com.example.edutechtf.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity(){
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etNickname = findViewById<EditText>(R.id.etNickname)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin= findViewById<Button>(R.id.btnLogin)

        etNickname.doAfterTextChanged { text -> viewModel.onNicknameChanged(text.toString()) }
        etPassword.doAfterTextChanged { text -> viewModel.onPasswordChanged(text.toString()) }
        btnLogin.setOnClickListener {
            try {
            viewModel.intentarLogin {
                Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            } catch (e: CredencialesInvalidasException){
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}