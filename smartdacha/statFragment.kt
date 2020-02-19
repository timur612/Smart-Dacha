package com.phtl.itlab.smartdacha

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.jjoe64.graphview.GraphView


import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import com.macroyau.thingspeakandroid.ThingSpeakChannel
import com.macroyau.thingspeakandroid.model.ChannelFeed
import kotlinx.android.synthetic.main.stat_fragment.*
import java.lang.Math.PI
import java.lang.Math.sqrt
import kotlin.math.pow
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import lecho.lib.hellocharts.view.LineChartView
import lecho.lib.hellocharts.model.Viewport;
import java.util.*
import lecho.lib.hellocharts.model.LineChartData
import com.macroyau.thingspeakandroid.ThingSpeakLineChart
import android.graphics.Color.parseColor

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.*

class statFragment : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_songs -> {
                //toolbar.title = "Stats"
                val intent = Intent(this, statFragment::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_albums -> {
                val intent = Intent(this, mainFragment::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
    lateinit var series1: LineGraphSeries<DataPoint>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stat_fragment)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)



        val database = FirebaseDatabase.getInstance()
        val tempText = database.getReference("temp")
        val humText = database.getReference("hum")

        tempText.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var value = dataSnapshot.getValue()

                series1 = LineGraphSeries<DataPoint>()
                 var d =value.toString()
                var x:Double = d.toDouble()

                series1.appendData(DataPoint(1.0,28.0),true,40)
                series1.appendData(DataPoint(2.0,25.0),true,40)
                series1.appendData(DataPoint(3.0,26.0),true,40)
                series1.appendData(DataPoint(4.0,21.0),true,40)
                series1.appendData(DataPoint(5.0,20.0),true,40)
                series1.appendData(DataPoint(6.0,24.0),true,40)
                series1.appendData(DataPoint(7.0,23.0),true,40)

                plot.viewport.isXAxisBoundsManual = true
                plot.viewport.setMaxX(7.0)
                plot.viewport.setMinX(1.0)

                plot.viewport.isYAxisBoundsManual = true
                plot.viewport.setMaxY(30.0)
                plot.viewport.setMinY(0.0)


                plot.addSeries(series1)

                plot.viewport.isScalable = true
                plot.viewport.isScrollable = true
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value



            }
        })
        humText.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
                series1 = LineGraphSeries<DataPoint>()
                var d =value.toString()
                var x:Double = d.toDouble()
                var y:Double = 0.0
                val sigma = 3.0
                val media = 3.0

                val numbers: Array<Int> = arrayOf(15, 21,25 , 18, 16, 20,17)

               /* for(i in numbers){
                    series1.appendData(DataPoint(i.toDouble(),numbers[2].toDouble()),true,40)
                }*/
                series1.appendData(DataPoint(1.0,35.0),true,40)
                series1.appendData(DataPoint(2.0,22.0),true,40)
                series1.appendData(DataPoint(3.0,24.0),true,40)
                series1.appendData(DataPoint(4.0,25.0),true,40)
                series1.appendData(DataPoint(5.0,23.0),true,40)
                series1.appendData(DataPoint(6.0,23.0),true,40)
                series1.appendData(DataPoint(7.0,38.0),true,40)

                plot2.viewport.isXAxisBoundsManual = true
                plot2.viewport.setMaxX(7.0)
                plot2.viewport.setMinX(1.0)

                plot2.viewport.isYAxisBoundsManual = true
                plot2.viewport.setMaxY(40.0)
                plot2.viewport.setMinY(0.0)

                plot2.addSeries(series1)
                plot2.viewport.isScalable = true
                plot2.viewport.isScrollable = true
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value

            }
        })
    }

}
