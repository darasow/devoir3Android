package com.example.devoir3androide

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreferences
    lateinit var db: DatabaseDara

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        db = DatabaseDara(this)
        sharedPreference = this.getSharedPreferences("etat_app", Context.MODE_PRIVATE)
        val estAuthentifier = sharedPreference.getBoolean("estIdentifier", false)

        if (estAuthentifier) {
            val email = sharedPreference.getString("email", "")
            val password = sharedPreference.getString("password", "")
            Intent(this, ListeEtudiant::class.java).also {
                startActivity(it)
            }
        } else {
            Toast.makeText(applicationContext, "Connectez-vous", Toast.LENGTH_SHORT).show()
        }

        val connecte: Button = findViewById(R.id.connecte) as Button
        val creeCompte: TextView = findViewById(R.id.creeCompte)

        connecte.setOnClickListener {
            val emailText: String = findViewById<TextView>(R.id.email).text.toString().trim()
            val passwordText: String = findViewById<TextView>(R.id.password).text.toString().trim()
            val nameText: String = "teste"

            val error: TextView = findViewById<TextView>(R.id.error)
            val useradd = User(nameText, emailText, passwordText)
            GlobalScope.launch(Dispatchers.Main) {
                val user = with(Dispatchers.IO) { db.findUserLogin(useradd) }
                if (user != null) {
                    error.isVisible = false

                    // On mémorise la connexion
                    val edit = sharedPreference.edit()
                    edit.putBoolean("estIdentifier", true)
                    edit.putString("email", emailText)
                    edit.putString("password", passwordText)
                    edit.apply() // ou commit()

                    Intent(applicationContext, ListeEtudiant::class.java).also {
                        it.putExtra("email", emailText)
                        it.putExtra("password", passwordText)
                        startActivity(it)
                    }
                } else {
                    error.isVisible = true
                }
            }
        }

        creeCompte.setOnClickListener {
            Intent(this, FormeUser::class.java).also {
                startActivity(it)
            }
        }
    }
}
