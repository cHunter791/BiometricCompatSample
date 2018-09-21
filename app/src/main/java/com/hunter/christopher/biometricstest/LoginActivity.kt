package com.hunter.christopher.biometricstest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.biometrics.BiometricPrompt
import android.os.Looper
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.login_button).setOnClickListener { openBiometricPrompt() }
    }

    private fun openBiometricPrompt() {
        BiometricPrompt(this, UiThreadExecutor(), object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@LoginActivity, "Auth error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@LoginActivity, "Auth failed", Toast.LENGTH_SHORT).show()
            }
        }).authenticate(BiometricPrompt.PromptInfo.Builder()
                .setTitle("Test Title")
                .setSubtitle("Test Subtitle")
                .setDescription("Test description")
                .setNegativeButtonText("Cancel")
                .build())
    }

    internal inner class UiThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            handler.post(command)
        }
    }
}
