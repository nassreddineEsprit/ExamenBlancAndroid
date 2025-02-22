package tn.esprit.examenblancandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.examenblancandroid.entity.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHold>{

    private List<Task> tasks;
    private Context context;


    public TaskAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public TaskViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(this.context)
                .inflate(R.layout.activity_single_task,parent,false);
        return new TaskViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHold holder, int position) {
        Task task = tasks.get(position);
        holder.tasktxtview.setText(task.getNomTask());
        holder.usertxtview.setText(task.getNomUser());
        holder.stateTask.setText(task.getStateTask());
        switch (task.getStateTask()) {
            case "Done":
                holder.imgTask.setImageResource(R.drawable.done);
                break;
            case "Doing":
                holder.imgTask.setImageResource(R.drawable.doing);
                break;
            case "To Do":
                holder.imgTask.setImageResource(R.drawable.to_do);
                break;
            default:
                holder.imgTask.setImageResource(R.drawable.blank); // Fallback icon
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateTaskActivity.class);
            intent.putExtra("taskId", tasks.get(position).getIdTask());
            intent.putExtra("taskName", task.getNomTask());
            intent.putExtra("userName", task.getNomUser());
            intent.putExtra("state", task.getStateTask());
            context.startActivity(intent);
        });
    }

    public void addTask(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1); // Notify RecyclerView
    }

    public void updateTasks(List<Task> newTasks) {
        tasks.clear();
        tasks.addAll(newTasks);
        notifyDataSetChanged();
    }
    public void updateTask(Task updatedTask, int position) {
        tasks.set(position, updatedTask); // Update the task in the dataset
        notifyItemChanged(position); // Notify the adapter of the change
    }

    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }
}
