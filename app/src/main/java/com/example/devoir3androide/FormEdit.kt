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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class FormEdit : AppCompatActivity() {
    lateinit var nomEtudiant : EditText
    lateinit var prenomEtudiant : EditText
    lateinit var matriculeEtudiant : EditText
    lateinit var secreId : TextView
    lateinit var imageEtudiant : ImageView
    lateinit var etudiantCreate : Button
    lateinit var adapter : EtudiantAdapteur
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
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_edit)

        db = DatabaseDara(this)
        val toolbar : Toolbar = findViewById(R.id.toolbarFormEdit) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextColor(Color.WHITE)

        nomEtudiant = findViewById(R.id.nomFormEdit)
        prenomEtudiant = findViewById(R.id.prenomFormEdit)
        matriculeEtudiant = findViewById(R.id.matriculeFormEdit)
        imageEtudiant = findViewById(R.id.imgEdit)
        etudiantCreate = findViewById(R.id.EtudiantEdit)
        var errorFormPoste = findViewById<TextView>(R.id.errorFormPoste)
        var secreId = findViewById<TextView>(R.id.idSecre)

        val etudiant = intent.getParcelableExtra<Etudiant>("etudiant") as Etudiant

        var bitmatimg = getByteMap(etudiant.image)
        imageEtudiant.setImageBitmap(bitmatimg)
        nomEtudiant.setText(etudiant.nom.toString())
        prenomEtudiant.setText(etudiant.prenom.toString())
        matriculeEtudiant.setText(etudiant.matricule.toString())

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
            secreId.text = etudiant.id.toString() as String
            if(bitMapImg.isEmpty())
            {
                bitMapImg = getBYteImage(bitmatimg)
            }
            if(nomText.isEmpty() || prenomText.isEmpty() || matriculeText.isEmpty())
            {
                errorFormPoste.setBackgroundColor(Color.RED)
                errorFormPoste.setText("Tout les champs sont obligatoire")
                errorFormPoste.visibility = View.VISIBLE
            }else
            {
                val etudiant = Etudiant(nomText, prenomText,matriculeText, bitMapImg)
                runBlocking {
                    val result = withContext(Dispatchers.IO) {
                        db.updateEtudiant(etudiant, secreId.text.toString().toLong())
                    }
                    if(!result)
                    {
                        errorFormPoste.setBackgroundColor(Color.RED)
                        errorFormPoste.setText("Le matricule existe deja")
                        errorFormPoste.visibility = View.VISIBLE
                    }else
                    {
                        errorFormPoste.setBackgroundColor(Color.BLUE)
                        errorFormPoste.setText("Etudiant modifier avec succes")
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



    }

    fun getByteMap(image: ByteArray?): Bitmap? {
        val bitmap = image?.let { BitmapFactory.decodeByteArray(image, 0, it.size) }
        return bitmap

    }
    private fun getBYteImage(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}