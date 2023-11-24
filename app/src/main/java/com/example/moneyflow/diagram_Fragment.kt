package com.example.moneyflow

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.moneyflow.DATA.Database
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class diagram_Fragment : Fragment() {
    lateinit var pieChart: PieChart
    lateinit var btn_barChart:ImageView
    lateinit var btn_linechart:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_diagram_, container, false)

        pieChart =v.findViewById(R.id.pie_chart)

        val list:ArrayList<PieEntry> = ArrayList()
        val database = Database.getInstance(requireContext()).UserDao()
        val positive : List<Int> = database.getpositive()
        var postamp = 0
        try {
            postamp = positive.first()}
        catch (e: Exception) {
            if (e is NullPointerException) {
                postamp = 0
            } else {
                Log.e("erro","error cuyyy")
            }
        }

        val negative : List<Int> = database.getnegative()
        var negtamp = 0
        try {
            negtamp = negative.first()}
        catch (e: Exception) {
            if (e is NullPointerException) {
                negtamp = 0
            } else {
                Log.e("erro","error cuyyy")
            }
        }
        val test =database.gettotaluangperbulan()
        Log.d("ini", "onCreate: ${test}")



        Log.d("ini", "onCreate: ${negtamp}")
        list.add(PieEntry(negtamp.toFloat()*-1))
        list.add(PieEntry(postamp.toFloat()))




        val pieDataSet= PieDataSet(list,": [pengeluaran,pemasukan]")

        pieDataSet.setColors(Color.RED, Color.GREEN,255)
        pieDataSet.valueTextColor= Color.WHITE
        pieDataSet.valueTextSize=15f


        val pieData= PieData(pieDataSet)

        pieChart.data= pieData

        pieChart.description.text= "total pengeluaran dan pemasukan"
        pieChart.description.textSize = 20f
        pieChart.centerText="List"
        pieChart.animateY(2000)

        btn_barChart=v.findViewById(R.id.pietobar)
        btn_barChart.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fillFragment, BarChartFragment())
                .commit()

        }
        btn_linechart = v.findViewById(R.id.linechat_btnfrom_piechart)
        btn_linechart.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fillFragment, LineChartFragment())
                .commit()

        }


        return v


    }
}


