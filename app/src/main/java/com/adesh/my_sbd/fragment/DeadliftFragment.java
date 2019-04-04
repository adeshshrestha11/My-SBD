package com.adesh.my_sbd.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.adesh.my_sbd.R;
import com.adesh.my_sbd.adapter.DeadliftAdapter;
import com.adesh.my_sbd.adapter.SquatAdapter;
import com.adesh.my_sbd.dialog.BenchInsertDialog;
import com.adesh.my_sbd.dialog.DeadliftInsertDialog;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DeadliftFragment extends Fragment {

    Button entryBtn;
    RecyclerView recyclerView;

    //for offline storage
    LiftDataModel liftDataModel;
    DataModel dataModel;
    static DatabaseAccessHelper db;

    DeadliftAdapter deadliftAdapter;

    LinearLayoutManager linearLayoutManager;


    public DeadliftFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_deadlift, container, false);

        db = new DatabaseAccessHelper(getContext());

        entryBtn = myView.findViewById(R.id.activity_squat_entry_btn);
        recyclerView = myView.findViewById(R.id.activity_squat_recyclerview);

        entryBtn.setOnClickListener(v ->
//                DeadliftInsertDialog.display(getActivity().getSupportFragmentManager())
                addEntryDialog());

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        deadliftAdapter = new DeadliftAdapter(getContext(), db.getDeadliftDetails());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(deadliftAdapter);

        return myView;
    }

    private void addEntryDialog() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_data, null);
        dialogBuilder.setView(dialogView);

        AppCompatButton addEntryBtn = dialogView.findViewById(R.id.dialog_add_entry_addEntry_btn);
        AppCompatButton cancelBtn = dialogView.findViewById(R.id.dialog_add_entry_cancelBtn);
        AppCompatEditText weekNoET = dialogView.findViewById(R.id.dialog_add_entry_week);
        AppCompatEditText dateET = dialogView.findViewById(R.id.dialog_add_data_date);
        AppCompatEditText dataET = dialogView.findViewById(R.id.dialog_add_data);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        Log.d("date", formattedDate);

        dateET.setText(formattedDate);
        dateET.setOnClickListener(v -> showCalender(dateET));

        final android.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();

        cancelBtn.setOnClickListener(v -> alertDialog.dismiss());

        addEntryBtn.setOnClickListener(v -> {
            if (weekNoET.getText().toString().isEmpty() || dataET.getText().toString().isEmpty()) {
                Toasty.error(getContext(), "Enter all details", Toast.LENGTH_SHORT, true).show();
            } else {
//                Toasty.success(DeadliftActivity.this, "Log Added", Toast.LENGTH_SHORT, true).show();
                alertDialog.dismiss();
                insertDeadliftDetails(
                        dateET.getText().toString().trim(),
                        weekNoET.getText().toString().trim(),
                        dataET.getText().toString().trim(),
                        "d");
            }
        });
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

    private void insertDeadliftDetails(String date, String week_no, String notes, String tag) {
        dataModel = new DataModel();
        dataModel.setDate(date);
        dataModel.setWeek_no(week_no);
        dataModel.setNotes(notes);
        dataModel.setTag(tag);

        if (db.addLiftData(dataModel)) {
            deadliftAdapter.insertRefreshAdapter(db.getDeadliftDetails());
        } else {
            Toasty.error(getContext(), "Unable to add lift details", Toast.LENGTH_SHORT, true).show();
        }
    }

}
