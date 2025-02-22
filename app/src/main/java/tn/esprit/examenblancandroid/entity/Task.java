package tn.esprit.examenblancandroid.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tasks")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int idTask;
    public String nomTask;

    public String nomUser;
    public String stateTask;
    public int imageResId;

    public Task() {
    }

    public Task(String nomTask, String nomUser, String stateTask, int imageResId) {
        this.nomTask = nomTask;
        this.nomUser = nomUser;
        this.stateTask = stateTask;
        this.imageResId = imageResId;
    }

    public Task(int idTask, String nomTask, String nomUser, String stateTask, int imageResId) {
        this.idTask = idTask;
        this.nomTask = nomTask;
        this.nomUser = nomUser;
        this.stateTask = stateTask;
        this.imageResId = imageResId;
    }

    public String getNomTask() {
        return nomTask;
    }

    public String getStateTask() {
        return stateTask;
    }

    public void setNomTask(String nomTask) {
        this.nomTask = nomTask;
    }

    public void setStateTask(String stateTask) {
        this.stateTask = stateTask;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", nomTask='" + nomTask + '\'' +
                ", nomUser='" + nomUser + '\'' +
                ", stateTask='" + stateTask + '\'' +
                ", imageResId=" + imageResId +
                '}';
    }
}
