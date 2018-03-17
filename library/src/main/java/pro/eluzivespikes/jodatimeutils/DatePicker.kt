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

package pro.eluzivespikes.jodatimeutils

import android.content.Context
import android.util.AttributeSet
import org.joda.time.DateTime
import android.widget.DatePicker as BasePicker

/**
 * Created by Luca Rossi on 11/03/2018.
 */
class DatePicker @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BasePicker(context, attrs, defStyleAttr), BasePicker.OnDateChangedListener {

    var onDateChangedListener: (picker: DatePicker, date: DateTime) -> Unit =
            { _: DatePicker, _: DateTime -> }

    /**
     * the month is incremented by 1: (0-11 for compatibility with
     * from (0-11 for compatibility with {@link Calendar#MONTH})
     * to (1-12 for compatibility with {@link DateTime} month)
     */
    override fun getMonth(): Int {
        return super.getMonth() + 1
    }

    override fun onDateChanged(p0: BasePicker?, year: Int, month: Int, day: Int) {
        onDateChangedListener(this, DateTime(year, month + 1, day, 0, 0))
    }

    fun init(date: DateTime) {
        super.init(date.year, date.monthOfYear - 1, date.dayOfMonth, this)
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
    fun changeDate(date: DateTime) {
        super.updateDate(date.year, date.monthOfYear - 1, date.dayOfMonth)
    }

    fun getDate(): DateTime {
        return DateTime(year, month, dayOfMonth, 0, 0)
    }
}