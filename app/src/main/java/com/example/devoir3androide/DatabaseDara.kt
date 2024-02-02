package com.example.devoir3androide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

//class DatabaseDara(var mContext: Context) :
//    SQLiteOpenHelper(mContext, dbName, null, dbVersion)
//{
//    override fun onCreate(db: SQLiteDatabase?)
//    {
//        // cration des tables
//        val createTableUser = """
//            CREATE TABLE "$UserTableName" (
//            $UserId integer PRIMARY KEY,
//            $UserName varchar(50),
//            $UserEmail varchar(100) unique,
//            $UserPassword varchar(20)
//            )
//        """.trimIndent()
//
//        val createTableEtudiant = """
//            CREATE TABLE "$EtudiantTableName" (
//            $EtudiantId integer PRIMARY KEY,
//            $EtudiantNom varchar(10),
//            $EtudiantPrenom varchar(30),
//            $EtudiantMatricule varchar(50),
//            $EtudiantImage blob
//            )
//        """.trimIndent()
//         db?.execSQL(createTableUser)
//         db?.execSQL(createTableEtudiant)
//
//    }
//
//    // on Upgrade une base de donnee c'est lorsq'on change la structure de la base de donnee (modification de nom d'une table, type de colonne...)
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
//    {
//        db?.execSQL("DROP TABLE IF EXISTS $UserTableName")
//        db?.execSQL("DROP TABLE IF EXISTS $EtudiantTableName")
//        onCreate(db)
//    }
//
//    companion object
//    {
//        // DB
//        private var dbName = "Dara"
//        private var dbVersion = 1
//
//        // Users
//        private var UserTableName = "Users"
//        private var UserId = "id"
//        private var UserName = "name"
//        private var UserEmail = "email"
//        private var UserPassword = "password"
//
//        // Etudiants
//        private var EtudiantTableName = "Etudiants"
//        private var EtudiantId = "id"
//        private var EtudiantNom = "nom"
//        private var EtudiantImage = "image"
//        private var EtudiantPrenom = "prenom"
//        private var EtudiantMatricule = "matricule"
//    }
//
//    fun adduser(user : User) : Boolean
//    {
//        var db = writableDatabase
//        var values = ContentValues()
//        values.put(UserName, user.name)
//        values.put(UserEmail, user.email)
//        values.put(UserPassword, user.password)
//
//        val resultat = db.insert(UserTableName, null, values).toInt()
//
//        db.close()
//        return  resultat != -1
//    }
//
//    fun addEtudiant(etudiant: Etudiant): Boolean {
//        var db = writableDatabase
//        var values = ContentValues()
//        values.put(EtudiantNom, etudiant.nom)
//        values.put(EtudiantPrenom, etudiant.prenom)
//        values.put(EtudiantMatricule, etudiant.matricule)
//
//        // Redimensionner l'image
//        val resizedImageByteArray = etudiant.image?.let { resizeByteArray(it, 1080, 1080) }
//
//        values.put(EtudiantImage, resizedImageByteArray)
//
//        val resultat = db.insert(EtudiantTableName, null, values)
//        db.close()
//        return resultat != -1L
//    }
////
//    fun deleteEtudiant(id : Int) : Boolean
//    {
//        var db = writableDatabase
//        var resultat =  db.delete(EtudiantTableName, "id=?", arrayOf(id.toString()))
//        db.close()
//        return  resultat > 0
//    }
//
//    fun resizeByteArray(imageByteArray: ByteArray, maxWidth: Int, maxHeight: Int): ByteArray {
//        val originalBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
//
//        val width = originalBitmap.width
//        val height = originalBitmap.height
//
//        if (width <= maxWidth && height <= maxHeight) {
//            return imageByteArray // Pas besoin de redimensionner
//        }
//
//        val scaleWidth = maxWidth.toFloat() / width
//        val scaleHeight = maxHeight.toFloat() / height
//
//        val matrix = Matrix()
//        matrix.postScale(scaleWidth, scaleHeight)
//
//        val resizedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false)
//
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//
//        return byteArrayOutputStream.toByteArray()
//    }
//
//
//    @SuppressLint("SuspiciousIndentation")
//    fun findUser(email : String, password : String) : User?
//    {
//        val db = this.readableDatabase
//
//        var querySelect = """
//            SELECT * FROM $UserTableName WHERE $UserEmail =? AND $UserPassword =?
//        """.trimIndent()
//
//        var selectionArgs = arrayOf(email, password)
//        var user : User? = null
//        var cursor = db.query(UserTableName, null, "$UserEmail =? AND $UserPassword =?", selectionArgs, null, null, null)
//        if(cursor != null)
//        {
//            if(cursor.moveToFirst())
//            {
//                val id = cursor.getInt(cursor.getColumnIndexOrThrow(UserId))
//                val name = cursor.getString(cursor.getColumnIndexOrThrow(UserName))
//                val email = cursor.getString(cursor.getColumnIndexOrThrow(UserEmail))
//                user = User(id, name, email, "")
//                return user
//
//            }
//        }
//        db.close()
//        return user
//
//    }
//
//   @SuppressLint("SuspiciousIndentation")
//    fun findEtudiants() : ArrayList<Etudiant>
//    {
//        val db = this.readableDatabase
//        val etudiants = ArrayList<Etudiant>()
//        val queryPostes = "SELECT * FROM $EtudiantTableName"
//
//        var cursor = db.rawQuery(queryPostes, null)
//        if(cursor != null)
//        {
//            if(cursor.moveToFirst())
//            {
//                do {
//                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(EtudiantId))
//                    val nom = cursor.getString(cursor.getColumnIndexOrThrow(EtudiantNom))
//                    val prenom = cursor.getString(cursor.getColumnIndexOrThrow(
//                        EtudiantPrenom))
//                    val image = cursor.getBlob(cursor.getColumnIndexOrThrow(EtudiantImage))
//                    val matricule = cursor.getString(cursor.getColumnIndexOrThrow(EtudiantMatricule))
//                    etudiants.add(Etudiant(id, nom, prenom, matricule, image))
//                }while (cursor.moveToNext())
//            }
//        }
//
//        db.close()
//        return etudiants
//    }
//
//
//
//}

