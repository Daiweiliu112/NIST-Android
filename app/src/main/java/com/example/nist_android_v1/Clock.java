package com.example.nist_android_v1;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Clock extends AppCompatActivity {
    private Calendar calendar;
    private TimePicker timePicker1;
    private Button set_button;
    private String format = "";
    private String format0 = "";
    private String time = "";
    private StringBuilder temp_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        set_button = (Button) findViewById(R.id.set_button);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        final ImageView back_1 = (ImageView)findViewById(R.id.back_1);
        final ImageView back_2 = (ImageView)findViewById(R.id.back_2);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = back_1.getWidth();
                final float translationX = width * progress;
                back_1.setTranslationX(translationX);
                back_2.setTranslationX(translationX - width);
            }
        });
        animator.start();

    }

    public void setTime(View view) { /*Is this detecting buttons? is the previous onClickListener blocking it?*/
        int hour = timePicker1.getHour();
        int min  = timePicker1.getMinute();
        showTime(hour,min);
        nextScreen();
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        if (min<10) {
            format0 = "0";
        }
        temp_string =(new StringBuilder().append(hour).append(":").append(format0).append(min).
                append(" ").append(format));
        time = temp_string.toString();
        /*time_button.setText(time + "" )*/;/*toString takes an array and changes it to a string*/
        /*What is temp_string? Arrays.toString() is supposed to be a method for Arrays*/

    }
    public void nextScreen(){
        /*Intent is needed for screen transitions*/
        Intent intent = new Intent(this, Question.class);
        intent.putExtra("time", time); /*this gives time var to another window*/
        startActivity(intent);
    }




}
