package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase


@Entity()
data class data(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "Date") val Date: String,
    @ColumnInfo(name = "min") val min: Double?,
    @ColumnInfo(name = "max") val max:Double?
)
data class Temp(
    val min: Double,
    val max: Double
)

@Dao
interface wthrDao {
    @Query("SELECT * FROM data")
    suspend fun getAll(): List<data>



    @Query("SELECT \n" +
            "    AVG(min) AS min,\n" +
            "    AVG(max) AS max\n" +
            "FROM \n" +
            "    data\n" +
            "WHERE \n" +
            "strftime('%m-%d', Date) = strftime('%m-%d',:input_date) ORDER BY Date DESC LIMIT 10")
    suspend fun getfuturespec(input_date: String):Temp


    @Query("SELECT min,max FROM data WHERE Date = :input_date")
    suspend fun gethist(input_date:String):Temp

    @Query("SELECT * FROM data WHERE strftime('%m-%d', Date) = strftime('%m-%d',:input_date) ORDER BY Date DESC LIMIT 10")
    suspend fun getd(input_date: String):List<data>

    @Insert
    suspend fun insertAll(dt: data)

    @Delete
    suspend fun delete(dt: data)

    @Query("DELETE FROM data")
    suspend fun deleteall()
}




@Database(entities = [data::class], version = 1)
abstract class Wt_DB : RoomDatabase() {
    abstract fun WDao(): wthrDao
}







