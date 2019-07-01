package com.fitsoft.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.fitsoft.Activities.MainActivity

import com.fitsoft.R
import com.fitsoft.Utils.Funciones
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_my_diet.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MyDietFragment : Fragment() {

    private var mContext: Context? = null
    private var linearCalendarRange: View? = null
    private var dialogCalendarRange: AlertDialog? = null
    private var fecha: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar!!.title = getString(R.string.menu_diet)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_diet, container, false)
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

        initHorizontalCalendar()

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

    private fun initHorizontalCalendar(){
        var endDate  = Calendar.getInstance()
        endDate.add(Calendar.MONTH,1)

        /** start before 1 month from now */
        var startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH,0)

        val horizontalCalendar = HorizontalCalendar.Builder(activity, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(5)
            .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {

                Log.d("Posicion",position.toString())
            }
        }

    }
}
