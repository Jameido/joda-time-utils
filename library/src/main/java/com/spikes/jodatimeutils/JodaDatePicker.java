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

package com.spikes.jodatimeutils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.DatePicker;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Created by Luca Rossi on 13/07/2017.
 */

public class JodaDatePicker extends DatePicker
implements DatePicker.OnDateChangedListener {

    private OnDateChangedListener mOnDateChangedListener;

    public JodaDatePicker(Context context) {
        super(context);
    }

    public JodaDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JodaDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * the month is incremented by 1: (0-11 for compatibility with
     * from (0-11 for compatibility with {@link Calendar#MONTH})
     * to (1-12 for compatibility with {@link DateTime} month)
     */
    @Override
    public int getMonth() {
        return super.getMonth() + 1;
    }

    public DateTime getDate() {
        return new DateTime(getYear(), getMonth(), getDayOfMonth(), 0, 0);
    }

    /**
     * Inits the {@link DatePicker}.
     *
     * year        the year
     * monthOfYear the month (1-12 for compatibility with
     *                    {@link DateTime} month)
     * dayOfMonth  the day of month (1-31, depending on month)
     *                    <p>
     *                    the month is decremented by 1:
     *                    from (1-12 for compatibility with {@link DateTime} month)
     *                    to (0-11 for compatibility with {@link Calendar#MONTH})
     */
    public void init(DateTime date) {
        init(date, null);
    }


    public void init(DateTime date, OnDateChangedListener onDateChangedListener) {
        if(date == null){
            date = new DateTime();
        }
        super.init(date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth(), this);
        mOnDateChangedListener = onDateChangedListener;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        if(null != mOnDateChangedListener){
            mOnDateChangedListener.onDateChanged(this, new DateTime(getYear(), getMonth(), getDayOfMonth(), 0, 0));
        }
    }


    /**
     * Sets the current date.
     *
     * year       the year
     * month      the month (1-12 for compatibility with
     *                   {@link DateTime} month)
     *dayOfMonth the day of month (1-31, depending on month)
     *                   <p>
     *                   the month is decremented by 1:
     *                   from (1-12 for compatibility with {@link DateTime} month)
     *                   to (0-11 for compatibility with {@link Calendar#MONTH})
     */
    public void updateDate(DateTime date) {
        super.updateDate(date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth());
    }

    public interface OnDateChangedListener{
        void onDateChanged(JodaDatePicker datePicker, DateTime date);
    }
}
