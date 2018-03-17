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

package pro.eluzivespikes.jodatimeutils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import pro.eluzivespikes.jodatimeutils.R;

import org.joda.time.DateTime;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * Created by Luca Rossi on 14/07/2017.
 */

public class DateRangePagerAdapter extends PagerAdapter {

    private Context mContext;

    private DateTime mDateFrom;
    private DateTime mDateTo;

    private DatePicker mDateFromPicker;
    private DatePicker mDateToPicker;
    private OnDateRangeChangedListener mOnDateRangeChangedListener;

    DateRangePagerAdapter(Context context) {
        this(context, null, null);
    }

    DateRangePagerAdapter(Context context, DateTime dateFrom, DateTime dateTo) {
        mContext = context;
        if (null == dateFrom && null == dateTo) {
            mDateFrom = DateTime.now();
            mDateTo = mDateFrom.plusDays(1);
        } else if (null == dateFrom) {
            mDateTo = dateTo;
            mDateFrom = dateTo.minusDays(1);
        } else if (null == dateTo || dateTo.isBefore(mDateFrom)) {
            mDateFrom = dateFrom;
            mDateTo = mDateFrom.plusDays(1);
        } else {
            mDateFrom = dateFrom;
            mDateTo = dateTo;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch (position) {
            case 0:
                if (null == mDateFromPicker) {
                    initDateFromPicker(container);
                }
                return mDateFromPicker;
            case 1:
                if (null == mDateToPicker) {
                    initDateToPicker(container);
                }
                return mDateToPicker;
            default:
                return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        switch (position) {
            case 0:
                mDateFromPicker = null;
                break;
            case 1:
                mDateToPicker = null;
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

    private void initDateFromPicker(ViewGroup container) {
        mDateFromPicker = new DatePicker(container.getContext());
        mDateFromPicker.init(mDateFrom);
        mDateFromPicker.setOnDateChanged(new Function2<DatePicker, DateTime, Unit>() {
            @Override
            public Unit invoke(DatePicker datePicker, DateTime dateTime) {
                mDateFrom = dateTime;
                mDateToPicker.setMinDate(0);
                mDateToPicker.setMinDate(mDateFrom.getMillis());
                if (null != mOnDateRangeChangedListener) {
                    mOnDateRangeChangedListener.onDateFromChanged(dateTime);
                }
                return null;
            }
        });
        mDateFromPicker.setMaxDate(mDateTo.getMillis());
        container.addView(mDateFromPicker);
    }


    private void initDateToPicker(ViewGroup container) {
        mDateToPicker = new DatePicker(container.getContext());
        mDateToPicker.init(mDateTo);
        mDateToPicker.setOnDateChanged(new Function2<DatePicker, DateTime, Unit>() {
            @Override
            public Unit invoke(DatePicker datePicker, DateTime dateTime) {
                mDateTo = dateTime;
                mDateFromPicker.setMaxDate(Long.MAX_VALUE);
                mDateFromPicker.setMaxDate(mDateTo.getMillis());
                if (null != mOnDateRangeChangedListener) {
                    mOnDateRangeChangedListener.onDateToChanged(dateTime);
                }
                return null;
            }
        });
        mDateToPicker.setMinDate(mDateFrom.getMillis());
        container.addView(mDateToPicker);
    }

    DateTime getDateFrom() {
        return mDateFrom;
    }

    DateTime getDateTo() {
        return mDateTo;
    }

    public void setmOnDateRangeChangedListener(OnDateRangeChangedListener mOnDateRangeChangedListener) {
        this.mOnDateRangeChangedListener = mOnDateRangeChangedListener;
    }

    interface OnDateRangeChangedListener {
        void onDateFromChanged(DateTime date);

        void onDateToChanged(DateTime date);
    }
}
