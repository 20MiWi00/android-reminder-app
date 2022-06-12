package com.example.z3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReminderActivity extends AppCompatActivity {

    EditText setMinutes;
    Button setTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        setMinutes = findViewById(R.id.editReminderTime);
        setMinutes.setText(MainActivity.reminderTimeToActivate.toString());
        setTime = findViewById(R.id.setReminderTime);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int time = Integer.parseInt(setMinutes.getText().toString());
                    MainActivity.reminderTimeToActivate = time;
                }catch(Exception e){
                    Toast.makeText(ReminderActivity.this,"Wrong number format!",Toast.LENGTH_LONG).show();
                    setMinutes.setText("");
                }
                Intent returnIntent = new Intent(ReminderActivity.this,MainActivity.class);
                startActivity(returnIntent);
            }
        });
    }
}