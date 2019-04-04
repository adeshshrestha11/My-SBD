package com.adesh.my_sbd.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.adesh.my_sbd.R;
import com.adesh.my_sbd.model.DataModel;
import com.adesh.my_sbd.sqlite.DatabaseAccessHelper;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class BenchAdapter extends RecyclerView.Adapter<BenchAdapter.MyViewHolder> {

    Context context;
    ArrayList<DataModel> list;

    DataModel dataModel;
    DatabaseAccessHelper db = new DatabaseAccessHelper(context);

    public BenchAdapter(Context context, ArrayList<DataModel> list) {
        this.context = context;
        this.list = list;
    }

    public void insertRefreshAdapter(ArrayList<DataModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BenchAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lift_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataModel dataModel = list.get(position);

        holder.date.setText("(" + dataModel.getDate() + ")");
        holder.weekNo.setText(dataModel.getWeek_no());
        holder.data.setText(dataModel.getNotes());

        holder.itemView.setOnClickListener(v -> {
            android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            final View dialogView = inflater.inflate(R.layout.dialog_edit_entry, null);
            dialogBuilder.setView(dialogView);

            EditText weekNoET = dialogView.findViewById(R.id.dialog_edit_entry_week);
            EditText dataET = dialogView.findViewById(R.id.dialog_edit_entry_data);
            AppCompatEditText dateET = dialogView.findViewById(R.id.dialog_edit_date);
            AppCompatButton updateEntryBtn = dialogView.findViewById(R.id.dialog_edit_entry_editEntry_btn);
            AppCompatButton deleteEntryBtn = dialogView.findViewById(R.id.dialog_edit_entry_deleteEntry_btn);
            AppCompatImageView cancelBtn = dialogView.findViewById(R.id.dialog_edit_entry_cancelBtn);

            weekNoET.setText(dataModel.getWeek_no());
            dataET.setText(dataModel.getNotes());
            dateET.setText(dataModel.getDate());

            dateET.setOnClickListener(v14 -> showCalender(dateET));

            final android.app.AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();

            updateEntryBtn.setOnClickListener(v12 -> {
                if (weekNoET.getText().toString().isEmpty() || dataET.getText().toString().isEmpty()) {
                    Toasty.error(context, "Enter all details", Toast.LENGTH_SHORT, true).show();
                } else {
                    Toasty.success(context, "Log Updated", Toast.LENGTH_SHORT, true).show();
                    alertDialog.dismiss();
                    updateBench(
                            dataModel.getLift_id(),
                            dateET.getText().toString().trim(),
                            weekNoET.getText().toString().trim(),
                            dataET.getText().toString().trim(),
                            "b",
                            position);
                }
            });

            deleteEntryBtn.setOnClickListener(v13 -> {
                deleteSquat(dataModel.getLift_id(), position);
                alertDialog.dismiss();
            });

            cancelBtn.setOnClickListener(v1 -> alertDialog.dismiss());
        });
    }

    private void showCalender(final AppCompatEditText date) {
        final Calendar newCalendar = Calendar.getInstance(Locale.ENGLISH);
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
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

    private void updateBench(String lift_id, String date, String week_no, String notes, String tag, int position) {
        dataModel = new DataModel();
        dataModel.setLift_id(lift_id);
        dataModel.setDate(date);
        dataModel.setWeek_no(week_no);
        dataModel.setNotes(notes);
        dataModel.setTag(tag);
        db.updateLiftDetails(dataModel);
        Log.d("ASfasfaf", new Gson().toJson(db.updateLiftDetails(dataModel)));

        list.set(position, dataModel);
        notifyDataSetChanged();
    }

    private void deleteSquat(String lift_id, int position) {
        dataModel = new DataModel();
        dataModel.setLift_id(lift_id);
        db.deleteLiftDetails(dataModel);

        list.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_squat_list_weekno)
        AppCompatTextView weekNo;
        @BindView(R.id.item_squat_list_data)
        AppCompatTextView data;
        @BindView(R.id.item_squat_list_date)
        AppCompatTextView date;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
