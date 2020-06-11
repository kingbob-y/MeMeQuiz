package com.example.kingbob_y.MeMeQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class IntroduceActivity extends AppCompatActivity {
    private static final String TAG = "IntroduceActivity";
    ArrayList<Quiz> quiz = new ArrayList<Quiz>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent !=null) {
            //Toast.makeText(this, "exiting", Toast.LENGTH_LONG).show();
            quiz = intent.getParcelableArrayListExtra("quiz");
        }

        setContentView(R.layout.activity_introduce);
        ImageButton mImageButton2;

        mImageButton2 = findViewById(R.id.imageButton4);

        ImageView imageView = (ImageView) findViewById(R.id.imageView2) ;
        imageView.setImageResource(R.drawable.main) ;

        mImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroduceActivity.this,
                        ChooseActivity.class);
                startActivity(intent);
            }
        });

    }
}
