package com.fitsoft.Fragments.MyDiet

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.fitsoft.Adapters.FoodAdapter

import com.fitsoft.R
import com.fitsoft.Utils.Funciones
import kotlinx.android.synthetic.main.fragment_foods_day.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FoodsDayFragment : Fragment() {

    private var mContext: Context? = null
    private var linearCalendarRange: View? = null
    private var dialogCalendarRange: AlertDialog? = null
    private var fecha: String = ""
    val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    var foods: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foods_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            fecha =  current.format(formatter)
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy")
            fecha = formatter.format(date)
        }

        //initHorizontalCalendar()
        val adapter = ArrayAdapter.createFromResource(
            mContext,
            R.array.foods_array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        foods_spinner.adapter = adapter
        initList()

        //Calendar Range View
        linearCalendarRange = LayoutInflater.from(context).inflate(R.layout.dialog_calendar_range, null, false)
        dialogCalendarRange = Funciones.calendarAlert(mContext,linearCalendarRange).create()
        var calendarRange: DateRangeCalendarView = linearCalendarRange!!.findViewById(R.id.calendar_range)
//        calendarRange.state().edit()
//            .setMinimumDate(CalendarDay.from(2019, 6, 28))
//            .setMaximumDate((CalendarDay.from(2019, 7, 3)))
//            .commit()

        openCalendar.setOnClickListener {
            dialogCalendarRange!!.show()
        }
    }

    private  fun initList(){
        foods.add("El Dani se la come")
        foods.add("El Dani se la come entera")
        foods.add("El Dani se la come")
        foods.add("El Dani se la come entera")
        foods.add("El Dani se la come")
        foods.add("El Dani se la come entera")
        foods.add("El Dani se la come")
        foods.add("El Dani se la come entera")

        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.hasFixedSize()
        recyclerView.adapter = FoodAdapter(foods)
    }

//    private fun initHorizontalCalendar(){
//        var date = Calendar.getInstance()
//        var endDate  = Calendar.getInstance()
//        endDate.add(Calendar.MONTH,1)
//        /** start before 1 month from now */
//        var startDate = Calendar.getInstance()
//        startDate.add(Calendar.MONTH,0)
//
//        val horizontalCalendar = HorizontalCalendar.Builder(activity, R.id.calendarView)
//            .range(startDate, endDate)
//            .datesNumberOnScreen(1)
//            .configure()
//                .showTopText(false)
//            .end()
//            .defaultSelectedDate(date)
//            .build()
//
//        horizontalCalendar.goToday(true)
//
//        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
//            override fun onDateSelected(date: Calendar?, position: Int) {
//                if(position == 0)
//                    btn_prev_day.isEnabled = false
//                else
//                    btn_prev_day.isEnabled = true
//
//                Log.d("Posicion",position.toString())
//                Log.d("Fecha",df.format(date!!.time))
//            }
//
//            override fun onCalendarScroll(calendarView: HorizontalCalendarView?, dx: Int, dy: Int) {
//                calendarView!!.positionOfCenterItem
//            }
//        }
//
//        btn_next_day.setOnClickListener {
//
//            Log.d("Fecha next",df.format(horizontalCalendar.selectedDate))
//        }
//    }
}
