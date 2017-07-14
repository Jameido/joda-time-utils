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
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;

/**
 * Created by Luca Rossi on 14/07/2017.
 */

public class DateRangePagerAdapter extends PagerAdapter {

    private Context mContext;

    private DateTime mStartDate;
    private DateTime mEndDate;

    private JodaDatePicker mStartDatePicker;
    private JodaDatePicker mEndDatePicker;

    public DateRangePagerAdapter(Context context) {
        this(context, null, null);
    }

    public DateRangePagerAdapter(Context context, DateTime startDate, DateTime endDate) {
        mContext = context;
        if (null == startDate && null == endDate) {
            mStartDate = DateTime.now();
            mEndDate = mStartDate.plusDays(1);
        } else if (null == startDate) {
            mEndDate = endDate;
            mStartDate = endDate.minusDays(1);
        } else if (null == endDate || endDate.isBefore(mStartDate) ) {
            mStartDate = startDate;
            mEndDate = mStartDate.plusDays(1);
        }else {
            mStartDate = startDate;
            mEndDate = endDate;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch (position) {
            case 0:
                if (null == mStartDatePicker) {
                    initStartDatePicker(container);
                }
                return mStartDatePicker;
            case 1:
                if (null == mEndDatePicker) {
                    initEndDatePicker(container);
                }
                return mEndDatePicker;
            default:
                return null;
        }
    }

    private void initStartDatePicker(ViewGroup container) {
        mStartDatePicker = new JodaDatePicker(container.getContext());
        mStartDatePicker.init(mStartDate, new JodaDatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(JodaDatePicker datePicker, DateTime date) {
                mStartDate = date;
                mEndDatePicker.setMinDate(0);
                mEndDatePicker.setMinDate(mStartDate.getMillis());
            }
        });
        mStartDatePicker.setMaxDate(mEndDate.getMillis());
        container.addView(mStartDatePicker);
    }


    private void initEndDatePicker(ViewGroup container) {
        mEndDatePicker = new JodaDatePicker(container.getContext());
        mEndDatePicker.init(mEndDate, new JodaDatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(JodaDatePicker datePicker, DateTime date) {
                mEndDate = date;
                mStartDatePicker.setMaxDate(Long.MAX_VALUE);
                mStartDatePicker.setMaxDate(mEndDate.getMillis());
            }
        });
        mEndDatePicker.setMinDate(mStartDate.getMillis());
        container.addView(mEndDatePicker);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        switch (position) {
            case 0:
                mStartDatePicker = null;
                break;
            case 1:
                mEndDatePicker = null;
                break;
        }
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.range_picker_start);
            case 1:
                return mContext.getString(R.string.range_picker_end);
            default:
                return "";
        }
    }

    public DateTime getStartDate() {
        return mStartDate;
    }

    public DateTime getEndDate() {
        return mEndDate;
    }
}
