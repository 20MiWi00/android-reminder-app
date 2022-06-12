package com.example.z3;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.z3.RoomDatabase.RoomDb;
import com.example.z3.RoomDatabase.Task;
import com.example.z3.RoomDatabase.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository taskRepository;

    private final LiveData<List<Task>> taskList;

    public TaskViewModel(Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        taskList = taskRepository.getTaskList();
    }

    public LiveData<List<Task>> getTaskList(){ return taskList;}

    public List<Task> getTask(String titlename){ return taskRepository.getTask(titlename); }

    public List<Task> getTasksByCategory(String category){return taskRepository.getTasksByCategory(category);}

    public List<Task> getAllTasks(){return taskRepository.getAllTasks();}

    public List<Task> getTasksByTitleAndCategory(String category, String titlename){return taskRepository.getTasksByTitleAndCategory(category, titlename);}

    public void updateTask(Task task){ taskRepository.updateTask(task); }

    public Task getTaskById(int id){return taskRepository.getTaskById(id);}

    public void insert(Task task){ taskRepository.insert(task); }

    public void insertAll(List<Task> taskList){ taskRepository.insertAll(taskList); }

    public void delete(int id){ taskRepository.delete(id); }

}
