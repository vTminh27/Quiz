package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity
{
    TextView textViewCongrats;
    TextView textViewScore;
    String userName;

    int totalQuestion;
    int totalCorrectAnswers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewCongrats = findViewById(R.id.textViewCongrats);
        textViewScore = findViewById(R.id.textViewScore);

        Intent intent = this.getIntent();
        userName = intent.getStringExtra("username");
        totalQuestion = intent.getIntExtra("totalQuestions", 0);
        totalCorrectAnswers = intent.getIntExtra("totalCorrectAnswers", 0);

        // Show congrats only if score is greater than Zero
        if (totalCorrectAnswers > 0) {
            textViewCongrats.setText("Congratulations " + userName + "!");
        } else {
            textViewCongrats.setText("Oops, " + userName);
        }

        textViewScore.setText(totalCorrectAnswers + "/" + totalQuestion);
    }

    public void newQuizClick(View view) {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        intent.putExtra("username", userName);
        startActivity(intent);
    }

    public void finishClick(View view) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

        // I found it there
        // https://stackoverflow.com/questions/9974792/how-to-close-an-android-application
    }
}
