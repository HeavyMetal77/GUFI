package ua.tarastom.gufi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class ProfileActivity extends AppCompatActivity {
    private String nameServiceItem;
    private String mainNameService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        nameServiceItem = intent.getStringExtra("nameServiceItem");
        mainNameService = intent.getStringExtra("mainNameService");
    }

    public void editProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void backToDetailService(View view) {
        Intent intent = new Intent(ProfileActivity.this, DetailServiceActivity.class);
        intent.putExtra("mainNameService", mainNameService);
        startActivity(intent);
    }
}