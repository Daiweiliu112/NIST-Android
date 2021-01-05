package com.example.nist_android_v1;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Question extends AppCompatActivity {
    private Button next_button;
    private StringBuilder temp_string1;
    private StringBuilder temp_string2;
    private String format = "";
    private String format0 = "";
    private String ans1;
    private String ans2;
    private String time;
    CheckBox check1;
    CheckBox check2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        next_button = (Button) findViewById(R.id.set_button);
        check1 = (CheckBox) findViewById(R.id.checkBox);
        check2 = (CheckBox) findViewById(R.id.checkBox2);
        final ImageView back_1 = (ImageView) findViewById(R.id.back_1);
        final ImageView back_2 = (ImageView) findViewById(R.id.back_2);
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

        Bundle extras = getIntent().getExtras();
        time = extras.getString("time");

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_Screen();
            }
        });

    }
    public void next_Screen(){
        temp_string1 =(new StringBuilder().append(String.valueOf(check1.isChecked())));
        temp_string2 = (new StringBuilder().append(String.valueOf(check2.isChecked())));
        ans1 = temp_string1.toString();
        ans2 = temp_string2.toString();
        Intent intent = new Intent(this, Summary.class);
        intent.putExtra("ans1", ans1); /*this gives time var to another window*/
        intent.putExtra("ans2", ans2);
        intent.putExtra("time", time);
        startActivity(intent);
    }


}
