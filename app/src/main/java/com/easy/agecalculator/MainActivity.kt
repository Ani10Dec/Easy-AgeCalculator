package com.easy.agecalculator

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var tvHeading: TextView
    lateinit var tvSelectedDt: TextView
    lateinit var tvAgeResult: TextView
    lateinit var btnChange: Button
    lateinit var tvStatus: TextView
    var state = "minutes"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvHeading = findViewById(R.id.heading_tv)
        tvSelectedDt = findViewById(R.id.tv_selected_dt)
        tvAgeResult = findViewById(R.id.tv_age_result)
        btnChange = findViewById(R.id.btn_change)
        tvStatus = findViewById(R.id.tv_status)
        val btnDtPicker = findViewById<Button>(R.id.btn_change_date)

        // Select Date
        btnDtPicker.setOnClickListener {
            datePickerDialog(state)
        }

        btnChange.setOnClickListener {
            showTypeDialog()
        }
    }

    private fun showTypeDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        builder.setView(view)
        val dialog = builder.show()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        val btnDay = dialog.findViewById<Button>(R.id.btn_day)
        val btnMinutes = dialog.findViewById<Button>(R.id.btn_minutes)
        val btnSecond = dialog.findViewById<Button>(R.id.btn_second)

        btnDay?.setOnClickListener {
            Toast.makeText(this, "Age result in Days", Toast.LENGTH_SHORT).show()
            tvHeading.text = "Calculate Your Age \n In Days"
            tvStatus.text = "In days till now"
            tvSelectedDt.text = resources.getString(R.string.select_dt)
            tvAgeResult.text = ""
            state = "days"
            dialog.dismiss()
        }
        btnMinutes?.setOnClickListener {
            Toast.makeText(this, "Age result in Minutes", Toast.LENGTH_SHORT).show()
            tvHeading.text = "Calculate Your Age \n In Minutes"
            tvStatus.text = "In minutes till now"
            tvSelectedDt.text = resources.getString(R.string.select_dt)
            tvAgeResult.text = ""
            state = "minutes"
            dialog.dismiss()
        }
        btnSecond?.setOnClickListener {
            Toast.makeText(this, "Age result in Seconds", Toast.LENGTH_SHORT).show()
            tvHeading.text = "Calculate Your Age \n In Seconds"
            tvStatus.text = "In seconds till now"
            tvSelectedDt.text = resources.getString(R.string.select_dt)
            tvAgeResult.text = ""
            state = "seconds"
            dialog.dismiss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun datePickerDialog(type: String) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val limit = DatePickerDialog(
            this, { it, selectedYear, selectedMonth, selectedDay ->
                var selectedDate = "null"
                if (selectedMonth < 10 && selectedDay < 10) {
                    selectedDate = "0$selectedDay/0${selectedMonth + 1}/$selectedYear"
                } else if (selectedMonth < 10) {
                    selectedDate = "$selectedDay/0${selectedMonth + 1}/$selectedYear"
                } else if (selectedDay < 10) {
                    selectedDate = "0$selectedDay/${selectedMonth + 1}/$selectedYear"
                }
                tvSelectedDt.text = selectedDate

                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                when (type) {
                    "days" -> changeToDays(date)
                    "minutes" -> changeDtToMinutes(date)
                    "seconds" -> changeDtToSeconds(date)
                }
            },     // to show current
            year,   // year
            month,  // month
            day     // day in the dialog
        )
        limit.datePicker.maxDate = Date().time - 86400000
        limit.show()
    }

    private fun changeToDays(date: String) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val dateFormat = sdf.parse(date)
        val selectedDtIntoTm = dateFormat!!.time / 60000
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        val currentDateToMinutes = currentDate!!.time / 60000
        val result =
            (currentDateToMinutes - selectedDtIntoTm) / (60 * 24)
        tvAgeResult.text = result.toString()
    }

    private fun changeDtToSeconds(date: String) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val dateFormat = sdf.parse(date)
        val selectedDtIntoTm = dateFormat!!.time / 1000
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        val currentDateToMinutes = currentDate!!.time / 1000
        val result =
            (currentDateToMinutes - selectedDtIntoTm)
        tvAgeResult.text = result.toString()
    }

    private fun changeDtToMinutes(date: String) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val dateFormat = sdf.parse(date)
        val selectedDtIntoTm = dateFormat!!.time / 60000
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        val currentDateToMinutes = currentDate!!.time / 60000
        val result =
            (currentDateToMinutes - selectedDtIntoTm)
        tvAgeResult.text = result.toString()
    }
}