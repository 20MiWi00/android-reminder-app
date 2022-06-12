package com.example.z3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z3.RoomDatabase.Task;
import com.example.z3.RoomDatabase.multiDate;

import java.sql.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

     public static final int PICK_IMAGE = 1;
     private TaskViewModel viewModel;
     private Integer providedID;

     EditText title;
     EditText description;
     Spinner category;
     List<String> temp = Arrays.asList(new String[]{"None","Home","Work","Study","School","Sport"});
     List<String> categoryList = new ArrayList<>(temp);

     Button addDate;
     TextView showDate;
     int Y,M,D;

     Task taskToUpade;

     EditText hour;
     EditText minute;

     EditText photo;

     CheckBox setReminder;
     Button confirm;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        viewModel = new ViewModelProvider(AddTaskActivity.this).get(TaskViewModel.class);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        category = findViewById(R.id.category);
        photo = findViewById(R.id.photoURL);
        addDate = findViewById(R.id.addDate);
        showDate = findViewById(R.id.showDate);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        setReminder = findViewById(R.id.alertBox);
        confirm = findViewById(R.id.confirmButton);
        providedID = (Integer) getIntent().getSerializableExtra("TaskID");
        if(providedID != null){
            taskToUpade = viewModel.getTaskById(providedID);
            title.setText(taskToUpade.getTitle());
            description.setText(taskToUpade.getDescription());
            categoryList.add("Hidden");
            if(!taskToUpade.getPhoto().equals("")){
                photo.setText(taskToUpade.getPhoto());
            }
            Integer H = taskToUpade.getChosenDate().getHour();
            Integer Min = taskToUpade.getChosenDate().getMinute();
            hour.setText(H.toString());
            minute.setText(Min.toString());
            if(taskToUpade.isReminderType())
                setReminder.setChecked(true);
            showDate.setText(taskToUpade.getChosenDate().getDay() + "." + taskToUpade.getChosenDate().getMonth() + "." + taskToUpade.getChosenDate().getYear());
            Y = taskToUpade.getChosenDate().getYear();
            M = taskToUpade.getChosenDate().getMonth();
            D = taskToUpade.getChosenDate().getDay();

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categoryList);
        category.setAdapter(adapter);
        if(providedID != null){
            category.setSelection(adapter.getPosition(taskToUpade.getCategory()));
        }
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int newYear, int newMonth, int newDayOfMonth) {
                        newMonth = newMonth + 1;
                        showDate.setText(newDayOfMonth + "." + newMonth  + "." + newYear);
                        Y = newYear;
                        M = newMonth;
                        D = newDayOfMonth;
                    }
                },year,month,day);
                datePickerDialog.setTitle("Select date");
                datePickerDialog.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    String stringTitle = title.getText().toString();
                    String stringDescription = description.getText().toString();
                    String URLPhoto = photo.getText().toString();
                    TextView selectedCategory = (TextView) category.getSelectedView();
                    String stringCategory = selectedCategory.getText().toString();
                    Date nowDate = new Date(System.currentTimeMillis());
                    SimpleDateFormat nowYear = new SimpleDateFormat("yyyy");
                    int intYear = Integer.parseInt(nowYear.format(nowDate));
                    SimpleDateFormat nowMonth = new SimpleDateFormat("MM");
                    int intMonth = Integer.parseInt(nowMonth.format(nowDate));
                    SimpleDateFormat nowDay = new SimpleDateFormat("dd");
                    int intDay = Integer.parseInt(nowDay.format(nowDate));
                    SimpleDateFormat nowHour = new SimpleDateFormat("HH");
                    int intHour = Integer.parseInt(nowHour.format(nowDate));
                    SimpleDateFormat nowMinute = new SimpleDateFormat("mm");
                    int intMinute = Integer.parseInt(nowMinute.format(nowDate));
                    multiDate date = new multiDate(intYear,intMonth,intDay,intHour,intMinute);
                    multiDate chosenDate = new multiDate(Y,M,D,Integer.parseInt(hour.getText().toString()),Integer.parseInt(minute.getText().toString()));
                    boolean reminderChecked = false;
                    if(setReminder.isChecked())
                        reminderChecked = true;
                    Task newTask = new Task(stringTitle,stringDescription,stringCategory,URLPhoto,reminderChecked,date,chosenDate,false);
                    if(providedID == null)
                        viewModel.insert(newTask);
                    else{
                        viewModel.delete(providedID);
                        viewModel.insert(newTask);
                    }
                    Intent returnToMain = new Intent(AddTaskActivity.this,MainActivity.class);
                    startActivity(returnToMain);
                }
            }
        });
    }

     public boolean validateData(){
         if(title.getText().toString().equals("")){
             Toast.makeText(AddTaskActivity.this,"No title!",Toast.LENGTH_SHORT).show();
             return false;
         }
         if(description.getText().toString().equals("")){
             Toast.makeText(AddTaskActivity.this,"No description!",Toast.LENGTH_SHORT).show();
             return false;
         }
         if(showDate.getText().toString().equals("")){
             Toast.makeText(AddTaskActivity.this,"No date selected!",Toast.LENGTH_SHORT).show();
             return false;
         }
         if(hour.getText().toString().equals("") || minute.getText().toString().equals("")){
             Toast.makeText(AddTaskActivity.this,"No hour or minute!",Toast.LENGTH_SHORT).show();
             return false;
         }
         Integer tempVal = Integer.parseInt(hour.getText().toString());
         if(tempVal < 0 || tempVal > 23){
             Toast.makeText(AddTaskActivity.this,"Wrong hour!",Toast.LENGTH_SHORT).show();
             hour.setText("");
             return false;
         }
         tempVal = Integer.parseInt(minute.getText().toString());
         if(tempVal < 0  || tempVal > 59){
             Toast.makeText(AddTaskActivity.this,"Wrong minute!",Toast.LENGTH_SHORT).show();
             minute.setText("");
             return false;
         }
         Date nowDate = new Date(System.currentTimeMillis());
         SimpleDateFormat nowYear = new SimpleDateFormat("yyyy");
         int intYear = Integer.parseInt(nowYear.format(nowDate));
         SimpleDateFormat nowMonth = new SimpleDateFormat("MM");
         int intMonth = Integer.parseInt(nowMonth.format(nowDate));
         SimpleDateFormat nowDay = new SimpleDateFormat("dd");
         int intDay = Integer.parseInt(nowDay.format(nowDate));
         SimpleDateFormat nowHour = new SimpleDateFormat("HH");
         int intHour = Integer.parseInt(nowHour.format(nowDate));
         SimpleDateFormat nowMinute = new SimpleDateFormat("mm");
         int intMinute = Integer.parseInt(nowMinute.format(nowDate));
         if(intYear > Y){
             Toast.makeText(AddTaskActivity.this,"Wrong chosen date!",Toast.LENGTH_SHORT).show();
             return false;
         }
         if(intYear == Y && intMonth > M){
             Toast.makeText(AddTaskActivity.this,"Wrong chosen date!",Toast.LENGTH_SHORT).show();
             return false;
         }
         else{
             if(intDay > D){
                 Toast.makeText(AddTaskActivity.this,"Wrong chosen date!",Toast.LENGTH_SHORT).show();
                 return false;
             }
             else{
                 if( intMonth == M && intDay == D && intHour > Integer.parseInt(hour.getText().toString())){
                     Toast.makeText(AddTaskActivity.this,"Wrong chosen date!",Toast.LENGTH_SHORT).show();
                     return false;
                 }
                 else{
                     if( intMonth == M && intDay == D && intHour == Integer.parseInt(hour.getText().toString()) && intMinute > Integer.parseInt(minute.getText().toString())){
                         Toast.makeText(AddTaskActivity.this,"Wrong chosen date!",Toast.LENGTH_SHORT).show();
                         return false;
                     }
                 }
             }
         }
         return true;
     }
}