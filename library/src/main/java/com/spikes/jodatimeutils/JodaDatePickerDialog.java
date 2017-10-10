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
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import org.joda.time.DateTime;

/**
 * Created by Jameido on 13/07/2017.
 */

public class JodaDatePickerDialog extends AlertDialog
        implements DialogInterface.OnClickListener {

    private static final String EXTRA_YEAR = "EXTRA_YEAR";
    private static final String EXTRA_MONTH = "EXTRA_MONTH";
    private static final String EXTRA_DAY = "EXTRA_DAY";

    private JodaDatePicker mDatePicker;

    private OnDateSetListener mDateSetListener;

    public JodaDatePickerDialog(@NonNull Context context) {
        this(context, 0, null, DateTime.now());
    }

    public JodaDatePickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener) {
        this(context, 0, listener, DateTime.now());
    }

    public JodaDatePickerDialog(@NonNull Context context, int year, int month, int dayOfMonth) {
        this(context, 0, null, year, month, dayOfMonth);
    }

    public JodaDatePickerDialog(@NonNull Context context, @Nullable DateTime dateTime) {
        this(context, 0, null, dateTime);
    }

    public JodaDatePickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener, @Nullable DateTime dateTime) {
        this(context, 0, listener, dateTime);
    }

    public JodaDatePickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        this(context, 0, listener, year, month, dayOfMonth);
    }

    public JodaDatePickerDialog(@NonNull Context context, @StyleRes int themeResId,
                                @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        this(context, themeResId, listener, new DateTime(year, month, dayOfMonth, 0, 0));
    }

    public JodaDatePickerDialog(@NonNull Context context, @StyleRes int themeResId,
                                @Nullable OnDateSetListener listener, @Nullable DateTime dateTime) {
        super(context, themeResId);

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.joda_date_picker_dialog, null);
        setView(view);

        setButton(BUTTON_POSITIVE, themeContext.getString(android.R.string.ok), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(android.R.string.cancel), this);

        mDatePicker = (JodaDatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(dateTime);

        mDateSetListener = listener;
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(EXTRA_YEAR, mDatePicker.getYear());
        state.putInt(EXTRA_MONTH, mDatePicker.getMonth());
        state.putInt(EXTRA_DAY, mDatePicker.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int year = savedInstanceState.getInt(EXTRA_YEAR);
        final int month = savedInstanceState.getInt(EXTRA_MONTH);
        final int day = savedInstanceState.getInt(EXTRA_DAY);
        mDatePicker.init(new DateTime(year, month, day, 0, 0));
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mDateSetListener != null) {
                    // Clearing focus forces the dialog to commit any pending
                    // changes, e.g. typed text in a NumberPicker.
                    mDatePicker.clearFocus();
                    mDateSetListener.onDateSet(mDatePicker, mDatePicker.getDate());
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    public void setOnDateSetListener(@Nullable OnDateSetListener listener) {
        mDateSetListener = listener;
    }

    /**
     * Returns the {@link DatePicker} contained in this dialog.
     *
     * @return the date picker
     */
    @NonNull
    public JodaDatePicker getDatePicker() {
        return mDatePicker;
    }

    /**
     * Sets the current date.
     *
     * @param year       the year
     * @param month      the month (1-12 for compatibility with
     *                   {@link DateTime} month)
     * @param dayOfMonth the day of month (1-31, depending on month)
     */
    public void updateDate(int year, int month, int dayOfMonth) {
        mDatePicker.updateDate(year, month - 1, dayOfMonth);
    }

    public void updateDate(DateTime date) {
        mDatePicker.updateDate(date);
    }


    public interface OnDateSetListener {
        void onDateSet(DatePicker datePicker, DateTime date);
    }
}
