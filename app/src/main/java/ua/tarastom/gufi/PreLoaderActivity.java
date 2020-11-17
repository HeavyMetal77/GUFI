package ua.tarastom.gufi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PreLoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    nextActivity();
                }
            }
        };
        timer.start();
    }

    public void nextActivity() {
        Intent intent = new Intent(this, FaqActivity.class);
        startActivity(intent);
    }

    protected void onPause(){
        super.onPause();
        finish(); 
    }

}