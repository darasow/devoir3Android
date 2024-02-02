package com.example.devoir3androide

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Etudiant::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}