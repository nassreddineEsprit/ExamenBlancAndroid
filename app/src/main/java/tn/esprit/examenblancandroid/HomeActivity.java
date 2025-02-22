package tn.esprit.examenblancandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.examenblancandroid.database.AppDatabase;
import tn.esprit.examenblancandroid.entity.Task;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_TASK = 1; // For adding a new task
    private static final int REQUEST_CODE_UPDATE_TASK = 2; // For updating an existing task
    private Button addTaskbtn;
    private RecyclerView rc;
    private TaskAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addTaskbtn = findViewById(R.id.newtaskbtn);
        rc = findViewById(R.id.recyclerView);

        db = AppDatabase.getInstance(this);

        adapter = new TaskAdapter(new ArrayList<>(), this);
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        loadTasks();

        addTaskbtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK); // Start with result
        });
    }

    private void loadTasks() {
        new LoadTasksAsyncTask(db).execute();
    }

    // Receive the result when a new task is added
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_ADD_TASK) {
                // Handle adding a new task
                String taskName = data.getStringExtra("taskName");
                String userName = data.getStringExtra("userName");
                String state = data.getStringExtra("state");
                int imageResId = data.getIntExtra("imageResId", R.drawable.ic_ahri);

                Task newTask = new Task(taskName, userName, state, imageResId);
                adapter.addTask(newTask); // Add the new task dynamically
            } else if (requestCode == REQUEST_CODE_UPDATE_TASK) {
                // Handle updating an existing task
                Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
                int position = data.getIntExtra("adapterPosition", -1);

                if (position != -1 && updatedTask != null) {
                    adapter.updateTask(updatedTask, position); // Update the task in the adapter
                }
            }
            else if (requestCode == REQUEST_CODE_UPDATE_TASK && data.getBooleanExtra("taskDeleted", false)) {
                int position = data.getIntExtra("adapterPosition", -1);
                if (position != -1) {
                    adapter.removeTask(position);
                }
            }
        }
    }

    private class LoadTasksAsyncTask extends AsyncTask<Void, Void, List<Task>> {
        private final AppDatabase db;

        LoadTasksAsyncTask(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected List<Task> doInBackground(Void... voids) {
            return db.taskDao().getAllTasks();
        }

        @Override
        protected void onPostExecute(List<Task> tasks) {
            adapter.updateTasks(tasks); // Update the adapter with new data
        }
    }
}
