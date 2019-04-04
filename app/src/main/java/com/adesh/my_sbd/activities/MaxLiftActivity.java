package com.adesh.my_sbd.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adesh.my_sbd.R;
import com.adesh.my_sbd.model.MaxLiftModel;
import com.adesh.my_sbd.sqlite.DatabaseAccessHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MaxLiftActivity extends AppCompatActivity {

    @BindView(R.id.maxLift_ok)
    Button okBtn;
    @BindView(R.id.maxLift_squat)
    AppCompatEditText squatET;
    @BindView(R.id.maxLift_bench)
    AppCompatEditText benchET;
    @BindView(R.id.maxLift_deadlift)
    AppCompatEditText deadliftET;
    @BindView(R.id.maxLift_total)
    AppCompatTextView maxlifttotal;

    MaxLiftModel maxLiftModel;
    static DatabaseAccessHelper db;

    Double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_lift);

        db = new DatabaseAccessHelper(getApplicationContext());
        ButterKnife.bind(this);

        maxLiftModel = db.getMaxes();
        try {
            squatET.setText(maxLiftModel.getSquat());
            benchET.setText(maxLiftModel.getBench());
            deadliftET.setText(maxLiftModel.getDeadlift());
            total = Double.parseDouble(squatET.getText().toString().trim()) + Double.parseDouble(benchET.getText().toString().trim()) + Double.parseDouble(deadliftET.getText().toString().trim());
            Log.d("total", String.valueOf(total));
            maxlifttotal.setText(String.valueOf(total));
        } catch (Exception e) {
            Toasty.error(MaxLiftActivity.this, "No Data", Toast.LENGTH_SHORT, true).show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);
        }

        okBtn.setOnClickListener(v ->
        {
            if (squatET.getText().toString().trim().isEmpty() || benchET.getText().toString().trim().isEmpty() || deadliftET.getText().toString().trim().isEmpty()) {
                Toasty.error(MaxLiftActivity.this, "Incomplete data", Toast.LENGTH_SHORT, true).show();
            } else {
                addMaxLift(
                        squatET.getText().toString().trim(),
                        benchET.getText().toString().trim(),
                        deadliftET.getText().toString().trim());
            }
        });
    }

    private void addMaxLift(String squat, String bench, String deadlift) {
        maxLiftModel = new MaxLiftModel();
        maxLiftModel.setSquat(squat);
        maxLiftModel.setBench(bench);
        maxLiftModel.setDeadlift(deadlift);

        db.addMaxes(maxLiftModel);
        Toasty.success(MaxLiftActivity.this, "Max Lift Recorded", Toast.LENGTH_SHORT, true).show();
        maxlifttotal.setText(String.valueOf(Double.parseDouble(squat) + Double.parseDouble(bench) + Double.parseDouble(deadlift)));
       /* if (db.addMaxes(maxLiftModel)) {
            db.getMaxes();
        } else {
            Toasty.error(MaxLiftActivity.this, "Error", Toast.LENGTH_SHORT, true).show();
        }*/
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.maxSave) {
            if (squatET.getText().toString().trim().isEmpty() || benchET.getText().toString().trim().isEmpty() || deadliftET.getText().toString().trim().isEmpty()) {
                Toasty.error(MaxLiftActivity.this, "Incomplete data", Toast.LENGTH_SHORT, true).show();
            } else {
                addMaxLift(
                        squatET.getText().toString().trim(),
                        benchET.getText().toString().trim(),
                        deadliftET.getText().toString().trim());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_max_lifts, menu);
        return true;
    }
}
