package com.example.z3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z3.RoomDatabase.Task;
import com.example.z3.RoomDatabase.Update;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    FloatingActionButton addTask;
    RecyclerView recyclerView;
    Button searchButton;
    EditText searchTask;
    Spinner category;
    TaskRecyclerViewAdapter adapter;

    public static Integer reminderTimeToActivate = 0;

    private TaskViewModel viewModel;
    private List<Task> allTasks = new ArrayList<>();
    private List<Task> chosenTasks = new ArrayList<>();
    private getUpdateTypeByPosition intInterface;
    List<String> categoryList = Arrays.asList(new String[]{"All","Home","Work","Study","School","Sport","Hidden"});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intInterface = new getUpdateTypeByPosition() {
            @Override
            public void resultedUpdate(Update update) {
                if(update.getTypeOfUpdate().equals("D")){
                    viewModel.delete(update.getId());
                    searchTask.setText("");
                }
                else{
                    Intent editTask = new Intent(MainActivity.this,AddTaskActivity.class);
                    editTask.putExtra("TaskID",update.getId());
                    searchTask.setText("");
                    startActivity(editTask);
                }
            }
        };
        recyclerView = findViewById(R.id.taskRecyclerView);
        adapter = new TaskRecyclerViewAdapter(allTasks,this,intInterface);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new ViewModelProvider(MainActivity.this).get(TaskViewModel.class);
        viewModel.getTaskList().observe(MainActivity.this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                allTasks.clear();
                List<Task> resultTask = viewModel.getAllTasks();
                resultTask.sort(Comparator.comparing(T -> T.getChosenDate().getFullDate()));
                allTasks.addAll(resultTask);
                checkIfDone();
                adapter.notifyDataSetChanged();
            }
        });
        addTask = findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTask = new Intent(MainActivity.this,AddTaskActivity.class);
                startActivity(addTask);
            }
        });
        searchTask = findViewById(R.id.searchTask);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchTask.getText().equals("")){
                    TextView selectedCategory = (TextView) category.getSelectedView();
                    String stringCategory = selectedCategory.getText().toString();
                    if(!stringCategory.equals("All")){
                        chosenTasks = viewModel.getTasksByTitleAndCategory(stringCategory,searchTask.getText().toString());
                    }
                    else
                        chosenTasks = viewModel.getTask(searchTask.getText().toString());
                }
                if(chosenTasks.size() > 0){
                    allTasks.clear();
                    chosenTasks.sort(Comparator.comparing(T -> T.getChosenDate().getFullDate()));
                    allTasks.addAll(chosenTasks);
                    checkIfDone();
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(MainActivity.this,"No tasks with searched titlename!",Toast.LENGTH_LONG).show();
                }
            }
        });
        category = findViewById(R.id.categoryFilter);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categoryList);
        category.setAdapter(spinnerAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    searchTask.setText("");
                    if(position != 0){
                        TextView selectedCategory = (TextView) category.getSelectedView();
                        String stringCategory = selectedCategory.getText().toString();
                        List<Task> catTasks = returnTaskList(stringCategory);
                        if(catTasks.size() > 0){
                            allTasks.clear();
                            catTasks.sort(Comparator.comparing(T -> T.getChosenDate().getFullDate()));
                            allTasks.addAll(catTasks);
                            checkIfDone();
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"No tasks with selected category!",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        allTasks.clear();
                        List<Task> resultList = viewModel.getAllTasks();
                        resultList.sort(Comparator.comparing(T -> T.getChosenDate().getFullDate()));
                        System.out.println(resultList);
                        allTasks.addAll(resultList);
                        checkIfDone();
                        adapter.notifyDataSetChanged();
                    }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.setReminderTime:
                Intent reminderIntent = new Intent(MainActivity.this,ReminderActivity.class);
                startActivity(reminderIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Task> returnTaskList(String category){
        List<Task> taskList = viewModel.getTasksByCategory(category);
        return taskList;
    }

    private void checkIfDone(){
        Date nowDate = new Date(System.currentTimeMillis());
        for(int i = 0;i < allTasks.size();i++){
            if(nowDate.after(allTasks.get(i).getChosenDate().getFullDate())){
                allTasks.get(i).setDone(true);
            }
        }
    }
}