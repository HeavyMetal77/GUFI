package ua.tarastom.gufi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ua.tarastom.gufi.model.Service;


public class ProfileActivity extends AppCompatActivity {
    private String nameServiceItem;
    private String surnameServiceItem;
    private String mainNameService;
    private final String nameCollection = "services2";
    private Service service;

    private TextView textViewNumberPhoneProfile;
    private TextView textViewNameProfile;
    private TextView textViewSexProfile;
    private TextView textViewDescriptionServicePortfolio;
    private ImageView imageViewIconProfile;
    private String idServices;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewNumberPhoneProfile = findViewById(R.id.textViewNumberPhoneProfile);
        textViewNameProfile = findViewById(R.id.textViewNameProfile);
        textViewSexProfile = findViewById(R.id.textViewSexProfile);
        textViewDescriptionServicePortfolio = findViewById(R.id.textViewDescriptionServicePortfolio);
        imageViewIconProfile = findViewById(R.id.imageViewIconProfile);

        Intent intent = getIntent();
        nameServiceItem = intent.getStringExtra("nameServiceItem");
        surnameServiceItem = intent.getStringExtra("surnameServiceItem");
        mainNameService = intent.getStringExtra("mainNameService");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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
        List<String> listImgProfilePicPath = service.getImgProfilePicPath();
        if (!listImgProfilePicPath.isEmpty()) {
            downloadImg(listImgProfilePicPath);
        }
    }

    private void downloadImg(List<String> listImgProfilePicPath) {
        StorageReference islandRef = storageReference.child(listImgProfilePicPath.get(0));
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File finalLocalFile = localFile;
        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                String filePath = finalLocalFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                imageViewIconProfile.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(exception -> {
            // Handle any errors
        });
    }

    public void editProfile(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        intent.putExtra("idServices", idServices);
        startActivity(intent);
    }

    public void backToDetailService() {
        Intent intent = new Intent(ProfileActivity.this, DetailServiceActivity.class);
        intent.putExtra("mainNameService", mainNameService);
        intent.putExtra("item", service.getItem());
        startActivity(intent);
    }
    public void backToDetailService(View view) {
        backToDetailService();
    }

    @Override
    public void onBackPressed() {
        backToDetailService();
    }
}