class DatabaseDara(context: Context) {

    private val appDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "Dara"
    ).build()

    private val appDao = appDatabase.appDao()

    fun insertEtudiant(etudiant: Etudiant) : Boolean {
        etudiant.image = etudiant.image?.let { resizeByteArray(it, 1080, 1080) }
        try {
            val existingEtudiant = etudiant.matricule?.let { appDao.findEtudiantByMatricule(it) }
            if (existingEtudiant == null) {
                appDao.insertEtudiant(etudiant)
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
    }

   fun updateEtudiant(etudiant: Etudiant, id : Long) : Boolean {
        etudiant.image = etudiant.image?.let { resizeByteArray(it, 1080, 1080) }
        try {
            // Trouver l'étudiant existant par matricule
            val existingEtudiant : Etudiant? = etudiant.matricule?.let { appDao.findEtudiantByMatricule(it) }
            if(existingEtudiant != null && existingEtudiant.id != id)
            {
                return false
            } else {
                etudiant.id = id
                appDao.updateEtudiant(etudiant)
                return true
            }
        } catch (e: Exception) {
            println("autre bleme")
            return false
        }
    }

    fun resizeByteArray(imageByteArray: ByteArray, maxWidth: Int, maxHeight: Int): ByteArray {
        val originalBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)

        val width = originalBitmap.width
        val height = originalBitmap.height

        if (width <= maxWidth && height <= maxHeight) {
            return imageByteArray // Pas besoin de redimensionner
        }

        val scaleWidth = maxWidth.toFloat() / width
        val scaleHeight = maxHeight.toFloat() / height

        val matrix = android.graphics.Matrix()
        matrix.postScale(scaleWidth, scaleHeight)

        val resizedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }

    suspend fun findUser(user : User?): User? {
        return withContext(Dispatchers.IO) {
            user?.email?.let { appDao.findUser(it) }
        }
    }

    suspend fun findEtudiantByMatricule(etudiant: Etudiant?): Etudiant? {
        return withContext(Dispatchers.IO) {
            etudiant?.matricule?.let { appDao.findEtudiantByMatricule(it) }
        }
    }

    suspend fun findUserLogin(user : User?): User? {
        return withContext(Dispatchers.IO) {
            user?.email?.let { user.password?.let { it1 -> appDao.findUserLogin(it, it1) } }
        }
    }

    suspend fun findEtudiants(): List<Etudiant> {
        return withContext(Dispatchers.IO) {
            appDao.findEtudiants()
        }
    }
    fun insertUser(user: User): Boolean {
        try {
            val existingUser = user.email?.let { appDao.findUser(it) }
            if (existingUser == null) {
                appDao.insertUser(user)
                return true
            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun deleteEtudiant(id: Long): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                appDao.deleteEtudiant(id)
            }
            true
        } catch (e: Exception) {
            false
        }
    }


}