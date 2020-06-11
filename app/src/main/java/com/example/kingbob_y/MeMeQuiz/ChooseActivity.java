package com.example.kingbob_y.MeMeQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ChooseActivity extends AppCompatActivity {
    private static final String TAG = "ChooseActivity";
    ArrayList<Quiz> quiz = new ArrayList<Quiz>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        if (intent !=null) {
            //Toast.makeText(this, "exiting", Toast.LENGTH_LONG).show();
            quiz = intent.getParcelableArrayListExtra("quiz");
        }



        setContentView(R.layout.activity_choose);
        Button mTeacherButton, mStudentButton;

        ImageButton mIntroduceButton;


        mTeacherButton = findViewById(R.id.teacher_button);
        mStudentButton = findViewById(R.id.student_button);
        mIntroduceButton = findViewById(R.id.introduce_button);

        mTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,
                        QuizActivity.class);
                startActivity(intent);
            }
        });

        mStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,
                        DisplayyActivity.class);
                intent.putParcelableArrayListExtra("quiz", quiz);
                startActivity(intent);
            }
        });


        mIntroduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,
                        IntroduceActivity.class);
                startActivity(intent);
            }
        });


    }
}
