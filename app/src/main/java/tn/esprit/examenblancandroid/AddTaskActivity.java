package tn.esprit.examenblancandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import tn.esprit.examenblancandroid.database.AppDatabase;
import tn.esprit.examenblancandroid.entity.Task;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskNameEditText, userNameEditText;
    private Button saveButton;
    private AppDatabase db;
    private RadioButton done, doing, todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskNameEditText = findViewById(R.id.taskNameEditText);
        userNameEditText = findViewById(R.id.userNameEditText);
        done = findViewById(R.id.done);
        doing = findViewById(R.id.doing);
        todo = findViewById(R.id.todo);
        saveButton = findViewById(R.id.saveButton);

        db = AppDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> {
            String taskName = taskNameEditText.getText().toString();
            String userName = userNameEditText.getText().toString();
            String state = "";

            if (done.isChecked()) {
                state = "Done";
            } else if (doing.isChecked()) {
                state = "Doing";
            } else if (todo.isChecked()) {
                state = "To Do";
            }

            Task task = new Task(taskName, userName, state, R.drawable.ic_ahri);
            new InsertTaskAsyncTask(this, db, task).execute();  // Pass 'this' as context
        });
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Void, Void, Task> {
        private final AppDatabase db;
        private final Task task;
        private final Activity activity; // Reference to AddTaskActivity

        InsertTaskAsyncTask(Activity activity, AppDatabase db, Task task) {
            this.activity = activity;
            this.db = db;
            this.task = task;
        }

        @Override
        protected Task doInBackground(Void... voids) {
            db.taskDao().insertTask(task);
            return task; // Return the inserted task
        }

        @Override
        protected void onPostExecute(Task task) {
            Intent intent = new Intent();
            intent.putExtra("taskName", task.getNomTask());
            intent.putExtra("userName", task.getNomUser());
            intent.putExtra("state", task.getStateTask());
            intent.putExtra("imageResId", task.getImageResId());

            // Now we can call setResult() using the activity reference
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish(); // Close the activity
        }
    }
}
