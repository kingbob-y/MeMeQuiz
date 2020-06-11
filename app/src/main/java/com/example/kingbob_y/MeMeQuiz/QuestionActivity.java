package com.example.kingbob_y.MeMeQuiz;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;
import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    private ArrayList<Quiz> quizList;
    private boolean checkAnswered[];
    private String question;
    private String answerSubmit = "";
    private int questionType;
    private TextView mQuestionTextView;
    private TextInputLayout mTextShortAnswerLayoutQuiz;
    private EditText mShortAnswerQuiz;
    private ImageButton mPrevButton, mNextButton;
    private Button mBackButtonQuiz, mSubmitButtonQuiz;
    private int mTotal = 0; //
    private int mCurrentIndex = 0; //
    private static final String KEY_INDEX = "index";
    private static final String TAG = "QuestionActivity";

    /**
     * From "IBM hangul-tensordoid" project
     */
    private static final String LABEL_FILE = "2350-common-hangul.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";
    private static final int WORD_SIZE = 3;

    private HangulClassifier classifier;
    private PaintView[] paintView = new PaintView[WORD_SIZE];
    private Button[][] alt = new Button[WORD_SIZE][4];
    private Button[] clearButton = new Button[WORD_SIZE];
    private LinearLayout[] altLayout = new LinearLayout[WORD_SIZE];
    private String[][] currentTopLabels = new String[WORD_SIZE][];
    private int EmptyFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        final ArrayList<Quiz> quiz = intent.getParcelableArrayListExtra("quiz");
        quizList = quiz;
        checkAnswered = new boolean[quizList.size()];
        mQuestionTextView = findViewById(R.id.text_view);
        mTextShortAnswerLayoutQuiz = findViewById(R.id.short_answer_layout_quiz);

        mPrevButton = findViewById(R.id.prev_button);
        mNextButton = findViewById(R.id.next_button);


        mShortAnswerQuiz = findViewById(R.id.short_answer_question_text_quiz);

        for(int i = 0; i < checkAnswered.length; i++) {
            Log.d(TAG, "Boolean value: " + checkAnswered[i]);
        }



        if (savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        Log.d(TAG, "The number type is: " + questionType);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % quizList.size();
                updateQuestion();
                updateType();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1);
                if(mCurrentIndex < 0) mCurrentIndex = quiz.size() - 1;
                updateQuestion();
                updateType();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % quiz.size();
                updateQuestion();
                updateType();
            }
        });

        mBackButtonQuiz = findViewById(R.id.back_button2);
        mBackButtonQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSubmitButtonQuiz = findViewById(R.id.submit_button_quiz);

        mSubmitButtonQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast;
                Context context = getApplicationContext();
                switch(questionType) {
                    case 0: //Multiple Choice

                        break;
                    case 1: //Multiple Answer Choices

                        break;
                    case 2: //True/False

                        break;
                    case 3: //Short Answer
                        if(mShortAnswerQuiz.getText().toString().length() > 0) {
                            submitDisable();
                            shortAnswerDisable();
                            answerSubmit = mShortAnswerQuiz.getText().toString();
                            quizList.get(mCurrentIndex).setUserAnswer(answerSubmit);
                            if(answerSubmit.toLowerCase().equals(quizList.get(mCurrentIndex).getAnswer().toLowerCase())) {
                                toast = Toast.makeText(context, "전송 완료", Toast.LENGTH_LONG);
                            }
                            else toast =Toast.makeText(context, "전송 완료",
                                    Toast.LENGTH_LONG);
                            mTotal++;
                        }
                        else {
                            toast = Toast.makeText(context, "정답을 입력하세요.",
                                    Toast.LENGTH_SHORT);
                        }
                        toast.show();
                        break;
                }
                clearAnswers();
                Log.d(TAG, "mTotal: " + mTotal + ", quizList: " + quizList.size());
                if(mTotal >= quizList.size()) {
                    Intent intent = new Intent(QuestionActivity.this,
                            ResultActivity.class);
                    intent.putParcelableArrayListExtra("quiz", quiz);
                    startActivity(intent);
                    Log.d(TAG, "Started ResultActivity");
                }

                for (int i=0; i<WORD_SIZE; i++){
                    altLayout[i].setVisibility(View.INVISIBLE);
                }

            }
        });
        updateQuestion();
        updateType();



        /**
         * From "IBM hangul-tensordoid" project
         */

        paintView[0] = (PaintView) findViewById(R.id.paintView);
        paintView[1] = (PaintView) findViewById(R.id.paintView2);
        paintView[2] = (PaintView) findViewById(R.id.paintView3);

        /**word size가 커지면 코드 추가 필요**/

        TextView[] drawHereText = new TextView[WORD_SIZE];
        drawHereText[0] = (TextView) findViewById(R.id.drawHere);
        drawHereText[1] = (TextView) findViewById(R.id.drawHere2);
        drawHereText[2] = (TextView) findViewById(R.id.drawHere3);

        /**word size가 커지면 코드 추가 필요**/
        for (int i=0; i<WORD_SIZE; i++){
            paintView[i].setDrawText(drawHereText[i]);
        }

        clearButton[0] = (Button) findViewById(R.id.buttonClear);
        clearButton[1] = (Button) findViewById(R.id.buttonClear2);
        clearButton[2] = (Button) findViewById(R.id.buttonClear3);

        /**word size가 커지면 코드 추가 필요**/

        for (int i=0; i<WORD_SIZE; i++){
            final int finalI = i;
            clearButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   clear(finalI);
                }
            });
        }

        Button clear_allButton= (Button) findViewById(R.id.buttonClearAll);
        clear_allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<WORD_SIZE; i++){
                    clear(i);
                }
                mShortAnswerQuiz.setText("");
            }
        });



        Button classifyButton = (Button) findViewById(R.id.buttonClassify);
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for (int i=0; i<WORD_SIZE; i++){
                    count = count + classify(i);
                    paintView[i].reset();
                    paintView[i].invalidate();
                }
                EmptyFlag = count;
            }
        });


        Button backspaceButton = (Button) findViewById(R.id.buttonBackspace);
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backspace();
                for (int i=0; i<WORD_SIZE; i++){
                    clear(i);
                }
            }
        });


        Button spaceButton = (Button) findViewById(R.id.buttonSpace);
        spaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                space();
            }
        });


        altLayout[0] = (LinearLayout) findViewById(R.id.altLayout);
        altLayout[1] = (LinearLayout) findViewById(R.id.altLayout2);
        altLayout[2] = (LinearLayout) findViewById(R.id.altLayout3);

        /**word size가 커지면 코드 추가 필요**/
        for (int i=0; i<WORD_SIZE; i++){
            altLayout[i].setVisibility(View.INVISIBLE);
        }


        alt[0][0] = (Button) findViewById(R.id.alt1);
        alt[0][1] = (Button) findViewById(R.id.alt2);
        alt[0][2] = (Button) findViewById(R.id.alt3);
        alt[0][3] = (Button) findViewById(R.id.alt4);
        alt[1][0] = (Button) findViewById(R.id.alt1_2);
        alt[1][1] = (Button) findViewById(R.id.alt2_2);
        alt[1][2] = (Button) findViewById(R.id.alt3_2);
        alt[1][3] = (Button) findViewById(R.id.alt4_2);
        alt[2][0] = (Button) findViewById(R.id.alt1_3);
        alt[2][1] = (Button) findViewById(R.id.alt2_3);
        alt[2][2] = (Button) findViewById(R.id.alt3_3);
        alt[2][3] = (Button) findViewById(R.id.alt4_3);

        /**word size가 커지면 코드 추가 필요**/




        for (int i=0; i<WORD_SIZE; i++){
            for (int j=0; j<4; j++) {
                final int finalI = i;
                alt[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //useAltLabel(finalI,Integer.parseInt(v.getTag().toString())); //tag1,2,3,4 xml파일에 있음
                        useAltLabel_practice(finalI,Integer.parseInt(v.getTag().toString()),EmptyFlag);
                    }
                });
            }
        }


        loadModel(); //메모리에 모델 불러오기



    }





    private void shortAnswerEnable() {
        mShortAnswerQuiz.setEnabled(true);
    }

    private void shortAnswerDisable() {
        mShortAnswerQuiz.setEnabled(false);
    }



    private void submitDisable() {
        checkAnswered[mCurrentIndex] = true;
        mSubmitButtonQuiz.setEnabled(false);
    }

    private void updateQuestion() {
        question = (mCurrentIndex + 1) + ". " + quizList.get(mCurrentIndex).getQuestion();
        mQuestionTextView.setText(question);
        if(checkAnswered[mCurrentIndex]) mSubmitButtonQuiz.setEnabled(false);
        else mSubmitButtonQuiz.setEnabled(true);
        clearAnswers();
    }

    private void updateType() {
        questionType = quizList.get(mCurrentIndex).getType();
        int counter = 0;
        switch(questionType) {
            case 3: //Short Answer
                if(!checkAnswered[mCurrentIndex]) shortAnswerEnable();
                else shortAnswerDisable();
                mTextShortAnswerLayoutQuiz.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void clearAnswers() {
        mShortAnswerQuiz.setText("");

    }



    /**
     * From "IBM hangul-tensordoid" project
     */



    private void backspace() {
        int len = mShortAnswerQuiz.getText().length();
        if (len > 0) {
            mShortAnswerQuiz.getText().delete(len - 1, len);
        }
    }

    private void space() {
        mShortAnswerQuiz.append(" ");
    }



    private void clear(int i) {
        paintView[i].reset();
        paintView[i].invalidate();
        //mShortAnswerQuiz.setText("");
        altLayout[i].setVisibility(View.INVISIBLE);
    }


    private int classify(int i) {
        float pixels[] = paintView[i].getPixelData();
        int flag = 1;
        for (int k = 0; k< pixels.length; k++){
            if (pixels[k]==1) {
                flag = 0;
            }
        }
        if (flag == 0) {
            currentTopLabels[i] = classifier.classify(pixels);
            mShortAnswerQuiz.append(currentTopLabels[i][0]);
            altLayout[i].setVisibility(View.VISIBLE);
            for (int j = 0; j < 4; j++) {
                alt[i][j].setText(currentTopLabels[i][j + 1]);
            }
        }
        return flag;
    }


    private void useAltLabel(int word_number, int index) {


        switch(word_number) {
            case 0:
                String Buff = mShortAnswerQuiz.getText().toString();
                String Buff2 = Buff.substring(1); //buff의 첫번째 글자만 삭제
                mShortAnswerQuiz.setText("");
                mShortAnswerQuiz.append(currentTopLabels[0][index]);
                mShortAnswerQuiz.append(Buff2);
                break;
            case 1:
                backspace();
                mShortAnswerQuiz.append(currentTopLabels[1][index]);
                break;
            default :
                break;

        }
    }


    private void useAltLabel_practice(int word_number, int index, int emptycount) {

        //만약 WORD_SIZE = 3, word_number =1 (2번째글자를), index =2(3번째 추천글자로 바꿀라면) 라면
        for (int i=0; i<WORD_SIZE; i++){
            if (word_number == i) { //word_number마다 다른 명령어를 실행하겠다, 예시의 경우 i=2일때 만족.
                String Buff = mShortAnswerQuiz.getText().toString(); //정답칸 문자열을 저장
                String Buff2 = Buff.substring(i+1-emptycount); //buff의 i+1 (예시의 경우 3)번째 글자까지 삭제
                for (int j=0; j<WORD_SIZE-word_number; j++){ backspace(); } //(3-2) 1번 실행
                mShortAnswerQuiz.append(currentTopLabels[word_number][index]);
                mShortAnswerQuiz.append(Buff2);
            }

        }

    }

    @Override
    protected void onResume() { //////??
        for (int i=0; i<WORD_SIZE;i++){
            paintView[i].onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() { //////??
        for (int i=0; i<WORD_SIZE;i++){
            paintView[i].onPause();
        }
        super.onPause();
    }

    private void loadModel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = HangulClassifier.create(getAssets(),
                            MODEL_FILE, LABEL_FILE, PaintView.FEED_DIMENSION,
                            "input", "keep_prob", "output");
                } catch (final Exception e) {
                    throw new RuntimeException("Error loading pre-trained model.", e);
                }
            }
        }).start();
    }



}
