package com.example.devoir3androide

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream

class FormeEtudiant : AppCompatActivity() {
    lateinit var nomEtudiant : EditText
    lateinit var prenomEtudiant : EditText
    lateinit var matriculeEtudiant : EditText
    lateinit var imageEtudiant : ImageView
    lateinit var etudiantCreate : Button
    lateinit var db : DatabaseDara
    var bitmap : Bitmap? = null
    private val PosteLauncherForImage: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                val inputStream = contentResolver.openInputStream(uri!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                imageEtudiant.setImageBitmap(bitmap)
            }
        }

    @SuppressLint("MissingInflatedId", "ResourceAsColor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forme_etudiant)
        db = DatabaseDara(this)
        val toolbar : Toolbar = findViewById(R.id.toolbarFormPoste) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextColor(Color.WHITE)


        nomEtudiant = findViewById(R.id.nomFormEtudiant)
        prenomEtudiant = findViewById(R.id.prenomFormEtudiant)
        matriculeEtudiant = findViewById(R.id.matriculeFormEtudiant)
        imageEtudiant = findViewById(R.id.imgEtudiant)
        etudiantCreate = findViewById(R.id.EtudiantCreate)
        var errorFormPoste = findViewById<TextView>(R.id.errorFormPoste)

        imageEtudiant.setOnClickListener {
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            intentImg.type = "image/*"
            PosteLauncherForImage.launch(intentImg)
        }
        etudiantCreate.setOnClickListener {

            val nomText = nomEtudiant.text.toString().trim()
            val prenomText = prenomEtudiant.text.toString().trim()
            val matriculeText = matriculeEtudiant.text.toString().trim()
            var bitMapImg : ByteArray = getBYteImage(bitmap)
            if(nomText.isEmpty() || prenomText.isEmpty() || bitMapImg.isEmpty() || matriculeText.isEmpty())
            {
                errorFormPoste.setBackgroundColor(R.color.red)
                errorFormPoste.setText("Tout les champs sont obligatoire")
                errorFormPoste.visibility = View.VISIBLE
            }else
            {
                val etudiant = Etudiant(nomText, prenomText,matriculeText, bitMapImg)
                runBlocking {
                    val result = withContext(Dispatchers.IO) {
                        db.insertEtudiant(etudiant)
                    }
                     if(!result)
                     {
                         errorFormPoste.setBackgroundColor(R.color.red)
                         errorFormPoste.setText("Le matricule existe deja")
                         errorFormPoste.visibility = View.VISIBLE
                     }else
                     {
                         errorFormPoste.setBackgroundColor(R.color.red)
                         errorFormPoste.setText("Etudiant crée avec succes")
                         errorFormPoste.visibility = View.VISIBLE
                         nomEtudiant.setText("")
                         prenomEtudiant.setText("")
                         matriculeEtudiant.setText("")
                         imageEtudiant.setImageResource(R.drawable.user)
                         bitmap = null
                         finish()
                     }
                }
            }

        }

    }// onCreate

    private fun getBYteImage(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }




}