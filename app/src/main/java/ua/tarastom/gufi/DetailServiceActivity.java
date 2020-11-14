package ua.tarastom.gufi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailServiceActivity extends AppCompatActivity {
    private TextView textViewHeaderSubCatalog;
    private ImageView imageViewArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        String mainNameService = getIntent().getStringExtra("mainNameService");
        textViewHeaderSubCatalog = findViewById(R.id.textViewHeaderSubCatalog);
        textViewHeaderSubCatalog.setText(mainNameService);
        imageViewArrow = findViewById(R.id.imageViewArrow);
        imageViewArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailServiceActivity.this, ServiceCatalogActivity.class );
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailServiceActivity.this, ServiceCatalogActivity.class );
        startActivity(intent);
        finish();
    }

}