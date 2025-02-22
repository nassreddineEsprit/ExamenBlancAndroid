package tn.esprit.examenblancandroid;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHold extends RecyclerView.ViewHolder{


    public ImageView imgTask;
    public TextView tasktxtview;
    public TextView usertxtview;
    public TextView stateTask;


    public TaskViewHold (@NonNull View itemView) {
        super(itemView);
        imgTask = itemView.findViewById(R.id.imgTask);
        tasktxtview = itemView.findViewById(R.id.tasktxtview);
        usertxtview = itemView.findViewById(R.id.usertxtview);
        stateTask = itemView.findViewById(R.id.stateTask);
    }

}
