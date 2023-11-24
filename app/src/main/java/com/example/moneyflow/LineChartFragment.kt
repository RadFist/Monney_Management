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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class LineChartFragment : Fragment() {
    lateinit var lineChart: LineChart
    lateinit var btnpie: ImageView
    lateinit var btnBar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_line_chart, container, false)
        lineChart = v.findViewById(R.id.lineChart)
        val Database = Database.getInstance(requireContext()).UserDao()
        val perhari = Database.gettotaluangperhari()
        val list:ArrayList<Entry> = ArrayList()


        var check = 0
        try {
            check = perhari.first()
            var j = 0
            for (i in perhari){
                list.add(Entry(j.toFloat(),i.toFloat()))
                j++
            }
        }
        catch (e: Exception) {
            if (e is NullPointerException) {
                list.add(Entry(0f,0f))
            } else {
                Log.e("erro","error cuyyy")
            }
        }


        val LineDataSet= LineDataSet(list,"subject")
        LineDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        LineDataSet.valueTextColor= Color.WHITE

        val LineData= LineData(LineDataSet)
        lineChart.data= LineData
        lineChart.invalidate()
        lineChart.animateY(2000)

        btnpie = v.findViewById(R.id.pie_from_line)
        btnpie.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fillFragment, diagram_Fragment())
                .commit()
        }

        btnBar = v.findViewById(R.id.bar_from_line)
        btnBar.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fillFragment, BarChartFragment())
                .commit()
        }





        return v
    }
 }
