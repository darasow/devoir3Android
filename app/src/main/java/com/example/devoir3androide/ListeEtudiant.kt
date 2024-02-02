package com.example.devoir3androide

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class ListeEtudiant : AppCompatActivity(), View.OnClickListener, CoroutineScope by MainScope() {
    @SuppressLint("MissingInflatedId")
    lateinit var recyclerView : RecyclerView
    lateinit var listeEtudiants : ArrayList<Etudiant>
    lateinit var adapter : EtudiantAdapteur
    lateinit var db : DatabaseDara
    private var longClickedPosition: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_etudiant)

        val toolbar : Toolbar = findViewById(R.id.toolbarListe) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)

        db = DatabaseDara(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextColor(Color.WHITE)

        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(this, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    // Gérez le clic sur l'élément ici si nécessaire
                    longClickedPosition = position
                }

                override fun onItemLongClick(view: View?, position: Int) {
                    longClickedPosition = position
                    openContextMenu(view)
                }
            })
        )
        registerForContextMenu(recyclerView)

    } // fin onCreate()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.acceuil, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.ajouter -> {

                Intent(this, FormeEtudiant::class.java).also {

                    startActivity(it)
                }
                return true
            }
            R.id.deconection ->
            {
                var confirmeFragment = ConfirmeDialogueLogout("", "Quitter", applicationContext)
                confirmeFragment.listener = object : ConfirmeDialogueLogout.ConfirmeDialogueListener{
                    override fun ondialoguePositiveClick() {
                        finish()
                    }
                    override fun ondialogueNegativeClick() {

                    }
                }
                confirmeFragment.show(supportFragmentManager, "ConfirmationDialogue")

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        launch {
            withContext(Dispatchers.IO) {
                listeEtudiants = db.findEtudiants() as ArrayList<Etudiant>
            }
            runOnUiThread {
                if (listeEtudiants != null) {
                    adapter = EtudiantAdapteur(this@ListeEtudiant, R.layout.item, listeEtudiants, this@ListeEtudiant)
                    recyclerView.adapter = adapter
                }
            }
        }
        super.onResume()
    }


    override fun onClick(view: View) {
          if(view.tag != null)
          {
              val index = view.tag as Int
          }
       return
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.editContext -> {
                var intent = Intent(this, FormEdit::class.java)
                launch {
                    withContext(Dispatchers.IO) {
                        val etudiant = listeEtudiants[longClickedPosition]
                        intent.putExtra("etudiant" , etudiant as Parcelable)
                        startActivity(intent)
                    }
                }
                return true
            }
            R.id.SupprimerContext ->{
                var confirmeFragment = ConfirmeDialogueLogout("", "Supprimer", applicationContext)
                confirmeFragment.listener = object : ConfirmeDialogueLogout.ConfirmeDialogueListener{
                    override fun ondialoguePositiveClick() {
                        if (longClickedPosition != -1) {
                            val etudiant = listeEtudiants[longClickedPosition]
                            println(listeEtudiants)
                            launch {
                                val result = db.deleteEtudiant(etudiant.id)
                                if (result) {
                                    listeEtudiants.removeAt(longClickedPosition)
                                    adapter.notifyDataSetChanged()
                                    Toast.makeText(applicationContext, "Supprimer étudiant à la position $longClickedPosition", Toast.LENGTH_SHORT).show()
                                }
                                // Réinitialisez la position après utilisation
                                longClickedPosition = -1
                            }
                        }
                    }
                    override fun ondialogueNegativeClick() {
                        Toast.makeText(applicationContext, "Suppression ennulée ${listeEtudiants[longClickedPosition].id}", Toast.LENGTH_SHORT).show()
                    }
                }
                confirmeFragment.show(supportFragmentManager, "ConfirmationDialogue")

                return true
            }
        }
        return super.onContextItemSelected(item)
    }


}

