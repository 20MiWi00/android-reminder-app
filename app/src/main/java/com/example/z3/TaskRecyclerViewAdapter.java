package com.example.z3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.z3.RoomDatabase.Task;
import com.example.z3.RoomDatabase.Update;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> allTasks;
    private Context context;
    private getUpdateTypeByPosition passedInterface;

    public TaskRecyclerViewAdapter(List<Task> allTasks, Context context, getUpdateTypeByPosition passedInterface) {
        this.allTasks = allTasks;
        this.context = context;
        this.passedInterface = passedInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.title.setText(allTasks.get(position).getTitle());
         String additionalHourZero = "";
         String additionalMinuteZero = "";
         if(allTasks.get(position).getChosenDate().getHour() < 10)
             additionalHourZero = "0";
         if(allTasks.get(position).getChosenDate().getMinute() < 10)
             additionalMinuteZero = "0";
         holder.date.setText(
                 allTasks.get(position).getChosenDate().getDay() + "."
                 + allTasks.get(position).getChosenDate().getMonth() + "."
                 + allTasks.get(position).getChosenDate().getYear() + " "
                 + additionalHourZero  + allTasks.get(position).getChosenDate().getHour() + ":"
                 + additionalMinuteZero  + allTasks.get(position).getChosenDate().getMinute()
                 );
         holder.category.setText(allTasks.get(position).getCategory());
         if(!allTasks.get(position).getPhoto().equals(""))
             holder.isPhoto.setImageResource(R.drawable.photo);
         else
             holder.isPhoto.setImageDrawable(null);
         if(allTasks.get(position).isReminderType())
             holder.isReminder.setImageResource(R.drawable.bell);
         else
             holder.isReminder.setImageDrawable(null);
         if(allTasks.get(position).isDone())
             holder.isDone.setImageResource(R.drawable.check);
         else
             holder.isDone.setImageDrawable(null);
         holder.infoButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(v.getContext(),InfoActivity.class);
                 ShortTask taskToSend = new ShortTask(
                         allTasks.get(holder.getAdapterPosition()).getTitle(),
                         allTasks.get(holder.getAdapterPosition()).getDescription(),
                         allTasks.get(holder.getAdapterPosition()).getCategory(),
                         allTasks.get(holder.getAdapterPosition()).getPhoto());
                 intent.putExtra("Task", taskToSend);
                 v.getContext().startActivity(intent);
             }
         });

         holder.deleteButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Update update = new Update(allTasks.get(holder.getAdapterPosition()).getId(),"D");
                 passedInterface.resultedUpdate(update);
             }
         });

         holder.editButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Update update = new Update(allTasks.get(holder.getAdapterPosition()).getId(),"E");
                 passedInterface.resultedUpdate(update);
             }
         });
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout itemListLayout;
        Button infoButton;
        Button editButton;
        Button deleteButton;
        TextView title;
        TextView date;
        TextView category;
        ImageView isPhoto;
        ImageView isReminder;
        ImageView isDone;

        public ViewHolder(View itemView) {
            super(itemView);
            itemListLayout = itemView.findViewById(R.id.itemListLayout);
            infoButton = itemView.findViewById(R.id.infoButton);
            editButton = itemView.findViewById(R.id.editTask);
            deleteButton = itemView.findViewById(R.id.deleteTask);
            category = itemView.findViewById(R.id.taskCategory);
            title = itemView.findViewById(R.id.taskTitle);
            date = itemView.findViewById(R.id.dateInfo);
            isPhoto = itemView.findViewById(R.id.imageOption);
            isReminder = itemView.findViewById(R.id.alertOption);
            isDone = itemView.findViewById(R.id.doneOption);
        }
    }
}
