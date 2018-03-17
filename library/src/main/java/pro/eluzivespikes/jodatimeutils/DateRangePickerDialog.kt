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
import android.content.DialogInterface
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import org.joda.time.DateTime

/**
 * Created by Luca Rossi on 17/03/2018.
 */
class DateRangePickerDialog(context: Context, dateFrom: DateTime = DateTime.now().minusDays(1), dateTo: DateTime = DateTime.now())
    : AlertDialog(context), DialogInterface.OnClickListener {

    var onRangeSelected: (dateFrom: DateTime, dateto: DateTime) -> Unit = { _, _ -> }

    private val mViewPager: ViewPager
    private val mTabLayout: TabLayout
    private val mAdapter: RangePagerAdapter

    init {
        val v = LayoutInflater.from(context).inflate(R.layout.joda_date_range_picker_dialog, null)
        setView(v)

        mViewPager = v.findViewById(R.id.view_pager_ranges)!!
        mTabLayout = v.findViewById(R.id.tabs_date_range)!!

        mAdapter = RangePagerAdapter(context, dateFrom, dateTo)
        mAdapter.onFromChanged = { _, _ -> mViewPager.currentItem = 1 }

        mViewPager.adapter = mAdapter

        mTabLayout.setupWithViewPager(mViewPager)

        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok), this)
        setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(android.R.string.cancel), this)
    }

    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                onRangeSelected.invoke(
                        mAdapter.dateFrom,
                        mAdapter.dateTo
                )
                cancel()
            }
            DialogInterface.BUTTON_NEGATIVE -> cancel()
        }
    }
}