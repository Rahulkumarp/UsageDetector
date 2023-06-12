package com.example.usagedetector.view.chart

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.usagedetector.R
import com.example.usagedetector.databinding.ActivityLineChartBinding
import com.example.usagedetector.model.CPUModel
import com.example.usagedetector.utils.DemoBase
import com.example.usagedetector.utils.IndexAxisValueFormatter
import com.example.usagedetector.utils.MyMarkerView
import com.example.usagedetector.utils.MyXAxisFormatter
import com.example.usagedetector.utils.Utils.Companion.dateToTime
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import dalvik.system.InMemoryDexClassLoader


@AndroidEntryPoint
class LineChartActivity : DemoBase(), OnChartValueSelectedListener {

    lateinit var activityLineChartBinding: ActivityLineChartBinding
    val lineCHartChartViewModel: LineChartViewModel by viewModels()
    lateinit var arrayListCPUModel:ArrayList<CPUModel>
    lateinit var timeArrayList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLineChartBinding = ActivityLineChartBinding.inflate(layoutInflater)
        setContentView(activityLineChartBinding.root)
        activityLineChartBinding.viewmodel = lineCHartChartViewModel
        arrayListCPUModel = arrayListOf()
        timeArrayList = arrayListOf()

        if(intent.getParcelableArrayListExtra<CPUModel>("List") != null) {
            arrayListCPUModel =
                intent.getParcelableArrayListExtra<CPUModel>("List") as ArrayList<CPUModel>
            Log.d("LIST", arrayListCPUModel.toString())

            for (i in 0 until arrayListCPUModel.size) {
                timeArrayList.add(dateToTime(arrayListCPUModel[i].timeDate))
            }
        }
        intiUI()

        activityLineChartBinding.lineChart.setBackgroundColor(Color.WHITE)

        // disable description text
        activityLineChartBinding.lineChart.description.isEnabled = false

        // enable touch gestures
        activityLineChartBinding.lineChart.setTouchEnabled(true)

        // set listeners
        activityLineChartBinding.lineChart.setOnChartValueSelectedListener(this)
        activityLineChartBinding.lineChart.setDrawGridBackground(false)

        // create marker to display box when values are selected
        val mv = MyMarkerView(this, R.layout.custom_marker_view)

        // Set the marker to the chart
        mv.chartView = activityLineChartBinding.lineChart
        activityLineChartBinding.lineChart.marker = mv


        // enable scaling and dragging

        // enable scaling and dragging
        activityLineChartBinding.lineChart.isDragEnabled = true
        activityLineChartBinding.lineChart.setScaleEnabled(true)

        // force pinch zoom along both axis
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        activityLineChartBinding.lineChart.setPinchZoom(true)



        // // X-Axis Style // //

        var xAxis: XAxis = activityLineChartBinding.lineChart.xAxis
         xAxis.position = XAxisPosition.BOTTOM
        xAxis.labelCount = 4
        xAxis.setDrawGridLines(true)
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        xAxis.valueFormatter = MyXAxisFormatter(timeArrayList)


//        xAxis.typeface = tfLight
       // xAxis.granularity = 1f // only intervals of 1 day
      //  xAxis.labelCount = 7


// Set the value formatter

// Set the value formatter

      // xAxis.valueFormatter = IndexAxisValueFormatter(weekdays)



        // vertical grid lines
      //  xAxis.enableGridDashedLine(10f, 10f, 0f)


        var yAxis: YAxis
          // // Y-Axis Style // //
            yAxis = activityLineChartBinding.lineChart.axisLeft
            // disable dual axis (only use LEFT axis)
        activityLineChartBinding.lineChart.axisRight.isEnabled = false
            // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)
            // axis range
            yAxis.axisMaximum = 190f
           // yAxis.axisMinimum = -50f
            yAxis.axisMinimum = 0f




        // // Create Limit Lines // //
        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        llXAxis.typeface = tfRegular

