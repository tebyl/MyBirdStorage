package com.tebyl.aviariolocal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [Bird::class], version = 1, exportSchema = true)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun birdDao(): BirdDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: run {
                    lateinit var db: AppDatabase
                    db = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "aviario.db"
                    )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(sqdb: SupportSQLiteDatabase) {
                            applicationScope.launch { seedIfEmpty(db.birdDao()) }
                        }
                    })
                    .build()
                    INSTANCE = db
                    db
                }
            }
        }
    }
}
