package ua.tarastom.gufi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ua.tarastom.gufi.model.Service;

public class EditProfileActivity extends AppCompatActivity {
    private final String nameCollection = "services2";
    private String idServices;
    private EditText editTextEditName;
    private EditText editTextEditSurname;
    private EditText editTextEditNumberPhone;
    private EditText editTextEditSex;
    private EditText editTextAboutMe;
    private Service service;
    private FirebaseFirestore db;
    private boolean isStudio;
    private Boolean createNewService;
    private String mainNameService;
    private ImageView imageViewLoadPortfolio_1;
    private ImageView imageViewLoadPortfolio_2;
    private ImageView imageViewLoadPortfolio_3;
    private ImageView imageViewLoadPortfolio_4;
    private ImageView imageViewLoadPortfolio_5;
    private ImageView imageViewLoadPortfolio_6;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private int numberBucket;
    private ImageView imageViewIconProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        if (intent.hasExtra("idServices")) {
            idServices = intent.getStringExtra("idServices");
        }
        if (intent.hasExtra("createNewService") && intent.hasExtra("mainNameService")) {
            createNewService = intent.getBooleanExtra("createNewService", false);
            mainNameService = intent.getStringExtra("mainNameService");
        }
        imageViewLoadPortfolio_1 = findViewById(R.id.imageViewLoadPortfolio_1);
        imageViewLoadPortfolio_2 = findViewById(R.id.imageViewLoadPortfolio_2);
        imageViewLoadPortfolio_3 = findViewById(R.id.imageViewLoadPortfolio_3);
        imageViewLoadPortfolio_4 = findViewById(R.id.imageViewLoadPortfolio_4);
        imageViewLoadPortfolio_5 = findViewById(R.id.imageViewLoadPortfolio_5);
        imageViewLoadPortfolio_6 = findViewById(R.id.imageViewLoadPortfolio_6);
        imageViewLoadPortfolio_1.setOnClickListener(view -> chooseImage(view, 1));
        imageViewLoadPortfolio_2.setOnClickListener(view -> chooseImage(view, 2));
        imageViewLoadPortfolio_3.setOnClickListener(view -> chooseImage(view, 3));
        imageViewLoadPortfolio_4.setOnClickListener(view -> chooseImage(view, 4));
        imageViewLoadPortfolio_5.setOnClickListener(view -> chooseImage(view, 5));
        imageViewLoadPortfolio_6.setOnClickListener(view -> chooseImage(view, 6));
        List<ImageView> portfolio = new ArrayList<>();
        portfolio.add(imageViewLoadPortfolio_1);
        portfolio.add(imageViewLoadPortfolio_2);
        portfolio.add(imageViewLoadPortfolio_3);
        portfolio.add(imageViewLoadPortfolio_4);
        portfolio.add(imageViewLoadPortfolio_5);
        portfolio.add(imageViewLoadPortfolio_6);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        if (createNewService == null) {
            db.collection(nameCollection).document(idServices)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            service = task.getResult().toObject(Service.class);
                            setFieldsProfile(service, portfolio);
                        }
                    });
        } else {
            String item;
            if (!createNewService) {
                item = "Мастер";
            } else {
                item = "Студия";
            }
            service = new Service(mainNameService, "", item,
                    "", "", "", new ArrayList<>(),
                    "", "", "");
            setFieldsProfile(service, portfolio);
        }
        imageViewIconProfile = findViewById(R.id.imageViewIconProfile);
    }

    private void chooseImage(View view, int number) {
        numberBucket = number;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выбрать фотографию"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        service.getImgProfilePicPath().add(filePath.toString());
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                setImg(bitmap, numberBucket);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setImg(Bitmap bitmap, int numberBucket) {
        switch (numberBucket) {
            case 1:
                imageViewLoadPortfolio_1.setImageBitmap(bitmap);
                break;
            case 2:
                imageViewLoadPortfolio_2.setImageBitmap(bitmap);
                break;
            case 3:
                imageViewLoadPortfolio_3.setImageBitmap(bitmap);
                break;
            case 4:
                imageViewLoadPortfolio_4.setImageBitmap(bitmap);
                break;
            case 5:
                imageViewLoadPortfolio_5.setImageBitmap(bitmap);
                break;
            case 6:
                imageViewLoadPortfolio_6.setImageBitmap(bitmap);
                break;
            default:
                break;
        }
    }

    private void setFieldsProfile(Service serviceItem, List<ImageView> imgPortfolio) {
        editTextEditName = findViewById(R.id.editTextEditName);
        editTextEditSurname = findViewById(R.id.editTextEditSurname);
        editTextEditNumberPhone = findViewById(R.id.editTextEditNumberPhone);
        editTextEditSex = findViewById(R.id.editTextEditSex);
        editTextAboutMe = findViewById(R.id.editTextAboutMe);

        editTextEditName.setText(serviceItem.getName());
        editTextEditSurname.setText(serviceItem.getSurname());
        editTextEditNumberPhone.setText(serviceItem.getNumberPhone());
        editTextEditSex.setText(serviceItem.getSex());
        editTextAboutMe.setText(serviceItem.getAboutMe());

        List<String> list = serviceItem.getImgProfilePicPath();
        if (!list.isEmpty()) {
            for (int i = 0; i < imgPortfolio.size() && i < list.size(); i++) {
                downloadImg(list.get(i), imgPortfolio.get(i));
            }
        }
    }

    private void downloadImg(String listImgProfilePicPath, ImageView porfolio) {
        StorageReference islandRef = storageReference.child(listImgProfilePicPath);
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
                porfolio.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void backToProfile() {
        Editable editName = editTextEditName.getText();
        Editable editSurname = editTextEditSurname.getText();
        Editable editNumberPhone = editTextEditNumberPhone.getText();
        Editable editSex = editTextEditSex.getText();
        Editable aboutMe = editTextAboutMe.getText();

        service.setName(editName.toString().trim());
        service.setSurname(editSurname.toString().trim());
        service.setNumberPhone(editNumberPhone.toString().trim());
        service.setSex(editSex.toString().trim());
        service.setAboutMe(aboutMe.toString());

        if (editName.toString().isEmpty() || editNumberPhone.toString().isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Профиль не создан! Заполните поля!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditProfileActivity.this, DetailServiceActivity.class);
            intent.putExtra("mainNameService", service.getCategory());
            startActivity(intent);
        } else {
            if (idServices != null) {
                db.collection(nameCollection).document(idServices)
                        .set(service)
                        .addOnSuccessListener(aVoid -> Toast.makeText(EditProfileActivity.this, "Профиль успешно обновлен!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Ошибка обновления профиля!", Toast.LENGTH_SHORT).show());
            } else {
                db.collection("services").add(service)
                        .addOnSuccessListener(aVoid -> Toast.makeText(EditProfileActivity.this, "Профиль успешно создан!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Ошибка создания профиля!", Toast.LENGTH_SHORT).show());
            }
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            intent.putExtra("nameServiceItem", editName.toString());
            intent.putExtra("surnameServiceItem", editSurname.toString());
            intent.putExtra("mainNameService", service.getCategory());
            startActivity(intent);
        }
    }

    public void backToProfile(View view) {
        backToProfile();
    }

    @Override
    public void onBackPressed() {
        backToProfile();
    }
}