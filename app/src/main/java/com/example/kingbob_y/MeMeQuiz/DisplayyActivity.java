package com.example.kingbob_y.MeMeQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;

import java.util.ArrayList;

public class DisplayyActivity extends AppCompatActivity {
    private static final String TAG = "DisplayyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayy);
        Button mPlayButton;

        Intent intent = getIntent();
        final ArrayList<Quiz> quiz = intent.getParcelableArrayListExtra("quiz");
        TableLayout tableLayout = findViewById(R.id.displayy_activity);
        TableRow.LayoutParams layoutParams;
        TableRow[] tableRow = new TableRow[quiz.size()];

        mPlayButton = findViewById(R.id.play_button);

        for(int i = 0; i < quiz.size(); i++) {
            tableRow[i] = new TableRow(getApplicationContext());

            TextView numberText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1.1f);
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

            //TextView answerText = new TextView(getApplicationContext());
            //layoutParams = new TableRow.LayoutParams(0,
            //        TableRow.LayoutParams.WRAP_CONTENT, 4f);
            //answerText.setLayoutParams(layoutParams);
            //answerText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            //answerText.setTextColor(Color.WHITE);
            //answerText.setPaddingRelative(0,0,0,20);
            //answerText.setText(quiz.get(i).getAnswer());

            tableRow[i].addView(numberText);
            tableRow[i].addView(questionText);
            //tableRow[i].addView(answerText);

            tableLayout.addView(tableRow[i]);
        }


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayyActivity.this,
                        QuestionActivity.class);
                intent.putParcelableArrayListExtra("quiz", quiz);
                startActivity(intent);
                Log.d(TAG, "Started QuestionActivity");
            }
        });


        Button mhomeButton_std;
        mhomeButton_std = findViewById(R.id.Button_std_home);

        mhomeButton_std.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(DisplayyActivity.this,ChooseActivity.class);

                startActivity(intent);
            }
        });
    }
}
