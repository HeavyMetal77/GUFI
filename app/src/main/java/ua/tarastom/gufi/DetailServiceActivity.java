package ua.tarastom.gufi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.model.Service;
import ua.tarastom.gufi.utils.DetailServiceAdapter;
import ua.tarastom.gufi.utils.SwipeToDeleteCallback;

public class DetailServiceActivity extends AppCompatActivity {
    private TextView textViewHeaderSubCatalog;
    private TextView textViewLabelStudios;
    private TextView textViewLabelMasters;
    private ImageView imageViewArrow;
    private RecyclerView recyclerview_detail_service;
    private DetailServiceAdapter adapter;
    private final String nameCollection = "services2";
    private String mainNameService;
    private String typeItem = "Мастер";
    private boolean isStudio;
    private FirebaseFirestore db;
    private String TAG;
    boolean deleted = false;
    final Service[] serviceDeleted = {null};
    private ConstraintLayout constraint_layout_detail_catalog;
    private ProgressBar progressBarDetailService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        mainNameService = getIntent().getStringExtra("mainNameService");

        constraint_layout_detail_catalog = findViewById(R.id.constraint_layout_detail_catalog);
        progressBarDetailService = findViewById(R.id.progressBarDetailService);
        constraint_layout_detail_catalog.setVisibility(View.INVISIBLE);
        progressBarDetailService.setVisibility(View.VISIBLE);

        textViewHeaderSubCatalog = findViewById(R.id.textViewHeaderSubCatalog);
        textViewLabelStudios = findViewById(R.id.textViewLabelStudios);
        textViewLabelMasters = findViewById(R.id.textViewLabelMasters);
        imageViewArrow = findViewById(R.id.imageViewArrow);
        imageViewArrow.setOnClickListener(view -> {
            Intent intent = new Intent(DetailServiceActivity.this, ServiceCatalogActivity.class);
            startActivity(intent);
            finish();
        });

//        Intent intent = getIntent();
//        if (intent.hasExtra("item")) {
//            String item = intent.getStringExtra("item");
//            if (!item.equals("Мастер")) {
//                typeItem = "Студия";
        //TODO реализовать возврат в нужную категорию
//            }
//        }
        db = FirebaseFirestore.getInstance();
        db.collection(nameCollection).addSnapshotListener((value, error) -> {
            List<Service> services = new ArrayList<>();
            if (value != null) {
                services = value.toObjects(Service.class);
            }
            changeStudioOrMaster(services, mainNameService);
            setRecyclerview_detail_service(getMastersOrStudios(services, mainNameService, typeItem), mainNameService);
            constraint_layout_detail_catalog.setVisibility(View.VISIBLE);
            progressBarDetailService.setVisibility(View.INVISIBLE);
        });
    }

    private void setOnClickStudio(List<Service> services, String mainNameService) {
        textViewLabelStudios.setOnClickListener(view -> {
            List<Service> listMasters = getMastersOrStudios(services, mainNameService, "Студия");
            setRecyclerview_detail_service(listMasters, mainNameService);
            textViewLabelStudios.setTypeface(Typeface.DEFAULT_BOLD);
            textViewLabelStudios.setTextSize(16);
            textViewLabelStudios.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black));
            textViewLabelMasters.setTypeface(Typeface.DEFAULT);
            textViewLabelMasters.setTextSize(12);
            textViewLabelMasters.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black_light));
            isStudio = true;
        });
    }

    private void setOnClickMaster(List<Service> services, String mainNameService) {
        textViewLabelMasters.setOnClickListener(view -> {
            List<Service> listStudios = getMastersOrStudios(services, mainNameService, "Мастер");
            setRecyclerview_detail_service(listStudios, mainNameService);
            textViewLabelMasters.setTypeface(Typeface.DEFAULT_BOLD);
            textViewLabelMasters.setTextSize(16);
            textViewLabelMasters.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black));
            textViewLabelStudios.setTypeface(Typeface.DEFAULT);
            textViewLabelStudios.setTextSize(12);
            textViewLabelStudios.setTextColor(ContextCompat.getColor(DetailServiceActivity.this, R.color.black_light));
            isStudio = false;
        });
    }

    private void changeStudioOrMaster(List<Service> services, String mainNameService) {
        setOnClickStudio(services, mainNameService);
        setOnClickMaster(services, mainNameService);
        textViewHeaderSubCatalog.setText(mainNameService);
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ConstraintLayout coordinatorLayout = viewHolder.itemView.findViewById(R.id.coordinatorLayout);
                final int position = viewHolder.getAdapterPosition();
                final Service item = adapter.getServiceItems().get(position);
                serviceDeleted[0] = item;
                adapter.removeItem(position);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Объект удален из списка.", Snackbar.LENGTH_LONG);
                deleted = true;
                snackbar.setAction("ОТМЕНИТЬ УДАЛЕНИЕ", view -> {
                    adapter.restoreItem(item, position);
                    recyclerview_detail_service.scrollToPosition(position);
                    deleted = false;
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                deleteFromDB(serviceDeleted[0], deleted);
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerview_detail_service);
    }

    private void deleteFromDB(Service service, boolean deleted) {
        if (service != null && deleted) {
            db.collection(nameCollection)
                    .whereEqualTo("name", service.getName())
                    .whereEqualTo("surname", service.getSurname())
                    .whereEqualTo("category", service.getCategory())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                db.collection(nameCollection).document(document.getId()).delete();
                                Log.d(TAG, "deleted");
                            }
                        }
                    });
        }
    }

    private void setRecyclerview_detail_service(List<Service> services, String mainNameService) {
        recyclerview_detail_service = findViewById(R.id.recyclerview_detail_service);
        adapter = new DetailServiceAdapter(services);
        adapter.setServiceItems(services);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerview_detail_service.setLayoutManager(layoutManager);

        adapter.setOnProfileClickListener(position -> {
            Intent intent = new Intent(DetailServiceActivity.this, ProfileActivity.class);
            Service service = adapter.getServiceItems().get(position);
            intent.putExtra("nameServiceItem", service.getName());
            intent.putExtra("surnameServiceItem", service.getSurname());
            intent.putExtra("mainNameService", mainNameService);
            startActivity(intent);
        });
        recyclerview_detail_service.setAdapter(adapter);
        enableSwipeToDeleteAndUndo();
    }

    private List<Service> getMastersOrStudios(List<Service> allServices, String mainNameService, String classTypeToReturn) {
        List<Service> resultList = new ArrayList<>();
        for (Service service : allServices) {
            if (service.getItem().equals(classTypeToReturn) && service.getCategory().equals(mainNameService)) {
                resultList.add(service);
            }
        }
        return resultList;
    }

    public void backToCatalog() {
        Intent intent = new Intent(DetailServiceActivity.this, ServiceCatalogActivity.class);
        startActivity(intent);
        finish();
    }

    public void backToCatalog(View view) {
        backToCatalog();
    }

    @Override
    public void onBackPressed() {
        backToCatalog();
    }

    public void createNewService(View view) {
        Intent intent = new Intent(DetailServiceActivity.this, EditProfileActivity.class);
        intent.putExtra("createNewService", isStudio);
        intent.putExtra("mainNameService", mainNameService);
        startActivity(intent);
        finish();
    }
}