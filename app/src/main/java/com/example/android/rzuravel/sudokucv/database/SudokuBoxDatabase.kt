package com.example.android.rzuravel.sudokucv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [SudokuBoxData::class], version = 1, exportSchema = false)
@TypeConverters(SudokuBoxDataConverters::class)
abstract class SudokuBoxDatabase : RoomDatabase() {
    abstract val sudokuBoxDatabaseDao: SudokuBoxDatabaseDao

    companion object {
        const val DB_NAME = "sudoku_board_value_table"

        @Volatile
        private var INSTANCE: SudokuBoxDatabase? = null

        fun getInstance(context: Context,
                        scope: CoroutineScope): SudokuBoxDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                            context.applicationContext,
                                            SudokuBoxDatabase::class.java,
                                            DB_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(SudokuBoxDatabaseCallback(scope))
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

    private class SudokuBoxDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.sudokuBoxDatabaseDao)
                }
            }
        }

        suspend fun populateDatabase(sudokuBoxDatabaseDao: SudokuBoxDatabaseDao) {
            sudokuBoxDatabaseDao.clear()

            for (row in 0..8) {
                for (column in 0..8) {
                    val box = SudokuBoxData(
                        boxId = row * 9 + column,
                        row = row,
                        column = column,
                        subregion = (row / 3) * 3 + (column / 3),
                        value = null,
                        hints = BooleanArray(9) { true })
                    sudokuBoxDatabaseDao.insert(box)
                }
            }
        }
    }
}