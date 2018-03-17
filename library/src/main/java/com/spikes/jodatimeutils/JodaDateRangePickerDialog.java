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
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import org.joda.time.DateTime;

/**
 * Created by Jameido on 14/07/2017.
 */

public class JodaDateRangePickerDialog extends AlertDialog
        implements DialogInterface.OnClickListener, DateRangePagerAdapter.OnDateRangeChangedListener {

    private static final String EXTRA_START = "EXTRA_START";
    private static final String EXTRA_END = "EXTRA_END";

    private ViewPager mViewPagerRange;
    private TabLayout mTabLayout;
    private DateRangePagerAdapter mDateRangePagerAdapter;
    private OnRangeSelectedListener mOnRangeSelectedListener;

    public JodaDateRangePickerDialog(@NonNull Context context) {
        this(context, null);
    }

    public JodaDateRangePickerDialog(@NonNull Context context, @Nullable JodaDateRangePickerDialog.OnRangeSelectedListener listener) {
        this(context, listener, null, null);
    }

    public JodaDateRangePickerDialog(@NonNull Context context, @Nullable DateTime dateFrom, @Nullable DateTime dateTo) {
        this(context, null, dateFrom, dateTo);
    }

    public JodaDateRangePickerDialog(@NonNull Context context, @Nullable JodaDateRangePickerDialog.OnRangeSelectedListener listener, @Nullable DateTime dateFrom, @Nullable DateTime dateTo) {
        this(context, 0, listener, dateFrom, dateTo);
    }

    public JodaDateRangePickerDialog(@NonNull Context context, @StyleRes int themeResId,
                                     @Nullable JodaDateRangePickerDialog.OnRangeSelectedListener listener, @Nullable DateTime dateFrom, @Nullable DateTime dateTo) {
        super(context, themeResId);
        mOnRangeSelectedListener = listener;

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.joda_date_range_picker_dialog, null);
        setView(view);

        setButton(BUTTON_POSITIVE, themeContext.getString(android.R.string.ok), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(android.R.string.cancel), this);

        mViewPagerRange = view.findViewById(R.id.view_pager_ranges);
        mDateRangePagerAdapter = new DateRangePagerAdapter(themeContext, dateFrom, dateTo);
        mDateRangePagerAdapter.setmOnDateRangeChangedListener(this);
        mViewPagerRange.setAdapter(mDateRangePagerAdapter);

        mTabLayout = view.findViewById(R.id.tabs_date_range);
        mTabLayout.setupWithViewPager(mViewPagerRange);
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        return state;
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mOnRangeSelectedListener != null) {
                    mOnRangeSelectedListener.onRangeSelected(mDateRangePagerAdapter.getDateFrom(), mDateRangePagerAdapter.getDateTo());
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    @Override
    public void onDateFromChanged(DateTime date) {
        mViewPagerRange.setCurrentItem(1);
    }

    @Override
    public void onDateToChanged(DateTime date) {

    }

    public void setOnRangeSelectedListener(@Nullable JodaDateRangePickerDialog.OnRangeSelectedListener listener) {
        mOnRangeSelectedListener = listener;
    }

    public interface OnRangeSelectedListener {
        void onRangeSelected(DateTime dateFrom, DateTime dateTo);
    }
}
