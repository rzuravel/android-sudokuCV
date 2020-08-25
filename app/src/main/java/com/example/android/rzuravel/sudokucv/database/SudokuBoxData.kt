package com.example.android.rzuravel.sudokucv.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SudokuBoxDatabase.DB_NAME)
data class SudokuBoxData(
    @PrimaryKey(autoGenerate = false)
    var boxId: Int = 0,

    @ColumnInfo(name="row")
    var row: Int = 0,

    @ColumnInfo(name="column")
    var column: Int = 0,

    @ColumnInfo(name="subregion")
    var subregion: Int = 0,

    @ColumnInfo(name="value")
    var value: Int? = 0,

    @ColumnInfo(name="hints")
    var hints: BooleanArray = BooleanArray(9) { false }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SudokuBoxData

        if (row != other.row) return false
        if (column != other.column) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + column
        return result
    }
}