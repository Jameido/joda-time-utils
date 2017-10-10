/*
 * Copyright 2017.  Luca Rossi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */

package jodatimeutils.spikes.com.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.AdapterView
import com.spikes.jodatimeutils.JodaDatePickerDialog
import com.spikes.jodatimeutils.JodaDateRangePickerDialog
import java.util.*
import android.widget.AdapterView.OnItemSelectedListener
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private var mDateFormatsAdapter: DateFormatsAdapter? = null
    private var mSpinnerDateFormat: AppCompatSpinner? = null

    private var mTextSelectedDate: AppCompatTextView? = null
    private var mTextSelectedRangeFrom: AppCompatTextView? = null
    private var mTextSelectedRangeTo: AppCompatTextView? = null

    private var mSelectedDate: DateTime = DateTime.now()
    private var mDateFrom: DateTime = DateTime.now()
    private var mDateTo: DateTime = DateTime.now().plusDays(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextSelectedDate = findViewById(R.id.text_selected_date)
        mTextSelectedRangeFrom = findViewById(R.id.text_selected_range_from)
        mTextSelectedRangeTo = findViewById(R.id.text_selected_range_to)

        findViewById<AppCompatButton>(R.id.button_date_picker)
                .setOnClickListener({ openDatePicker() })
        findViewById<AppCompatButton>(R.id.button_range_picker)
                .setOnClickListener({ openRangePicker() })

        mSpinnerDateFormat = findViewById(R.id.spinner_date_format)
        setupDateFormatSpinner()
    }

    private fun setupDateFormatSpinner() {
        mDateFormatsAdapter = DateFormatsAdapter(this, resources.getStringArray(R.array.date_formats))
        mSpinnerDateFormat?.adapter = mDateFormatsAdapter
        mSpinnerDateFormat?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val formatter = DateTimeFormat.forPattern(mDateFormatsAdapter?.getItem(position))
                changeAllTextDates(formatter)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                val formatter = DateTimeFormat.forPattern(mDateFormatsAdapter?.getItem(0))
                changeAllTextDates(formatter)
            }

        }
    }

    private fun openDatePicker() {
        JodaDatePickerDialog(this,
                JodaDatePickerDialog.OnDateSetListener { _, date ->
                    mSelectedDate = date
                    val formatter = DateTimeFormat.forPattern(mDateFormatsAdapter?.getItem(mSpinnerDateFormat!!.selectedItemPosition))
                    changeTextSelected(formatter)
                },
                mSelectedDate)
                .show()
    }

    private fun openRangePicker() {
        JodaDateRangePickerDialog(this,
                JodaDateRangePickerDialog.OnRangeSelectedListener { dateFrom, dateTo ->
                    mDateFrom = dateFrom
                    mDateTo = dateTo
                    val formatter = DateTimeFormat.forPattern(mDateFormatsAdapter?.getItem(mSpinnerDateFormat!!.selectedItemPosition))
                    changeTextFrom(formatter)
                    changeTextTo(formatter)
                },
                mDateFrom,
                mDateTo)
                .show()
    }

    private fun changeAllTextDates(formatter: DateTimeFormatter) {
        changeTextSelected(formatter)
        changeTextFrom(formatter)
        changeTextTo(formatter)
    }

    private fun changeTextSelected(formatter: DateTimeFormatter) {
        mTextSelectedDate?.text = formatter.print(mSelectedDate)
    }

    private fun changeTextFrom(formatter: DateTimeFormatter) {
        mTextSelectedRangeFrom?.text = String.format(Locale.getDefault(), getString(R.string.range_from), formatter.print(mDateFrom))
    }

    private fun changeTextTo(formatter: DateTimeFormatter) {
        mTextSelectedRangeTo?.text = String.format(Locale.getDefault(), getString(R.string.range_to), formatter.print(mDateTo))
    }
}
