package com.example.zegar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView clockTextView;
    private EditText hourInput;
    private EditText minuteInput;
    private TextView alarmStatusTextView;
    private int targetHour;
    private int targetMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockTextView = findViewById(R.id.clockTextView);
        hourInput = findViewById(R.id.hourInput);
        minuteInput = findViewById(R.id.minuteInput);
        alarmStatusTextView = findViewById(R.id.alarmStatusTextView);
        Button setAlarmButton = findViewById(R.id.setAlarmButton);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        updateClock();
    }

    private void updateClock() {
        clockTextView.post(new Runnable() {
            @Override
            public void run() {
                Calendar currentCalendar = Calendar.getInstance();
                int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentCalendar.get(Calendar.MINUTE);
                int currentSecond = currentCalendar.get(Calendar.SECOND);

                clockTextView.setText(new SimpleDateFormat("HH:mm:ss").format(currentCalendar.getTime()));

                if (currentHour == targetHour && currentMinute == targetMinute && currentSecond == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Alarm!");
                    builder.setMessage("Czas: " + targetHour + ":" + targetMinute);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                clockTextView.postDelayed(this, 1000);
            }
        });
    }

    private void setAlarm() {
        targetHour = Integer.parseInt(hourInput.getText().toString());
        targetMinute = Integer.parseInt(minuteInput.getText().toString());
        alarmStatusTextView.setText("Alarm ustawiony na: " + String.format("%02d:%02d", targetHour, targetMinute));
    }
}
