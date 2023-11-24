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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.math.log


class BarChartFragment : Fragment() {

    lateinit var barChart: BarChart
    lateinit var valueString : List<String>
    lateinit var btnpie : ImageView
    lateinit var btnline : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bar_chart, container, false)
        barChart=v.findViewById(R.id.bar_chart)
        barChart.axisRight.setDrawLabels(false)

        val database = Database.getInstance(requireContext()).UserDao()
        val totaluangbulan = database.gettotaluangperbulan()

        val testbulan = database.getNamaBulan()
        valueString = converterbulan(testbulan)

        val list: ArrayList<BarEntry> = ArrayList()
        var check = 0
        try {
            check = totaluangbulan.first()
            var j = 0
            for (i in totaluangbulan){
                list.add(BarEntry(j.toFloat(),i.toFloat()))
                j++
            }
        }
        catch (e: Exception) {
            if (e is NullPointerException) {
                list.add(BarEntry(0f,0f))
            } else {
                Log.e("erro","error cuyyy")
            }
        }


        val yAxis =barChart.axisLeft

        yAxis.axisLineWidth=2f
        yAxis.axisLineColor = Color.WHITE
        yAxis.labelCount = 10

        val barDataSet= BarDataSet(list,"subject")

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        barDataSet.valueTextColor= Color.WHITE

        val barData= BarData(barDataSet)
        barChart.data= barData

        barChart.description.isEnabled=false
        barChart.invalidate()

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(valueString)
        barChart.xAxis.axisLineColor = Color.WHITE
        barChart.xAxis.textColor = Color.WHITE

        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.granularity = 1f
        barChart.xAxis.isGranularityEnabled = true
        barChart.animateY(2000)

        btnpie =v.findViewById(R.id.piechart_btnfrom_barchart)
        btnpie.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fillFragment, diagram_Fragment())
                .commit()
        }
        btnline = v.findViewById(R.id.linechat_btnfrom_barchart)
        btnline.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fillFragment,LineChartFragment())
                .commit()
        }


        return v
    }
    fun converterbulan(value:List<String>):List<String> {
        val result: MutableList<String> = mutableListOf()
        for (i in value) {
            when (i) {
                "01" -> result.add("jan")
                "02" -> result.add("feb")
                "03" -> result.add("mar")
                "04" -> result.add("april")
                "05" -> result.add("mei")
                "06" -> result.add("jun")
                "07" -> result.add("jul")
                "08" -> result.add("agust")
                "09" -> result.add("sept")
                "10" -> result.add("okt")
                "11" -> result.add("nov")
                "12" -> result.add("des")
            }
        }
            return result
    }
}

