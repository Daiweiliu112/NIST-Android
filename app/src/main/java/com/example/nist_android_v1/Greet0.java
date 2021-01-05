package com.example.nist_android_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Greet0 extends AppCompatActivity {
    private Button start_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greet0);
        start_button = (Button)findViewById(R.id.set_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_screen();
            }
        });
    }

    public void next_screen() {
        Intent intent = new Intent(this, Clock.class);
        startActivity(intent);
    }
}