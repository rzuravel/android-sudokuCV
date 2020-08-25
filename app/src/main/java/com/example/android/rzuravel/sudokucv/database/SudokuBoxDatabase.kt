package com.example.android.rzuravel.sudokucv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SudokuBoxData::class], version = 1, exportSchema = false)
@TypeConverters(SudokuBoxDataConverters::class)
abstract class SudokuBoxDatabase : RoomDatabase() {
    abstract val sudokuBoxDatabaseDao: SudokuBoxDatabaseDao

    companion object {
        const val DB_NAME = "sudoku_board_value_table"

        @Volatile
        private var INSTANCE: SudokuBoxDatabase? = null

        fun getInstance(context: Context): SudokuBoxDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                            context.applicationContext,
                                            SudokuBoxDatabase::class.java,
                                            DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}