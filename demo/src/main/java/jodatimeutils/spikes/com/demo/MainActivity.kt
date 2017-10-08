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
                JodaDateRangePickerDialog.OnRangeSelectedListener { startDate, endDate ->
                    mDateFrom = startDate
                    mDateTo = endDate
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
