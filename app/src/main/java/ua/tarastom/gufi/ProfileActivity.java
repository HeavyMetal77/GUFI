package ua.tarastom.gufi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import ua.tarastom.gufi.model.Service;


public class ProfileActivity extends AppCompatActivity {
    private String nameServiceItem;
    private String surnameServiceItem;
    private String mainNameService;
    private final String nameCollection = "services";
    private Service service;

    private TextView textViewNumberPhoneProfile;
    private TextView textViewNameProfile;
    private TextView textViewSexProfile;
    private TextView textViewDescriptionServicePortfolio;
    private String idServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewNumberPhoneProfile = findViewById(R.id.textViewNumberPhoneProfile);
        textViewNameProfile = findViewById(R.id.textViewNameProfile);
        textViewSexProfile = findViewById(R.id.textViewSexProfile);
        textViewDescriptionServicePortfolio = findViewById(R.id.textViewDescriptionServicePortfolio);

        Intent intent = getIntent();
        nameServiceItem = intent.getStringExtra("nameServiceItem");
        surnameServiceItem = intent.getStringExtra("surnameServiceItem");
        mainNameService = intent.getStringExtra("mainNameService");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(nameCollection)
                .whereEqualTo("name", nameServiceItem)
                .whereEqualTo("surname", surnameServiceItem)
                .whereEqualTo("category", mainNameService)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            service = document.toObject(Service.class);
                            idServices = document.getId();
                            if (service != null) {
                                setFieldsProfile();
                            }
                        }
                    }
                });
    }

    private void setFieldsProfile() {
        textViewNumberPhoneProfile.setText(service.getNumberPhone());
        String name = "";
        if (service.getItem().equals("Мастер")) {
            name = service.getName() + " " + service.getSurname();
        }
        if (service.getItem().equals("Студия")) {
            name = service.getName();
        }
        textViewNameProfile.setText(name);
        textViewSexProfile.setText(service.getSex());
        textViewDescriptionServicePortfolio.setText(service.getAboutMe());
    }

    public void editProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        intent.putExtra("idServices", idServices);
        startActivity(intent);
    }

    public void backToDetailService(View view) {
        Intent intent = new Intent(ProfileActivity.this, DetailServiceActivity.class);
        intent.putExtra("mainNameService", mainNameService);
        intent.putExtra("item", service.getItem());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, DetailServiceActivity.class);
        startActivity(intent);
        finish();
    }
}