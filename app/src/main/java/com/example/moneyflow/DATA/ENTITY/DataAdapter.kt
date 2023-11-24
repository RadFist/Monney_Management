package com.example.moneyflow.DATA.ENTITY

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverters
import com.example.moneyflow.DATA.Converter
import com.example.moneyflow.DATA.Database
import com.example.moneyflow.EditActivity
import com.example.moneyflow.R
import kotlinx.coroutines.currentCoroutineContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@TypeConverters(Converter::class)
class DataAdapter(var List: MutableList<User>) : RecyclerView.Adapter<DataAdapter.viewholder>()  {
    inner class viewholder(view: View): RecyclerView.ViewHolder(view) {

        var keterangan: TextView
        var uang: TextView
        var tanggal: TextView
        var edit: ImageView
        var delete : ImageView
        var db = Database.getInstance(view.context).UserDao()
        var card:CardView

        init {
            keterangan = view.findViewById(R.id.txt_keterangan)
            uang = view.findViewById(R.id.txt_Uang)
            tanggal = view.findViewById(R.id.txt_tanggal)
            edit = view.findViewById(R.id.btn_edit)
            delete = view.findViewById(R.id.btn_hapus)
            card = view.findViewById(R.id.card_all)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_card,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return List.size
    }
    override fun onBindViewHolder(holder: viewholder, position: Int) {

        if (List[position].uang.toString().toInt() < 0){
            holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.red))
        }
        else{
            holder.card.setCardBackgroundColor(holder.itemView.context.resources.getColor(R.color.green))
        }

        holder.keterangan.text = List[position].Keterangan
        holder.uang.text = List[position].uang.toString()
        holder.tanggal.text = formatDate(List[position].tanggal)
        holder.edit.setOnClickListener{
            val intent= Intent(holder.itemView.context, EditActivity::class.java).apply {
                this.putExtra("id",List[position].id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            val dialog = AlertDialog.Builder(holder.itemView.context)
            dialog.setTitle("Hapus")
            dialog.setMessage("anda yakin untuk menghapus?")
            dialog .setPositiveButton("Ya") { _, _ ->
                holder.db.delete(List[position])
                List.removeAt(position)
                notifyDataSetChanged()
            }

            dialog.setNegativeButton("Batal") { _, _ ->
            }

            val alertDialog = dialog.create()
            alertDialog.show()

        }
    }

    fun formatDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        return date.format(formatter)
    }
}