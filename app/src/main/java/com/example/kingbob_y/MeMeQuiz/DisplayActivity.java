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

public class DisplayActivity extends AppCompatActivity { //메소드
    private static final String TAG = "DisplayActivity";

    @Override //상속된 메소드의 내용을 자식 클래스에서 수정
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //명시적으로 부모생성자 호출
        setContentView(R.layout.activity_display); //display content
        Button mBackButton, mFinishButton, mResetButton; // 버튼생성

        Intent intent = getIntent();
        final ArrayList<Quiz> quiz = intent.getParcelableArrayListExtra("quiz"); //최종상태
        TableLayout tableLayout = findViewById(R.id.display_activity);
        TableRow.LayoutParams layoutParams;
        TableRow[] tableRow = new TableRow[quiz.size()];

        mBackButton = findViewById(R.id.back_button);
        mFinishButton = findViewById(R.id.finish_button);
        mResetButton = findViewById(R.id.reset_button); //문제 생성 이후 Review 화면

        for(int i = 0; i < quiz.size(); i++) {
            tableRow[i] = new TableRow(getApplicationContext()); //생성된 문제 갯수

            TextView numberText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1.1f);
            numberText.setLayoutParams(layoutParams);
            numberText.setGravity(Gravity.END);
            numberText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            numberText.setTextColor(Color.BLACK);
            numberText.setPaddingRelative(0,0,0,20);
            numberText.setText(getResources().getString(R.string.number, i + 1)); //Review 화면 1열 :　문제 번호

            TextView questionText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 5f);
            questionText.setLayoutParams(layoutParams);
            questionText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            questionText.setTextColor(Color.BLACK);
            questionText.setPaddingRelative(10,0,15,20);
            questionText.setText(quiz.get(i).getQuestion()); // Review 화면 2열 : 문제 제목

            TextView answerText = new TextView(getApplicationContext());
            layoutParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 4f);
            answerText.setLayoutParams(layoutParams);
            answerText.setTextSize(getResources().getDimension(R.dimen.displaySize));
            answerText.setTextColor(Color.BLACK);
            answerText.setPaddingRelative(0,0,0,20);
            answerText.setText(quiz.get(i).getAnswer()); //Review 화면 1열  : 정답

            tableRow[i].addView(numberText);
            tableRow[i].addView(questionText);
            tableRow[i].addView(answerText); //Review 화면  맨 윗줄 number-question-answer

            tableLayout.addView(tableRow[i]);
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this,
                        QuestionActivity.class);
                intent.putParcelableArrayListExtra("quiz", quiz);
                startActivity(intent);
                Log.d(TAG, "Started QuestionActivity");
            }
        });*/

        mFinishButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(DisplayActivity.this,ChooseActivity.class);
                intent.putParcelableArrayListExtra("quiz", quiz);
                startActivity(intent);
                Log.d(TAG, "Started ChooseActivity");

            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Log.d(TAG, "Started DisplayyActivity");
            }
        });




    }
}
