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
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.joda.time.DateTime

/**
 * Created by Luca Rossi on 17/03/2018.
 */
class RangePagerAdapter(private var context: Context, var dateFrom: DateTime = DateTime.now().minusDays(1), var dateTo: DateTime = DateTime.now()) : PagerAdapter() {

    private var mPickerFrom: DatePicker? = null
    private var mPickerTo: DatePicker? = null

    var onFromChanged: (dateFrom: DateTime, dateTo: DateTime) -> Unit = { _, _ -> }
    var onToChanged: (dateFrom: DateTime, dateTo: DateTime) -> Unit = { _, _ -> }

    override fun instantiateItem(container: ViewGroup, position: Int): Any? {
        return when (position) {
            0 -> {
                if (mPickerFrom == null) {
                    initPickerFrom(container)
                }
                mPickerFrom
            }
            1 -> {
                if (mPickerTo == null) {
                    initPickerTo(container)
                }
                mPickerTo
            }
            else -> super.instantiateItem(container, position)
        }
    }

    override fun isViewFromObject(view: View?, obj: Any?): Boolean {
        return view === obj
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.range_picker_start)
            1 -> context.getString(R.string.range_picker_end)
            else -> ""
        }
    }

    private fun initPickerFrom(container: ViewGroup) {
        mPickerFrom = LayoutInflater.from(context).inflate(R.layout.joda_date_picker, container, false) as DatePicker
        mPickerFrom?.init(dateFrom)
        mPickerFrom?.maxDate = dateTo.millis
        mPickerFrom?.onDateChangedListener = { _, date ->
            dateFrom = date
            mPickerTo?.minDate = 0
            mPickerTo?.minDate = dateFrom.millis
            onFromChanged.invoke(dateFrom, dateTo)
        }
        container.addView(mPickerFrom)
    }

    private fun initPickerTo(container: ViewGroup) {
        mPickerTo = LayoutInflater.from(context).inflate(R.layout.joda_date_picker, container, false) as DatePicker
        mPickerTo?.init(dateTo)
        mPickerTo?.minDate = dateFrom.millis
        mPickerTo?.onDateChangedListener = { _, date ->
            dateTo = date
            mPickerFrom?.maxDate = Long.MAX_VALUE
            mPickerFrom?.maxDate = dateTo.millis
            onToChanged.invoke(dateFrom, dateTo)
        }
        container.addView(mPickerTo)
    }
}