package com.example.project2

import android.app.Application
import com.example.project2.database.AppDatabase

class DogApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}