package com.example.z3.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTaskList();

    @Query("SELECT * FROM task_table WHERE title = :titlename")
    List<Task> getTask(String titlename);

    @Query("SELECT * FROM task_table WHERE id = :id")
    Task getTaskById(int id);

    @Query("SELECT * FROM task_table WHERE category = :category")
    List<Task> getTasksByCategory(String category);

    @Query("SELECT * FROM task_table WHERE category != 'Hidden'")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE category = :category AND title = :titlename")
    List<Task> getTasksByTitleAndCategory(String category, String titlename);

    @Update
    void updateTask(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> taskList);

    @Query("DELETE FROM task_table WHERE id = :id")
    void delete(int id);


}
