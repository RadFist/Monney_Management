package com.example.moneyflow.DATA

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moneyflow.DATA.DAO.UserDao
import com.example.moneyflow.DATA.ENTITY.User

@androidx.room.Database(entities = [User::class], version = 1)
@TypeConverters(Converter::class)
abstract class Database:RoomDatabase(){
    abstract fun UserDao():UserDao

    companion object{
        private var instance:Database? = null

        fun getInstance(context: Context):Database{
            if (instance==null){
                instance= Room.databaseBuilder(context,Database::class.java,"monney_flow")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}