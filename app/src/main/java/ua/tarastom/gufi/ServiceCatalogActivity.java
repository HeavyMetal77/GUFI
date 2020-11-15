package ua.tarastom.gufi;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ua.tarastom.gufi.model.Category;
import ua.tarastom.gufi.model.Service;
import ua.tarastom.gufi.utils.FavoriteAdapter;
import ua.tarastom.gufi.utils.ServiceAdapter;

public class ServiceCatalogActivity extends AppCompatActivity {
    private final String nameCollection = "services";
    private RecyclerView recyclerview_all_services;
    private RecyclerView recyclerview_studios;
    private RecyclerView recyclerview_all_masters;
    private RecyclerView recyclerview_favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_catalog);

        recyclerview_favorites = findViewById(R.id.recyclerview_favorites);
        recyclerview_all_masters = findViewById(R.id.recyclerview_all_masters);
        recyclerview_studios = findViewById(R.id.recyclerview_studios);
        recyclerview_all_services = findViewById(R.id.recyclerview_all_services);

        Thread thread = new Thread(new GetDataFromCloudDB());
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BottomNavigationView bottomNavigationView = new BottomNavigationView(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_page_1:
                    break;
                case R.id.menu_page_2:
                    break;
                case R.id.menu_page_3:
                    break;
                case R.id.menu_page_4:
                    break;
            }
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

    private class GetDataFromCloudDB implements Runnable {
        @Override
        public void run() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(nameCollection).addSnapshotListener((value, error) -> {
                List<Service> services = new ArrayList<>();
                Set<Category> categories = new LinkedHashSet<>();
                if (value != null) {
                    services = value.toObjects(Service.class);
                }
                for (Service service : services) {
                    categories.add(new Category(service.getCategory()));
                }
                List<Category> categoryList = new ArrayList<>(categories);
                loadDataToRecyclerView(categoryList);
            });
        }
    }
}