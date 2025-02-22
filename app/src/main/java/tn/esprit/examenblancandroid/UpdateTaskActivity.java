package tn.esprit.examenblancandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tn.esprit.examenblancandroid.database.AppDatabase;
import tn.esprit.examenblancandroid.entity.Task;

public class UpdateTaskActivity extends AppCompatActivity {

    private TextView taskNameEditText, userNameEditText;
    private RadioGroup radioGroup;
    private RadioButton done, doing, todo;
    private Button updateButton;
    private Button deleteButton;

    private AppDatabase db;
    private int taskId; // ID of the task to update
    private int adapterPosition; // Position of the task in the RecyclerView adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        // Log the taskId
        Intent intent = getIntent();
        taskId = intent.getIntExtra("taskId", -1);
        adapterPosition = intent.getIntExtra("adapterPosition", -1); // Get the adapter position
        Log.d("UpdateTaskActivity", "Task ID: " + taskId);
        Log.d("UpdateTaskActivity", "Adapter Position: " + adapterPosition);

        // Initialize views
        taskNameEditText = findViewById(R.id.taskNameEditText);
        userNameEditText = findViewById(R.id.userNameEditText);
        radioGroup = findViewById(R.id.radioGroup);
        done = findViewById(R.id.done);
        doing = findViewById(R.id.doing);
        todo = findViewById(R.id.todo);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(v -> deleteTask());


        db = AppDatabase.getInstance(this);

        // Retrieve data passed from the Adapter
        String taskName = intent.getStringExtra("taskName");
        String userName = intent.getStringExtra("userName");
        String state = intent.getStringExtra("state");

        // Display values in the fields
        taskNameEditText.setText(taskName);
        userNameEditText.setText(userName);

        // Select the correct RadioButton based on the state
        switch (state) {
            case "To Do":
                radioGroup.check(R.id.todo);
                break;
            case "Doing":
                radioGroup.check(R.id.doing);
                break;
            case "Done":
                radioGroup.check(R.id.done);
                break;
        }

        // Set click listener for the update button
        updateButton.setOnClickListener(v -> updateTask());
    }

    private void updateTask() {
        String updatedTaskName = taskNameEditText.getText().toString().trim();
        String updatedUserName = userNameEditText.getText().toString().trim();
        String updatedState = "";

        // Get selected radio button
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.todo) {
            updatedState = "To Do";
        } else if (selectedId == R.id.doing) {
            updatedState = "Doing";
        } else if (selectedId == R.id.done) {
            updatedState = "Done";
        }

        // Validate fields
        if (updatedTaskName.isEmpty() || updatedUserName.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the updated task
        Task updatedTask = new Task(taskId, updatedTaskName, updatedUserName, updatedState, getImageResource(updatedState));

        // Update task in the database
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            db.taskDao().updateTask(updatedTask);

            handler.post(() -> {
                Toast.makeText(this, "Tâche mise à jour avec succès", Toast.LENGTH_SHORT).show();

                // Return updated task and adapter position to HomeActivity
                Intent resultIntent = new Intent(this, HomeActivity.class);
                resultIntent.putExtra("updatedTask", updatedTask);
                resultIntent.putExtra("adapterPosition", adapterPosition);
                setResult(RESULT_OK, resultIntent);

                // Navigate back to HomeActivity
                startActivity(resultIntent);
                finish();
            });
        });
    }

    private int getImageResource(String state) {
        switch (state) {
            case "Done":
                return R.drawable.done;  // Image for "Done"
            case "Doing":
                return R.drawable.doing; // Image for "Doing"
            case "To Do":
                return R.drawable.to_do; // Image for "To Do"
            default:
                return R.drawable.to_do;
        }
    }
    private void deleteTask() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Fetch task from DB and delete it
            Task taskToDelete = db.taskDao().getTaskById(taskId);
            if (taskToDelete != null) {
                db.taskDao().deleteTask(taskToDelete);
            }

            handler.post(() -> {
                Toast.makeText(this, "Tâche supprimée avec succès", Toast.LENGTH_SHORT).show();

                // Return to HomeActivity after deletion
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Close the current activity
            });
        });
    }




}