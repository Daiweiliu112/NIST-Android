package com.example.nist_android_v1;


import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import pub.devrel.easypermissions.EasyPermissions;

public class Submit extends AppCompatActivity {
    String wake_time;
    private int test_seconds;
    String ans1;
    String ans2;
    private TextView test_result;
    private Button retest;
    private Button submit;
    private static final String TAG = "Submit";


    @Override
    public void onBackPressed() {
        //not calling super.OnBackPressed here disables the back button
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);



        retest = (Button) findViewById(R.id.set_button);
        retest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            wake_time = extras.getString("time");
            ans1 = extras.getString("ans1");
            ans2 = extras.getString("ans2");
        }
        if (wake_time == null) {
            wake_time = "N/A";
        }
        if (ans1 == null) {
            ans1 = "N/A";
        }
        if (ans2 == null) {
            ans2 = "N/A";
        }


        test_seconds = extras.getInt("seconds_elapsed");
        test_result = (TextView) findViewById(R.id.test_result);
        test_result.setText("Tested for " + test_seconds + " seconds");


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

    }

    public void restart() {
        Intent intent = new Intent(this, Instruction.class);
        startActivity(intent);
    }







    // prev error was a typo
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void writeFileExternalStorage(View v) {
        String textToWrite = String.format("Wake Time, ans1, ans2\n %s,%s,%s",
                wake_time, ans1, ans2, test_seconds);


        //Request permissions with the below website:
        //https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/

        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(this, "NOT saved", Toast.LENGTH_LONG).show();

            //If it isn't mounted - we can't write into it.
            return;
        }

        //Create a new file that points to the root directory, with the given name:
        File file = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Test.csv");


        }

        //This point and below is responsible for the write operation
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            //second argument of FileOutputStream constructor indicates whether
            //to append or create new file if one exists
            outputStream = new FileOutputStream(file, false); //add append: true into FileOutputStream if needed
            outputStream.write(textToWrite.getBytes());
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/*


    private void checkExternalMedia() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalSotrageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // read and write allowed
            mExternalStorageAvailable = mExternalSotrageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // only read is allowed
            mExternalStorageAvailable = true;
            mExternalSotrageWriteable = false;
        } else {
            // read and write not allowed
            mExternalStorageAvailable = mExternalSotrageWriteable = false;
        }

    }
    private void writeToSDFile() {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir,"test_data.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("Hi");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read_Raw() {
        InputStream is = this.getResources().openRawResource(R.raw.textfile);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);

        try {
            String test;
            while (true) {
                test = br.readLine();
                if (test == null) break;
            }
            isr.close();
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

}

