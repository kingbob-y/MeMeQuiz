package com.example.kingbob_y.MeMeQuiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    ArrayList<Quiz>quiz = new ArrayList<>();

    private TextInputLayout mTextInputLayout, mTextShortAnswerLayout;
    private TextInputEditText mEditText;
    private Button mDoneButton;
    private String questionText, shortAnswerText;
    private EditText mShortAnswer;
    private String answerList = "";
    private static final String TAG = "QuizActivity";

    public QuizActivity() {

    }

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        TextInputEditText mInputEditText;
        Button mSubmitButton, mClearButton;


        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                //R.array.question_option, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner


        mDoneButton= findViewById(R.id.done_button);
        mTextShortAnswerLayout = findViewById(R.id.short_answer_layout);





        mTextInputLayout = findViewById(R.id.text_input_layout);
        mEditText = findViewById(R.id.edit_text);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > mTextInputLayout.getCounterMaxLength())
                    mTextInputLayout.setError("Max character length is " +
                            mTextInputLayout.getCounterMaxLength());
                else
                    mTextInputLayout.setError(null);
            }
        });

        mInputEditText = findViewById(R.id.edit_text);

        mShortAnswer = findViewById(R.id.short_answer_text);
        mInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });




        mShortAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        mSubmitButton = findViewById(R.id.submit_button);
        mClearButton = findViewById(R.id.clear_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                questionText = mEditText.getText().toString();
                Toast toast = Toast.makeText(context, "Enter in a question!",
                        Toast.LENGTH_SHORT);
                if (questionText.length() > 500) {
                    toast = Toast.makeText(context, "Error: Question is too long (Max 500)",
                            Toast.LENGTH_SHORT);
                }

                shortAnswerText = mShortAnswer.getText().toString();
                if (shortAnswerText.length() > 30) {
                    toast = Toast.makeText(context, "Error: Answer is too long " +
                            "(Max 30)", Toast.LENGTH_SHORT);
                }
                else if (shortAnswerText.length() > 0) {
                    quiz.add(new Quiz(questionText, shortAnswerText, 3,
                            shortAnswerText));
                    toast = Toast.makeText(context, shortAnswerText +
                            "\n\nSubmitted!", Toast.LENGTH_SHORT);
                    clearForm();
                } else {
                    toast = Toast.makeText(context, "Fill in short answer!",
                            Toast.LENGTH_SHORT);
                }
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });

        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quiz.isEmpty()) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Quiz is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(QuizActivity.this,
                            DisplayActivity.class);
                    intent.putParcelableArrayListExtra("quiz", quiz);
                    startActivity(intent);
                    Log.d(TAG, "Started DisplayActivity");
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm =
                (InputMethodManager)getSystemService(QuizActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    public void clearForm() {
        mEditText.setText("");
        mEditText.setText("");
        mShortAnswer.setText("");

    }

    /*
    public void onRadioButtonClicked(CompoundButton buttonView) {
        boolean checkClicked = ((RadioButton) buttonView).isChecked();
        switch(buttonView.getId()) {
            case R.id.radio_a:
                mRadioB.setChecked(!checkClicked);
                mRadioC.setChecked(!checkClicked);
                mRadioD.setChecked(!checkClicked);
                break;
            case R.id.radio_b:
                mRadioA.setChecked(!checkClicked);
                mRadioC.setChecked(!checkClicked);
                mRadioD.setChecked(!checkClicked);
                break;
            case R.id.radio_c:
                mRadioA.setChecked(!checkClicked);
                mRadioB.setChecked(!checkClicked);
                mRadioD.setChecked(!checkClicked);
                break;
            case R.id.radio_d:
                mRadioA.setChecked(!checkClicked);
                mRadioB.setChecked(!checkClicked);
                mRadioC.setChecked(!checkClicked);
                break;
        }
    }
    */
}