package com.adesh.my_sbd.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adesh.my_sbd.R;
import com.adesh.my_sbd.adapter.SquatAdapter;
import com.adesh.my_sbd.model.DataModel;
import com.adesh.my_sbd.model.LiftDataModel;
import com.adesh.my_sbd.sqlite.DatabaseAccessHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class DeadliftInsertDialog extends DialogFragment {

    public static final String TAG = "insert_dialog";

    //for offline storage
    LiftDataModel liftDataModel;
    DataModel dataModel;
    static DatabaseAccessHelper db;

    SquatAdapter squatAdapter;

    AppCompatEditText weekNoET;
    AppCompatEditText dateET;
    AppCompatEditText dataET;
    private Toolbar toolbar;

    public static DeadliftInsertDialog display(FragmentManager fragmentManager) {
        DeadliftInsertDialog deadliftInsertDialog = new DeadliftInsertDialog();
        deadliftInsertDialog.show(fragmentManager, TAG);
        return deadliftInsertDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.insert_dialog, container, false);

        db = new DatabaseAccessHelper(getContext());

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.cross_icon);
        toolbar.setNavigationOnClickListener(view1 -> dismiss());

        weekNoET = view.findViewById(R.id.dialog_add_entry_week);
        dateET = view.findViewById(R.id.dialog_add_data_date);
        dataET = view.findViewById(R.id.dialog_add_data);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        Log.d("date", formattedDate);

        dateET.setText(formattedDate);
        dateET.setOnClickListener(v -> showCalender(dateET));

        return view;
    }

    private void showCalender(final AppCompatEditText date) {
        final Calendar newCalendar = Calendar.getInstance(Locale.ENGLISH);
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(getActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setFirstDayOfWeek(Calendar.MONDAY);
            gc.set(Calendar.MONTH, view.getMonth());
            gc.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
            gc.set(Calendar.YEAR, view.getYear());
            DecimalFormat mFormat = new DecimalFormat("00");
            String selectedDate = String.format("%s-%S-%s",
                    gc.get(Calendar.YEAR),
                    mFormat.format(gc.get(Calendar.MONTH) + 1),
                    mFormat.format(gc.get(Calendar.DATE)));
            date.setText(selectedDate);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.setTitle("Select Date:");
        fromDatePickerDialog.getDatePicker().setSpinnersShown(true);
        fromDatePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
        fromDatePickerDialog.show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Add Log");
        toolbar.inflateMenu(R.menu.menu_insert_data);
        toolbar.setOnMenuItemClickListener(item -> {
            if (weekNoET.getText().toString().trim().isEmpty() ||
                    dateET.getText().toString().trim().isEmpty() ||
                    dataET.getText().toString().trim().isEmpty()) {
                Toasty.error(getContext(), "Enter all details", Toast.LENGTH_SHORT, true).show();
            } else {
                insertSquatDetails(
                        dateET.getText().toString().trim(),
                        weekNoET.getText().toString().trim(),
                        dataET.getText().toString().trim(),
                        "d");
                dismiss();
            }
            return true;
        });
    }

    private void insertSquatDetails(String date, String week_no, String notes, String tag) {
        dataModel = new DataModel();
        dataModel.setDate(date);
        dataModel.setWeek_no(week_no);
        dataModel.setNotes(notes);
        dataModel.setTag(tag);
        db.addLiftData(dataModel);
       /* if (db.addLiftData(dataModel)) {
            squatAdapter.insertRefreshAdapter(db.getSquatDetails());
        } else {
            Toasty.error(getContext(), "Unable to add lift details", Toast.LENGTH_SHORT, true).show();
        }*/
    }
}
