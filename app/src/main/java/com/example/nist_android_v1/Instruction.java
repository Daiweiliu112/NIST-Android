package com.example.nist_android_v1;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Instruction extends AppCompatActivity {
    private Button next_button;
    private TextView time_ans;
    private TextView check_ans1;
    private TextView check_ans2;
    String time;
    String ans1;
    String ans2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        next_button = (Button) findViewById(R.id.set_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_screen();
            }
        });

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
        if (extras != null) {
            time = extras.getString("time");
            ans1 = extras.getString("ans1");
            ans2 = extras.getString("ans2");
        }
        if (time == null) {
            time = "N/A";
        }
        if (ans1 == null ) {
            ans1 = "N/A";
        }
        if (ans2 == null) {
            ans2 = "N/A";
        }


    }

    public void next_screen() {
        Intent intent = new Intent(this, Test.class);
        intent.putExtra("ans1", ans1); /*this gives time var to another window*/
        intent.putExtra("ans2", ans2);
        intent.putExtra("time", time);
        startActivity(intent);
    }
}