        val ll1 = LimitLine(190f, "Upper Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        ll1.typeface = tfRegular

        val ll2 = LimitLine(0f, "Lower Limit")
        ll2.lineWidth = 1f
        ll2.enableDashedLine(10f, 0f, 0f)
        ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f
        ll2.typeface = tfRegular
        ll2.lineColor = Color.BLACK

        // draw limit lines behind data instead of on top

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)

        // add limit lines
        yAxis.addLimitLine(ll1)
        yAxis.addLimitLine(ll2)
        //xAxis.addLimitLine(llXAxis);

        if(intent.getStringExtra("type").equals("all"))
        {
            setDataForAll(45,180f,yAxis)
        }else{
            setData(45, 180f, yAxis)
        }


        // draw points over time
        activityLineChartBinding.lineChart.animateX(1500)

        // get the legend (only possible after setting data)
        val l: Legend =  activityLineChartBinding.lineChart.legend

        // draw legend entries as lines
        l.form = LegendForm.LINE

    }

    override fun saveToGallery() {

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }

    override fun onNothingSelected() {

    }


    private fun setData(count: Int, range: Float, yaxis : YAxis) {
        val values = ArrayList<Entry>()
//        for (i in 0 until count) {
//            val `val` = (Math.random() * range).toFloat() - 30
//            values.add(Entry(i.toFloat(), `val`, resources.getDrawable(R.drawable.star)))
//        }



        for (i in 0 until arrayListCPUModel.size) {

            if(intent.getStringExtra("type").equals("res")){
                values.add(Entry(i.toFloat(), (arrayListCPUModel[i].RES).substring(0, arrayListCPUModel[i].RES.length -1).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))

            }else if(intent.getStringExtra("type").equals("shr")){
                values.add(Entry(i.toFloat(), (arrayListCPUModel[i].SHR).substring(0, arrayListCPUModel[i].SHR.length -1).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))

            }else if(intent.getStringExtra("type").equals("virt")) {
                yaxis.axisMaximum = 20f
                values.add(Entry(i.toFloat(), (arrayListCPUModel[i].VIRT).substring(0, arrayListCPUModel[i].VIRT.length -1).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))

            }else if(intent.getStringExtra("type").equals("cpu")) {
                yaxis.axisMaximum = 50f
                values.add(Entry(i.toFloat(), (arrayListCPUModel[i].CPU).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))

            }else if(intent.getStringExtra("type").equals("mem")) {
                yaxis.axisMaximum = 20f
                values.add(Entry(i.toFloat(), (arrayListCPUModel[i].MEMPercentage).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))

            }else if(intent.getStringExtra("type").equals("all")) {
                values.add(Entry(i.toFloat(), (arrayListCPUModel[i].RES).substring(0, arrayListCPUModel[i].RES.length -1).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))
            }
        }


        val set1: LineDataSet
        if (activityLineChartBinding.lineChart.data != null &&
            activityLineChartBinding.lineChart.data.dataSetCount > 0
        ) {
            set1 = activityLineChartBinding.lineChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            activityLineChartBinding.lineChart.data.notifyDataChanged()
            activityLineChartBinding.lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "")
            set1.setDrawIcons(false)

            // draw dashed line (graph data line)
            set1.enableDashedLine(10f, 0f, 0f)

            // black lines and points
            set1.color = Color.GREEN
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 2f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 8f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> activityLineChartBinding.lineChart.axisLeft.axisMinimum }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            activityLineChartBinding.lineChart.data = data
        }
    }

    private fun setDataForAll(count: Int, range: Float, yaxis : YAxis) {
        val values = ArrayList<Entry>()
        val values1 = ArrayList<Entry>()
        val values2 = ArrayList<Entry>()
        val values3 = ArrayList<Entry>()
        val values4 = ArrayList<Entry>()
//        for (i in 0 until count) {
//            val `val` = (Math.random() * range).toFloat() - 30
//            values.add(Entry(i.toFloat(), `val`, resources.getDrawable(R.drawable.star)))
//        }



        for (i in 0 until arrayListCPUModel.size) {

            if(intent.getStringExtra("type").equals("all")){
                values.add(Entry(i.toFloat(), (arrayListCPUModel[i].RES).substring(0, arrayListCPUModel[i].RES.length -1).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))
                values1.add(Entry(i.toFloat(), (arrayListCPUModel[i].SHR).substring(0, arrayListCPUModel[i].SHR.length -1).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))
                values2.add(Entry(i.toFloat(), (arrayListCPUModel[i].VIRT).substring(0, arrayListCPUModel[i].VIRT.length -1).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))
                values3.add(Entry(i.toFloat(), (arrayListCPUModel[i].CPU).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))
                values4.add(Entry(i.toFloat(), (arrayListCPUModel[i].MEMPercentage).toFloat(), resources.getDrawable(R.drawable.star), arrayListCPUModel[i]))

            }
        }



            val dataSets = ArrayList<ILineDataSet>()
        var set =  allData(values, Color.CYAN, "RES")
        var set1 = allData(values1, Color.GREEN,"SHR")
        var set2 = allData(values2, Color.YELLOW,"VIRT")
        var set3 = allData(values3, Color.BLUE,"CPU %")
        var set4 = allData(values4,Color.MAGENTA,"MEM %")
            dataSets.add(set) // add the data sets
            dataSets.add(set1) // add the data sets
            dataSets.add(set2) // add the data sets
            dataSets.add(set3) // add the data sets
            dataSets.add(set4) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            activityLineChartBinding.lineChart.data = data
    }

    private fun allData(values :ArrayList<Entry>, color : Int, label: String) : LineDataSet {
        val set1: LineDataSet
        if (activityLineChartBinding.lineChart.data != null &&
            activityLineChartBinding.lineChart.data.dataSetCount > 0
        ) {
            set1 = activityLineChartBinding.lineChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            activityLineChartBinding.lineChart.data.notifyDataChanged()
            activityLineChartBinding.lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, label)
            set1.setDrawIcons(false)

            // draw dashed line (graph data line)
            set1.enableDashedLine(10f, 0f, 0f)

            // black lines and points
            set1.color = color
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 2f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 8f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> activityLineChartBinding.lineChart.axisLeft.axisMinimum }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }

        }
        return set1
    }

    private fun intiUI()
    {
        if(intent.getStringExtra("type").equals("res")){
            activityLineChartBinding.yAxisTitle.text = "RES in MB"
            activityLineChartBinding.xAxisTitle.text = "Time Interval in Sec"
        }else if(intent.getStringExtra("type").equals("shr")){
            activityLineChartBinding.yAxisTitle.text = "SHR in MB"
            activityLineChartBinding.xAxisTitle.text = "Time Interval in Sec"
        }else if(intent.getStringExtra("type").equals("virt")) {
            activityLineChartBinding.yAxisTitle.text = "Virtual Memory in GB"
            activityLineChartBinding.xAxisTitle.text = "Time Interval in Sec"
        }else if(intent.getStringExtra("type").equals("cpu")) {
            activityLineChartBinding.yAxisTitle.text = "CPU Usage in %"
            activityLineChartBinding.xAxisTitle.text = "Time Interval in Sec"
        }else if(intent.getStringExtra("type").equals("mem")) {
            activityLineChartBinding.yAxisTitle.text = "Memory Usage in %"
            activityLineChartBinding.xAxisTitle.text = "Time Interval in Sec"
        }else if(intent.getStringExtra("type").equals("all")) {
            activityLineChartBinding.yAxisTitle.text = "Memory GB/MB"
            activityLineChartBinding.xAxisTitle.text = "Time Interval in Sec"
        }
    }
}