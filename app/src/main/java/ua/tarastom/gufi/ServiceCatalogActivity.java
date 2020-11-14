package ua.tarastom.gufi;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.model.Service;
import ua.tarastom.gufi.model.ServiceItem;
import ua.tarastom.gufi.utils.FavoriteAdapter;
import ua.tarastom.gufi.utils.ServiceAdapter;

public class ServiceCatalogActivity extends AppCompatActivity {
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

        recyclerview_favorites.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(false);
        favoriteAdapter.setServiceItemsFavorites(getListService());
        favoriteAdapter.setScreenWidth(getScreenWidth());
        recyclerview_favorites.setAdapter(favoriteAdapter);

        recyclerview_all_masters.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
        FavoriteAdapter serviceAdapter_all_masters = new FavoriteAdapter(true);
        serviceAdapter_all_masters.setServiceItemsFavorites(getListService());
        serviceAdapter_all_masters.setScreenWidth(getScreenWidth());
        recyclerview_all_masters.setAdapter(serviceAdapter_all_masters);

        recyclerview_studios.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
        FavoriteAdapter serviceAdapter_studios = new FavoriteAdapter(true);
        serviceAdapter_studios.setServiceItemsFavorites(getListService());
        serviceAdapter_studios.setScreenWidth(getScreenWidth());
        recyclerview_studios.setAdapter(serviceAdapter_studios);

        recyclerview_all_services.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        ServiceAdapter serviceAdapter3 = new ServiceAdapter();
        serviceAdapter3.setServiceItems(getListService());
        serviceAdapter3.setScreenWidth(getScreenWidth());
        recyclerview_all_services.setAdapter(serviceAdapter3);

    }

    private List<Service> getListService() {
        List<Service> serviceItems = new ArrayList<>();
        ArrayList<ServiceItem> serviceItemArrayList = new ArrayList<>();
        serviceItems.add(new Service("Макияж", serviceItemArrayList));
        serviceItems.add(new Service("Маникюр, педикюр", serviceItemArrayList));
        serviceItems.add(new Service("Парикмахерские услуги", serviceItemArrayList));
        serviceItems.add(new Service("Депиляция", serviceItemArrayList));
        serviceItems.add(new Service("Архитектура бровей", serviceItemArrayList));
        serviceItems.add(new Service("Маникюр с покрытием", serviceItemArrayList));
        serviceItems.add(new Service("Консультация по подбору ортопедических изделий", serviceItemArrayList));
        serviceItems.add(new Service("SPA", serviceItemArrayList));
        serviceItems.add(new Service("Услуги грузчиков", serviceItemArrayList));
        serviceItems.add(new Service("Юридические услуги", serviceItemArrayList));
        serviceItems.add(new Service("Риелторские услуги", serviceItemArrayList));
        serviceItems.add(new Service("Доставка воды", serviceItemArrayList));
        serviceItems.add(new Service("Наращивание ногтей", serviceItemArrayList));
        return serviceItems;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ServiceCatalogActivity.this, FaqActivity.class );
        startActivity(intent);
        finish();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

}