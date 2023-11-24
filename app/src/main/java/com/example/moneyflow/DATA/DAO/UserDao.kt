package com.example.moneyflow.DATA.DAO

import androidx.room.*
import com.example.moneyflow.DATA.ENTITY.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert
    fun insertAll(vararg User: User)

    @Delete
    fun delete(User: User)

    @Update
    fun update(User:User)

    @Query("SELECT * FROM User WHERE id = :id")
    fun get(id:Int):User

    @Query("SELECT SUM(uang) FROM User")
    fun getTotalUang():Int

    @Query("SELECT sum(uang) FROM User where uang >= 0")
    fun getpositive():List<Int>

    @Query("SELECT sum(uang) FROM User where uang < 0")
    fun getnegative():List<Int>


    @Query("SELECT sum(uang) as total FROM User GROUP BY strftime('%Y-%m',tanggal) ")
    fun gettotaluangperbulan(): List<Int>

    @Query("SELECT sum(uang) as total FROM User GROUP BY strftime('%Y-%m-%d',tanggal) ")
    fun gettotaluangperhari(): List<Int>

    @Query("SELECT strftime('%m', tanggal) as namaBulan FROM User GROUP BY strftime('%m', tanggal)")
    fun getNamaBulan(): List<String>
}