/*
 * MIT License
 *
 * Copyright (c) 2018 Luca Rossi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.spikes.jodatimeutils

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import org.joda.time.DateTime

/**
 * Created by Luca Rossi on 11/03/2018.
 */
class DatePickerDialog(context: Context, date: DateTime = DateTime())
    : AlertDialog(context), DialogInterface.OnClickListener {

    val EXTRA_DATE: String = "EXTRA_DATE"

    private var mDatePicker: DatePicker
    var onDatePicked: (picker: DatePicker, date: DateTime) -> Unit =
            { _: DatePicker, _: DateTime -> }

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.joda_date_picker_dialog, null)
        setView(v)
        mDatePicker = v.findViewById(R.id.date_picker)!!
        mDatePicker.init(date)
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok), this)
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(android.R.string.cancel), this)
    }

    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                // Clearing focus forces the dialog to commit any pending
                // changes, e.g. typed text in a NumberPicker.
                mDatePicker.clearFocus()
                onDatePicked(mDatePicker, mDatePicker.getDate())
                cancel()
            }
            DialogInterface.BUTTON_NEGATIVE -> cancel()
        }
    }

    override fun onSaveInstanceState(): Bundle {
        val stateBundle = super.onSaveInstanceState()
        stateBundle.putLong(EXTRA_DATE, mDatePicker.getDate().millis)
        return stateBundle
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_DATE)) {
            mDatePicker.changeDate(DateTime(savedInstanceState.getLong(EXTRA_DATE)))
        }
    }

    fun changeDate(date: DateTime) {
        mDatePicker.changeDate(date)
    }
}