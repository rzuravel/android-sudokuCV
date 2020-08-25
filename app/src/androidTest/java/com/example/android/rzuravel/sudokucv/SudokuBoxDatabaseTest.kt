package com.example.android.rzuravel.sudokucv

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.rzuravel.sudokucv.database.SudokuBoxData
import com.example.android.rzuravel.sudokucv.database.SudokuBoxDatabase
import com.example.android.rzuravel.sudokucv.database.SudokuBoxDatabaseDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SudokuBoxDatabaseTest {

    private lateinit var sudokuBoxDatabaseDao: SudokuBoxDatabaseDao
    private lateinit var sudokuBoxDatabase: SudokuBoxDatabase

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        val databaseBuild = Room.inMemoryDatabaseBuilder(context, SudokuBoxDatabase::class.java)
        val allowMainThread = databaseBuild.allowMainThreadQueries()
        sudokuBoxDatabase = allowMainThread.build()

        sudokuBoxDatabaseDao = sudokuBoxDatabase.sudokuBoxDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() = sudokuBoxDatabase.close()

    @Test
    @Throws(Exception::class)
    fun fillAndCheck() {
        for (row in 0..8) {
            for (column in 0..8) {
                var box = SudokuBoxData(
                    boxId = row * 9 + column,
                    row = row,
                    column = column,
                    subregion = (row / 3) * 3 + (column / 3),
                    value = null,
                    hints = BooleanArray(9) { true })
                sudokuBoxDatabaseDao.insert(box)
            }
        }

        val boxes = sudokuBoxDatabaseDao.getAllBoxes()
        assertEquals(boxes.size, 81)
    }
}