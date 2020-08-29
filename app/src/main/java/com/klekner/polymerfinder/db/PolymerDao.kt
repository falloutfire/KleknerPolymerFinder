package com.klekner.polymerfinder.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.klekner.polymerfinder.data.Polymer
import com.klekner.polymerfinder.data.PolymerBlend
import com.klekner.polymerfinder.data.PolymerWithBlends


@Dao
interface PolymerDao {

    @Query("SELECT * FROM Polymer ORDER BY id ASC")
    fun getPolymers(): LiveData<List<Polymer>>

    @Query("SELECT * FROM Polymer ORDER BY id ASC")
    fun getPolymersList(): List<Polymer>

    @Query("SELECT * FROM Polymer WHERE id = :id")
    fun getPolymer(id: Long): LiveData<Polymer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPolymers(vararg items: Polymer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPolymer(items: Polymer)

    @Delete
    fun deletePolymers(keeps: Polymer)

    @Query("SELECT * FROM Polymer INNER join PolymerBlend on Polymer.id = PolymerBlend.polymerId where PolymerBlend.polymerId = :id")
    fun getPolymerWithBlends(id: Long): LiveData<PolymerWithBlends?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBlends(vararg items: PolymerBlend)
}
