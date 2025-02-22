package tn.esprit.examenblancandroid.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tn.esprit.examenblancandroid.entity.Task;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM tasks WHERE idTask = :taskId LIMIT 1")
    Task getTaskById(int taskId);
}