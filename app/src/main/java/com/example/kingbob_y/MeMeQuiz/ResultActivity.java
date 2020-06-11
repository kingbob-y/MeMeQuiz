package com.example.kingbob_y.MeMeQuiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    double score = 0;
    private static final String TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Button mRetakeButton, mHomeButton;
        Intent intent = getIntent();
        final ArrayList<Quiz> quiz = intent.getParcelableArrayListExtra("quiz");
        TableLayout tableLayout = findViewById(R.id.result_activity);
        TableRow.LayoutParams layoutParams;
        TableRow[] tableRow = new TableRow[quiz.size()];
        TableRow[] scoreRow = new TableRow[1];

        mRetakeButton = findViewById(R.id.retake_button);
        mHomeButton = findViewById(R.id.home_button);

        for(int i = 0; i < quiz.size(); i++) {
            tableRow[i] = new TableRow(getApplicationContext());

            TextView numberText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,
                    1.1f);
            numberText.setLayoutParams(layoutParams);
            numberText.setGravity(Gravity.END);
            numberText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            numberText.setTextColor(Color.BLACK);
            numberText.setPaddingRelative(0,0,0,20);
            numberText.setText(getResources().getString(R.string.number, i + 1));


            TextView questionText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 5f);
            questionText.setLayoutParams(layoutParams);
            questionText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            questionText.setTextColor(Color.BLACK);
            questionText.setPaddingRelative(10,0,15,20);
            questionText.setText(quiz.get(i).getQuestion());

            TextView userAnswerText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,
                    4f);
            userAnswerText.setLayoutParams(layoutParams);
            userAnswerText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            userAnswerText.setPaddingRelative(0,0,0,20);
            userAnswerText.setText(quiz.get(i).getUserAnswer());

            TextView answerText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,
                    4f);
            answerText.setLayoutParams(layoutParams);
            answerText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            answerText.setPaddingRelative(0,0,0,20);
            answerText.setText(quiz.get(i).getAnswer());

            Log.d(TAG, "The user answer: " + quiz.get(i).getUserAnswer() + "-- The correct " +
                    "answer: " + quiz.get(i).getAnswer());
            if(quiz.get(i).getUserAnswer().toLowerCase().equals(quiz.get(i).getAnswer().toLowerCase())) {
                userAnswerText.setTextColor(Color.GREEN);
                score++;
            }
            else userAnswerText.setTextColor(Color.RED);

            tableRow[i].addView(numberText);
            tableRow[i].addView(questionText);
            tableRow[i].addView(userAnswerText);
            tableRow[i].addView(answerText);

            tableLayout.addView(tableRow[i]);
        }

        score = score / quiz.size() * 100;
        scoreRow[0] = new TableRow(getApplicationContext());
        TextView scoreText = new TextView((getApplicationContext()));
        layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,
                1.1f);
        scoreText.setLayoutParams(layoutParams);
        scoreText.setTextSize(getResources().getDimension(R.dimen.scoreSize));
        scoreText.setPaddingRelative(40,40,0,0);
        scoreRow[0].addView(scoreText);
        tableLayout.addView(scoreRow[0]);
        scoreText.setText(String.format("Score: %.02f%%", score));

        mRetakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,
                        QuestionActivity.class);
                intent.putParcelableArrayListExtra("quiz", quiz);
                startActivity(intent);
                Log.d(TAG, "Finished quiz");
            }
        });


        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,
                        ChooseActivity.class);
                intent.putParcelableArrayListExtra("quiz", quiz);
                startActivity(intent);
                Log.d(TAG, "Finished quiz");
            }
        });

    }
}

