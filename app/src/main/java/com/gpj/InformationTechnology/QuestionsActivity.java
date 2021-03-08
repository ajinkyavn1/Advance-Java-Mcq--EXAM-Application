package com.gpj.InformationTechnology;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikasojha.quizbee.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import static android.view.View.OnClickListener;

public class QuestionsActivity extends AppCompatActivity {

    TextView tv,coundown;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    Vector v = new Vector ();
    Random r =new Random();
    DatabaseReference mDatabase;
    String timeLeftFormatted="00:03";
    Question question;
    LinearLayout start,questionspace;
    RadioButton rb1, rb2, rb3, rb4, selectedbtn;
    int total = 0,questnNo;
    public static int correct = 0, wrong = 0,score=0;

    // for timer
    private final long START_TIME_IN_MILLIS = 300000;  //5400000=90;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis;
    private long mEndTime;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        if (!isNetworkConnected()) {


            new AlertDialog.Builder(this)
                    .setTitle("Error!!")
                    .setIcon(R.drawable.warning)
                    .setMessage("Internet Connection is Required !!")
                    .setPositiveButton("ok",null)
                    .show();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        }


        TextView textView;
        textView = findViewById(R.id.dispName);
        String na=MainActivity2.NA;

            textView.setText("Hello "+na);
            questionspace=findViewById(R.id.main_question_layout);
            submitbutton = findViewById(R.id.button3);
            quitbutton = findViewById(R.id.buttonquit);
            tv = findViewById(R.id.tvque);
          //  coundown=findViewById(R.id.timer);

            start = findViewById(R.id.startlayout);
            radio_g = findViewById(R.id.answersgrp);
            rb1 = findViewById(R.id.radioButton);
            rb2 = findViewById(R.id.radioButton2);
            rb3 = findViewById(R.id.radioButton3);
            rb4 = findViewById(R.id.radioButton4);

            start.setVisibility(View.VISIBLE);
            questionspace.setVisibility(View.INVISIBLE);

            updateQuestions();
            quitbutton.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
                Quit();

            }
        });
        submitbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                Handler handler = new Handler();
                try {
                    selectedbtn = findViewById(radio_g.getCheckedRadioButtonId());
                    String selectedAns = selectedbtn.getText().toString();

                    if (!selectedAns.equalsIgnoreCase(question.getAnswer())) {
                        wrong++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                total++;
                                score=total;
                                checkcorrect();
                                updateQuestions();
                            }
                        }, 1);

                        }
                        else
                        {
                        correct++;

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                total++;
                                score=total;

                                updateQuestions();
                            }
                        }, 1);

                    }
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                }

            }

            private void checkcorrect() {


                if (rb1.getText().toString().equalsIgnoreCase(question.getAnswer())) {

                } else if (rb2.getText().toString().equalsIgnoreCase(question.getAnswer())) {

                } else if (rb3.getText().toString().equalsIgnoreCase(question.getAnswer())) {

                } else if (rb4.getText().toString().equalsIgnoreCase(question.getAnswer())) {
                }
            }

        });
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
               // updateButtons();
            }
        }.start();

        mTimerRunning = true;
        //updateButtons();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

         timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                startTimer();
            }
        }
    }


    private boolean isNetworkConnected () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;}
    private void updateQuestions () {
        radio_g.clearCheck();
        questnNo = generateNo();
        if(timeLeftFormatted.equals("00:01"))
        {
            Quit();
        }

        if(total>=70)
        {
            finish();
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
         if (total < 561) {


          mDatabase = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(questnNo));
          //  mDatabase = FirebaseDatabase.getInstance().getReference().child(String.valueOf(questnNo));
            ValueEventListener error = mDatabase.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    question = dataSnapshot.getValue(Question.class);

                    tv.setText(total + 1 + " ) " + question.getQuestion());
                    rb1.setText(question.getOption1());
                    rb2.setText(question.getOption2());
                    rb3.setText(question.getOption3());
                    rb4.setText(question.getOption4());
                    start.setVisibility(View.INVISIBLE);
                    questionspace.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(QuestionsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void exitDialog(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Quit();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void onBackPressed() {
        exitDialog();
    }
    public int generateNo(){
        int n = r.nextInt(561);
        v.add(questnNo);
        if (v.contains(n)){
            n = generateNo();
        }
        return  n;
    }
    public  void Quit()
    {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        finish();
        Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
        startActivity(intent);
        finish();
    }
}
