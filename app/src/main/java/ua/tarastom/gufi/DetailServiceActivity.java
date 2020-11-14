package ua.tarastom.gufi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        String mainNameService = getIntent().getStringExtra("mainNameService");
        Toast.makeText(this, mainNameService, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailServiceActivity.this, FaqActivity.class );
        startActivity(intent);
        finish();
    }

}