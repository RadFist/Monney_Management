package com.example.moneyflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneyflow.DATA.Database
import com.example.moneyflow.DATA.ENTITY.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class EditActivity : AppCompatActivity() {
    lateinit var keterangan: EditText
    lateinit var harga: EditText
    lateinit var tanggal: EditText
    lateinit var btnSave: Button
    private lateinit var database: Database

    fun lateinitComp(){
        keterangan = findViewById(R.id.edit_keterangan)
        harga = findViewById(R.id.edit_harga)
        tanggal = findViewById(R.id.edit_tanggal)
        btnSave = findViewById(R.id.btn_save)
    }

    fun formatDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        return date.format(formatter)
    }


    fun convertStringToDate(value: String): LocalDateTime {
        val localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-d"))
        val midnight = LocalTime.MIDNIGHT
        val localDateTime = LocalDateTime.of(localDate, midnight)
        return localDateTime
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)


        lateinitComp()
        database = Database.getInstance(applicationContext)

        var intent = intent.extras
        if (intent!=null){
            val id = intent.getInt("id",0)
            var user = database.UserDao().get(id)

            keterangan.setText(user.Keterangan)
            harga.setText(user.uang.toString())
            tanggal.setText(formatDate(user.tanggal))

        }

        btnSave.setOnClickListener() {
            if ((keterangan.text.isNotEmpty()) && (keterangan.text.isNotEmpty()) && (tanggal.text.isNotEmpty())) {
                if (intent != null) {
                    database.UserDao().update(
                        User(
                            intent.getInt("id", 0),
                            keterangan.text.toString(),
                            harga.text.toString().toInt(),
                            convertStringToDate(tanggal.text.toString())
                        )
                    )
                    finish()
                } else {
                    try {
                        database.UserDao().insertAll(
                            User(
                                null,
                                keterangan.text.toString(),
                                harga.text.toString().toInt(),
                                convertStringToDate(tanggal.text.toString())
                            )
                        )
                        finish()
                    }catch (e:DateTimeParseException){
                        Toast.makeText(applicationContext, "salah format tanggal", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if ((keterangan.text.isNotEmpty()) && (harga.text.isNotEmpty()) && (tanggal.text.isEmpty())) {
                database.UserDao().insertAll(
                    User(
                        null,
                        keterangan.text.toString(),
                        harga.text.toString().toInt(),
                        LocalDateTime.now()
                    )
                )

                finish()
            } else {
                Toast.makeText(applicationContext, "belom disii woi", Toast.LENGTH_LONG).show()
            }
        }
    }
}