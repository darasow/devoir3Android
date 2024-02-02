package com.example.devoir3androide
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.os.Parcel
//import android.os.Parcelable
//
//class Etudiant(
//    var nom: String?,
//    var prenom: String?,
//    var matricule: String?,
//    var image: ByteArray?,
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.createByteArray()
//    ) {
//    }
//    var id : Int = -1
//    constructor(id : Int, nom: String?, prenom: String?, matricule: String?, image: ByteArray?):this(nom, prenom,matricule, image)
//    {
//        this.id = id
//    }
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(nom)
//        parcel.writeString(prenom)
//        parcel.writeString(matricule)
//        parcel.writeByteArray(image)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Etudiant> {
//        override fun createFromParcel(parcel: Parcel): Etudiant {
//            return Etudiant(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Etudiant?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//    fun resizeImage(image: ByteArray?, maxWidth: Int, maxHeight: Int): Bitmap? {
//        image?.let {
//            val originalBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
//
//            var width = 200
//            var height = 200
//
//            val aspectRatio: Float = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
//
//            if (width > height) {
//                width = maxWidth
//                height = (width / aspectRatio).toInt()
//            } else {
//                height = maxHeight
//                width = (height * aspectRatio).toInt()
//            }
//
//            return Bitmap.createScaledBitmap(originalBitmap, width, height, true)
//        }
//        return null
//    }
//}


import androidx.room.Entity
import androidx.room.PrimaryKey
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore

@Entity(tableName = "Etudiants")
data class Etudiant(
    val nom: String?,
    val prenom: String?,
    val matricule: String?,
    var image: ByteArray?
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createByteArray()
    ) {
        id = parcel.readLong()
    }

    @Ignore
    constructor(id : Long, nom: String?, prenom: String?, matricule: String?, image: ByteArray?):this(nom, prenom,matricule, image)
    {
        this.id = id
    }
    override fun describeContents(): Int {
        return 0
    }



    fun resizeImage(maxWidth: Int, maxHeight: Int): Bitmap? {
        image?.let {
            val originalBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)

            var width = 200
            var height = 200

            val aspectRatio: Float = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()

            if (width > height) {
                width = maxWidth
                height = (width / aspectRatio).toInt()
            } else {
                height = maxHeight
                width = (height * aspectRatio).toInt()
            }

            return Bitmap.createScaledBitmap(originalBitmap, width, height, true)
        }
        return null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(prenom)
        parcel.writeString(matricule)
        parcel.writeByteArray(image)
        parcel.writeLong(id)
    }

    companion object CREATOR : Parcelable.Creator<Etudiant> {
        override fun createFromParcel(parcel: Parcel): Etudiant {
            return Etudiant(parcel)
        }

        override fun newArray(size: Int): Array<Etudiant?> {
            return arrayOfNulls(size)
        }
    }
}
