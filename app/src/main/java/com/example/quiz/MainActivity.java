package com.example.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);

        // Check if have user name already
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("username");
        if (!TextUtils.isEmpty(name)) {
            userName = name;
            editTextName.setText(userName);
        }
    }

    public void startClick(View view) {
        String name = editTextName.getText().toString();

        // Check case name is empty
        if (TextUtils.isEmpty(name)) {
            Log.d(MainActivity.this.toString(), "User did not enter the name");
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("");
            alertDialog.setMessage("Please enter your name!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        } else {
            Log.d(MainActivity.this.toString(), "User entered the name");
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("username", name);
            intent.putExtra("questionIndex", 1);
            intent.putExtra("totalQuestions", 5);
            intent.putExtra("totalCorrectAnswers", 0);
            startActivity(intent);
        }
    }
}