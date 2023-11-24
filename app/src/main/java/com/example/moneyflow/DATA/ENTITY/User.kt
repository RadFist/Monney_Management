package com.example.moneyflow.DATA.ENTITY

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)var id : Int? = null,
    @ColumnInfo("Keterangan")var Keterangan:String?,
    @ColumnInfo("uang")var  uang:Int?,
    @ColumnInfo("tanggal")var tanggal: LocalDateTime

)
data class getDatadateandMonney(
    @ColumnInfo("total")var  uang:Int?,
    @ColumnInfo("bulan")var tanggal: String
)
