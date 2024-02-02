package com.example.devoir3androide

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppDao {

    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertEtudiant(etudiant: Etudiant)

    @Update
    fun updateEtudiant(etudiant: Etudiant)

    @Query("SELECT * FROM Users WHERE email = :email")
    fun findUser(email: String): User?

    @Query("SELECT * FROM Etudiants WHERE matricule = :matricule")
    fun findEtudiantByMatricule(matricule: String): Etudiant?

    @Query("SELECT * FROM Etudiants WHERE matricule = :matricule AND id != :id")
    fun findEtudiantByMatriculeEdit(matricule: String, id : Long): Etudiant?

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    fun findUserLogin(email: String, password : String): User?

    @Query("SELECT * FROM Etudiants")
    fun findEtudiants(): List<Etudiant>

    @Query("DELETE FROM Etudiants WHERE id = :id")
    fun deleteEtudiant(id: Long)
}