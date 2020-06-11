package com.example.kingbob_y.MeMeQuiz; // 관리의 편의를 위해 해당 package 내에 아래의 클래스들을 정의하겠다 (상위 패키지 폴더 안에 5개의 java 코드 파일이 있는 걸 확인)

import android.os.Parcel;
import android.os.Parcelable;

/*
간단히 말하자면 함수의 인수로 class단위을 사용하기 위해 parcel 기능을 (안드로이드에서 제공) 추가
어플 구동을 위해 데이터를 A Activitiy -> B Activity로 넘겨받을 때 parcel을 사용한다.
하나의 Class화 된 여러 데이터들이 순서대로 Byte형식으로 변환되어 직렬로 A -> B로 전달된다. 일종의 소포.
ex) 데이터를 주고받을 때 class 통째로 공유하는 것이 아닌, 0101011101..등 직렬화시켜 전송.
따라서 직렬화하는 메소드와 직렬화를 풀어주는 메소드를 정의해주어야 한다.

https://developer88.tistory.com/64
http://devstory.ibksplatform.com/2018/05/android-parcelable.html
심화 내용은 링크 참조
*/

// class Quiz 선언
public class Quiz implements Parcelable { //"implements Parcelable"- parcel이 가능한 class로 선언하겠다
    private String mQuestion; // 문자열 변수 - 문제
    private String mAnswer; // 문자열 변수 - 정답
    private int mType; // 정수형 변수 - 문제 유형 (1.객관식, 2.주관식 등)
    private String mAnswerChoice; // 객관식 정답
    private String mUserAnswer;  // 문자열 변수 - 사용자의 정답

    public Quiz(String question, String answer, int type, String answerChoice) {
        mQuestion = question;
        mAnswer = answer;
        mType = type;
        mAnswerChoice = answerChoice;
    } // 외부에서 class Quiz를 생성하는 함수 (mQuestion, mAnswer, mType, mAnswerChoice를 입력값으로 하는 class를)

    public String getQuestion() {
        return mQuestion;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public int getType() {
        return mType;
    }

    public String getAnswerChoice() { return mAnswerChoice; }

    public String getUserAnswer() { return mUserAnswer; }

    // 5개의 get 함수 - mQuestion, mAnswer, mType, mAnswerChoice, mUserAnswer 변수값을 알고 싶을 때 사용

    public void setUserAnswer(String userAnswer) {mUserAnswer = userAnswer; };

    // 사용자가 고른 정답을 mUserAnswer 변수에 저장하는 함수

    protected Quiz(Parcel in) {
        mQuestion = in.readString();
        mAnswer = in.readString();
        mType = in.readInt();
        mAnswerChoice = in.readString();
        mUserAnswer = in.readString();
    }
    // 외부에서 클래스를 생성하는 함수가 아닌, creator가 사용하는 생성자.
    // parcel 객체를 받았을 때 직렬화를 풀어주는 로직.

    @Override // 앞으로 나올 override 부분은 implement parcelable 할 시 자동으로 추가되는 메소드임.
    // 부모클래스(parcel)의 함수를 서브클래스(Quiz)에서 재정의
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestion);
        dest.writeString(mAnswer);
        dest.writeInt(mType);
        dest.writeString(mAnswerChoice);
        dest.writeString(mUserAnswer);
    }
    //객체가 직렬화되어 보내지기 이전에 데이터를 parcel로 저장해주는 메소드로,
    //dest에 순차적으로 Class 내부에 있는 데이터들을 저장시켜 놓음.
    //parcel이 직렬화되어 보내질 때, 해당 변수 순서대로 이동된다(주의)

    @Override
    public int describeContents() {
        return 0;
    } // 해당 클래스가 가족클래스가 있을 시 이를 외부에서 구분하기 위함 (return값을 다르게 함) -switch, if문의 조건으로 사용됨

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() { //final - 상속 제한
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    //이 Creator 부분이 없다면 데이터를 넘기게 되더라고 Creator가 없다는 Exception오류가 남. (필히 작성해야 함)
    //안드로이드 스튜디오의 경우 자동으로 입력됨.
    //unmarshal 역할을 함 : 한 객체의 메모리에서 표현방식을 저장 또는 전송에 적합한 다른 데이터 형식으로 변환하는 과정
    //deserialize 역할을 함 : 직렬화된 파일 등을 역으로 직렬화하여 다시 객체의 형태로 만드는 것을 의미. 저장된 파일을 읽거나 전송된 스트림 데이터를 읽어 원래 객체의 형태로 복원한다.

}
