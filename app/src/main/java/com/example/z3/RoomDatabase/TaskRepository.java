package com.example.z3.RoomDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public class TaskRepository {

    private DatabaseDao databaseDao;
    private LiveData<List<Task>> taskList;

    public TaskRepository(Application application) {
        RoomDb db = RoomDb.getDatabase(application);
        databaseDao = db.databaseDao();
        taskList = databaseDao.getTaskList();
    }

    public LiveData<List<Task>> getTaskList(){
        return taskList;
    }

    public List<Task> getTask(String titlename){
        return databaseDao.getTask(titlename);
    }

    public List<Task> getTasksByCategory(String category){return databaseDao.getTasksByCategory(category);}

    public List<Task> getAllTasks(){return  databaseDao.getAllTasks();}

    public List<Task> getTasksByTitleAndCategory(String category, String titlename){return databaseDao.getTasksByTitleAndCategory(category, titlename);}

    public Task getTaskById(int id){return databaseDao.getTaskById(id);}

    public void updateTask(Task task){
        RoomDb.databaseWriteExecutor.execute(() -> {
            databaseDao.updateTask(task);
        });
    }

    public void insert(Task task){
        RoomDb.databaseWriteExecutor.execute(() -> {
            databaseDao.insert(task);
        });
    }

    public void insertAll(List<Task> taskList){
        RoomDb.databaseWriteExecutor.execute(() -> {
            databaseDao.insertAll(taskList);
        });
    }

    public void delete(int id){
        RoomDb.databaseWriteExecutor.execute(() -> {
            databaseDao.delete(id);
        });
    }

}
