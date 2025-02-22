package tn.esprit.examenblancandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText usernametxt, passwordtxt;
    Button loginbtn;
    private SharedPreferences mPreferences;
    public static final String SharedPrefFile = "tn.esprit.examenblancandroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernametxt = findViewById(R.id.usernametxt);
        passwordtxt = findViewById(R.id.passwordtxt);
        loginbtn = findViewById(R.id.loginbtn);

        mPreferences = getSharedPreferences(SharedPrefFile, MODE_PRIVATE);

        usernametxt.setText(mPreferences.getString("usernametxt", ""));
        passwordtxt.setText(mPreferences.getString("passwordtxt", ""));

        String savedUsername = mPreferences.getString("usernametxt", "");
        String savedPassword = mPreferences.getString("passwordtxt", "");
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            usernametxt.setText(savedUsername);
            passwordtxt.setText(savedPassword);
        }

        loginbtn.setOnClickListener(v ->{
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("usernametxt", usernametxt.getText().toString());
            editor.putString("passwordtxt", passwordtxt.getText().toString());
            editor.apply();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
        });
    }
}