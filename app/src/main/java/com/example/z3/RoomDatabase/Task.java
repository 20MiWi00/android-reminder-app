package com.example.z3.RoomDatabase;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "task_table")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String title;
    private String description;
    private String category;
    private String photo;
    private boolean reminderType;
    private multiDate nowDate;
    private multiDate chosenDate;
    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Task(String title, String description, String category, String photo, boolean reminderType, multiDate nowDate, multiDate chosenDate,boolean isDone) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.photo = photo;
        this.reminderType = reminderType;
        this.nowDate = nowDate;
        this.chosenDate = chosenDate;
        this.isDone = isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isReminderType() {
        return reminderType;
    }

    public void setReminderType(boolean reminderType) {
        this.reminderType = reminderType;
    }

    public multiDate getNowDate() {
        return nowDate;
    }

    public void setNowDate(multiDate nowDate) {
        this.nowDate = nowDate;
    }

    public multiDate getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(multiDate chosenDate) {
        this.chosenDate = chosenDate;
    }
}
