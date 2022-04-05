package com.example.quiz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity
{
    TextView textViewWelcome;
    TextView textViewIndex;
    ProgressBar progressBar;
    TextView textViewQuestionTitle;
    TextView textViewQuestionDetails;
    String userName;

    int questionIndex;
    int totalQuestions;
    int totalCorrectAnswers;
    String questionTitle;
    String questionDetails;

    String answer1;
    String answer2;
    String answer3;
    String correctAnswer;
    String currentSelectedAnswer;
    Boolean answerIsSubmitted;

    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonSubmit;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewWelcome = findViewById(R.id.textViewWelcome);

        textViewIndex = findViewById(R.id.textViewIndex);
        progressBar = findViewById(R.id.progressBar);
        textViewQuestionTitle = findViewById(R.id.textViewQuestionTitle);
        textViewQuestionDetails = findViewById(R.id.textViewQuestionDetails);

        buttonAnswer1 = findViewById(R.id.button1);
        buttonAnswer2 = findViewById(R.id.button2);
        buttonAnswer3 = findViewById(R.id.button3);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Get extra from intent
        Intent intent = this.getIntent();
        userName = intent.getStringExtra("username");
        textViewWelcome.setText("Welcome" + " " + userName + "!");

        questionIndex = intent.getIntExtra("questionIndex", 0);
        totalQuestions = intent.getIntExtra("totalQuestions", 0);
        totalCorrectAnswers = intent.getIntExtra("totalCorrectAnswers", 0);

        switch(questionIndex) {
            case 1:
                questionTitle = "Question 1";
                questionDetails = "What is an Android version codename?";
                answer1 = "Oreo";
                answer2 = "iOS 14";
                answer3 = "iOS 15";
                correctAnswer = "Oreo";
                break;
            case 2:
                questionTitle = "Question 2";
                questionDetails = "What language is used to make an Android app?";
                answer1 = "C#";
                answer2 = "Java";
                answer3 = "Python";
                correctAnswer = "Java";
                break;
            case 3:
                questionTitle = "Question 3";
                questionDetails = "What IDE is used to make an Android app?";
                answer1 = "Android Studio";
                answer2 = "Lazarus";
                answer3 = "SharpDevelop";
                correctAnswer = "Android Studio";
                break;
            case 4:
                questionTitle = "Question 4";
                questionDetails = "What control allows user to perform an action?";
                answer1 = "TextView";
                answer2 = "Button";
                answer3 = "Neither";
                correctAnswer = "Button";
                break;
            case 5:
                questionTitle = "Question 5";
                questionDetails = "Where can you download android apps?";
                answer1 = "Google Play";
                answer2 = "Youtube";
                answer3 = "Facebook";
                correctAnswer = "Google Play";
                break;
            default:
                break;
        }

        textViewIndex.setText(questionIndex + "/" + totalQuestions);
        progressBar.setMax(totalQuestions * 10);
        progressBar.setProgress(questionIndex * 10, true);

        textViewQuestionTitle.setText(questionTitle);
        textViewQuestionDetails.setText(questionDetails);

        buttonAnswer1.setText(answer1);
        buttonAnswer2.setText(answer2);
        buttonAnswer3.setText(answer3);
        buttonSubmit.setText("Submit");

        answerIsSubmitted = false;

        // Note: As in the wireframe, only show Welcome title in the first Question
        if (questionIndex == 1) {
            textViewWelcome.setVisibility(View.VISIBLE);
        } else {
            textViewWelcome.setVisibility(View.INVISIBLE);
        }
    }

    public void answer1Click(View view) {
        if (answerIsSubmitted == false) {
            buttonAnswer1.setBackgroundResource(R.drawable.button_border);
            buttonAnswer2.setBackgroundResource(R.drawable.button_border_white);
            buttonAnswer3.setBackgroundResource(R.drawable.button_border_white);
            currentSelectedAnswer = answer1;
        }
    }

    public void answer2Click(View view) {
        // Dont allow user to change the answer after Submitting
        if (answerIsSubmitted == false) {
            buttonAnswer2.setBackgroundResource(R.drawable.button_border);
            buttonAnswer1.setBackgroundResource(R.drawable.button_border_white);
            buttonAnswer3.setBackgroundResource(R.drawable.button_border_white);
            currentSelectedAnswer = answer2;
        }
    }

    public void answer3Click(View view) {
        // Dont allow user to change the answer after Submitting
        if (answerIsSubmitted == false) {
            buttonAnswer3.setBackgroundResource(R.drawable.button_border);
            buttonAnswer1.setBackgroundResource(R.drawable.button_border_white);
            buttonAnswer2.setBackgroundResource(R.drawable.button_border_white);
            currentSelectedAnswer = answer3;
        }
    }

    public void updateStatusOfAllButtons() {
        updateButton(buttonAnswer1);
        updateButton(buttonAnswer2);
        updateButton(buttonAnswer3);
    }

    public void updateButton(Button button) {
        String buttonTitle = button.getText().toString();

        if (!buttonTitle.equals(correctAnswer) && !buttonTitle.equals(currentSelectedAnswer)) {
            button.setBackgroundResource(R.drawable.button_border_white);
        } else {
            if (buttonTitle.equals(currentSelectedAnswer) && !buttonTitle.equals(correctAnswer)) {
                button.setBackgroundResource(R.drawable.button_border_red);
            } else if (buttonTitle.equals(correctAnswer)) {
                button.setBackgroundResource(R.drawable.button_border_green);
            }
        }
    }

    public void submitClick(View view) {

        if (TextUtils.isEmpty(currentSelectedAnswer) == true) {
            AlertDialog alertDialog = new AlertDialog.Builder(DetailActivity.this).create();
            alertDialog.setTitle("");
            alertDialog.setMessage("Please answer the question!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        } else {
            if (answerIsSubmitted == false) {
                updateStatusOfAllButtons();
                answerIsSubmitted = true;

                if (currentSelectedAnswer.equals(correctAnswer)) {
                    totalCorrectAnswers += 1;
                }

                // Change title from Submit to Next
                buttonSubmit.setText("Next");
            } else {
                if (questionIndex < totalQuestions) {
                    Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                    intent.putExtra("username", userName);
                    intent.putExtra("questionIndex", questionIndex + 1);
                    intent.putExtra("totalQuestions", totalQuestions);
                    intent.putExtra("totalCorrectAnswers", totalCorrectAnswers);
                    startActivity(intent);
                }  else {
                    Intent intent = new Intent(DetailActivity.this, ResultActivity.class);
                    intent.putExtra("username", userName);
                    intent.putExtra("totalQuestions", totalQuestions);
                    intent.putExtra("totalCorrectAnswers", totalCorrectAnswers);
                    startActivity(intent);
                }
            }
        }
    }
}
