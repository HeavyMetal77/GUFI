package ua.tarastom.gufi;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ua.tarastom.gufi.model.Category;
import ua.tarastom.gufi.utils.FavoriteAdapter;
import ua.tarastom.gufi.utils.ServiceAdapter;

public class ServiceCatalogActivity extends AppCompatActivity {
    private final String nameCollectionCategory = "category";
    private RecyclerView recyclerview_all_services;
    private RecyclerView recyclerview_studios;
    private RecyclerView recyclerview_all_masters;
    private RecyclerView recyclerview_favorites;
    private ScrollView scrollview_catalog;
    private ProgressBar progressBar_catalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_catalog);

        recyclerview_favorites = findViewById(R.id.recyclerview_favorites);
        recyclerview_all_masters = findViewById(R.id.recyclerview_all_masters);
        recyclerview_studios = findViewById(R.id.recyclerview_studios);
        recyclerview_all_services = findViewById(R.id.recyclerview_all_services);
        scrollview_catalog = findViewById(R.id.scrollview_catalog);
        progressBar_catalog = findViewById(R.id.progressBar_catalog);
        scrollview_catalog.setVisibility(View.INVISIBLE);
        progressBar_catalog.setVisibility(View.VISIBLE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(nameCollectionCategory).addSnapshotListener((value, error) -> {
            List<Category> categoryList = new ArrayList<>();
            if (value != null) {
                List<DocumentSnapshot> documents = value.getDocuments();
                for (String s : Objects.requireNonNull(documents.get(0).getData()).keySet()) {
                    categoryList.add(new Category(s));
                }
            }
            loadDataToRecyclerView(categoryList);
            scrollview_catalog.setVisibility(View.VISIBLE);
            progressBar_catalog.setVisibility(View.INVISIBLE);
        });

        BottomNavigationView bottomNavigationView = new BottomNavigationView(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.menu_page_1:
//                    break;
//                case R.id.menu_page_2:
//                    break;
//                case R.id.menu_page_3:
//                    break;
//                case R.id.menu_page_4:
//                    break;
//            }
            return false;
        });
    }

    private void loadDataToRecyclerView(List<Category> categories) {
        recyclerview_favorites.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(false);
        favoriteAdapter.setServiceItemsFavorites(categories);
        favoriteAdapter.setScreenWidth(getScreenWidth());
        recyclerview_favorites.setAdapter(favoriteAdapter);

        recyclerview_all_masters.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
        FavoriteAdapter serviceAdapter_all_masters = new FavoriteAdapter(true);
        serviceAdapter_all_masters.setServiceItemsFavorites(categories);
        serviceAdapter_all_masters.setScreenWidth(getScreenWidth());
        recyclerview_all_masters.setAdapter(serviceAdapter_all_masters);

        recyclerview_studios.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
        FavoriteAdapter serviceAdapter_studios = new FavoriteAdapter(true);
        serviceAdapter_studios.setServiceItemsFavorites(categories);
        serviceAdapter_studios.setScreenWidth(getScreenWidth());
        recyclerview_studios.setAdapter(serviceAdapter_studios);

        recyclerview_all_services.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        ServiceAdapter serviceAdapter3 = new ServiceAdapter();
        serviceAdapter3.setServiceItems(categories);
        serviceAdapter3.setScreenWidth(getScreenWidth());
        recyclerview_all_services.setAdapter(serviceAdapter3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ServiceCatalogActivity.this, FaqActivity.class);
        startActivity(intent);
        finish();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}