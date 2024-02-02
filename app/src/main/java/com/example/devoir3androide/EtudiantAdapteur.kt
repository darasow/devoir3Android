package com.example.devoir3androide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class EtudiantAdapteur(
    var mcontext: Context,
    var resource: Int,
    var value: ArrayList<Etudiant>,
    var itemClickListener : View.OnClickListener?
) : RecyclerView.Adapter<EtudiantAdapteur.EtudiantViewHolder>()
{


    class EtudiantViewHolder(vue:View):RecyclerView.ViewHolder(vue){
        val cardView = vue.findViewById<CardView>(R.id.card_view)
        val etudiantNom = vue.findViewById<TextView>(R.id.nom)
        val etudiantPrenom = vue.findViewById<TextView>(R.id.prenom)
        val etudiantmatricule = vue.findViewById<TextView>(R.id.matricule)
        val etudiantImage = vue.findViewById<ImageView>(R.id.imageUser)
    }


    fun getByteMap(image: ByteArray?): Bitmap? {
        return image?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val vue =  LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return EtudiantViewHolder(vue)
    }

    override fun getItemCount(): Int {
       return  value.size
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        val etudiant = value[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.etudiantNom.setText(etudiant.nom)
        holder.etudiantPrenom.setText(etudiant.prenom)
        holder.etudiantmatricule.setText(etudiant.matricule)
        val resizedBitmap = getByteMap(etudiant.image)
        holder.etudiantImage.setImageBitmap(resizedBitmap)

    }


}

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val etudiant = value[position]
//        val convertView = LayoutInflater.from(this.mcontext).inflate(this.resource, parent, false)
//        var nom = convertView.findViewById<TextView>(R.id.nom)
//        var image = convertView.findViewById<ImageView>(R.id.imageUser)
//        var prenom = convertView.findViewById<TextView>(R.id.prenom)
//        var matricule = convertView.findViewById<TextView>(R.id.matricule)
//
//        nom.text = etudiant.nom
//        prenom.text = etudiant.prenom
//        val resizedBitmap = getByteMap(etudiant.image)
//        image.setImageBitmap(resizedBitmap)
//        var db = DatabaseDara(mcontext)
//
//        return convertView
//    }