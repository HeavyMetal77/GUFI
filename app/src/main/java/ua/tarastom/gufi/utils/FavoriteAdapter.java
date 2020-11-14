package ua.tarastom.gufi.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.tarastom.gufi.DetailServiceActivity;
import ua.tarastom.gufi.R;
import ua.tarastom.gufi.model.Service;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ServiceViewHolder> {

    private List<Service> serviceItemsFavorites;
    private int screenWidth;
    private boolean changeIndent; //флаг для изменения отступов

    public FavoriteAdapter(boolean changeIndent) {
        this.changeIndent = changeIndent;
        serviceItemsFavorites = new ArrayList<>();
    }

    public void setServiceItemsFavorites(List<Service> serviceItemsFavorites) {
        this.serviceItemsFavorites = serviceItemsFavorites;
        notifyDataSetChanged();
    }

    public List<Service> getServiceItemsFavorites() {
        return serviceItemsFavorites;
    }

    private void addServiceItems(ArrayList<Service> serviceItems) {
        this.serviceItemsFavorites.addAll(serviceItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceItemsFavorites.get(position);
        holder.button_favorite.setText(service.getMainNameService());
        holder.button_favorite.setWidth(screenWidth / 3 - 80);
        holder.button_favorite.setHeight(screenWidth / 3 - 80);

        holder.button_favorite.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailServiceActivity.class);
            intent.putExtra("mainNameService", service.getMainNameService());
            holder.itemView.getContext().startActivity(intent);
        });

        holder.imageViewIconHeart.setOnClickListener(view -> {
            Integer integer = (Integer) holder.imageViewIconHeart.getTag();
            integer = integer == null ? 0 : integer;
            switch(integer) {
                case R.drawable.heart_icon_1:
                    holder.imageViewIconHeart.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.heart_icon_2));
                    holder.imageViewIconHeart.setTag(R.drawable.heart_icon_2);
                    break;
                case R.drawable.heart_icon_2:
                default:
                    holder.imageViewIconHeart.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.heart_icon_1));
                    holder.imageViewIconHeart.setTag(R.drawable.heart_icon_1);
                    break;
            }
        });

        //замена нижнего и верхнего отступа
        ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) holder.button_favorite.getLayoutParams();
        if (changeIndent && position % 2 == 1) {
            newLayoutParams.topMargin = 0;
            newLayoutParams.bottomMargin = 0;
            holder.layout_item_service.setLayoutParams(newLayoutParams);
        } else {
            newLayoutParams.topMargin = 20;
            newLayoutParams.bottomMargin = 20;
            holder.layout_item_service.setLayoutParams(newLayoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return serviceItemsFavorites.size();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        private final Button button_favorite;
        private final ConstraintLayout layout_item_service;
        private final ImageView imageViewIconHeart;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            button_favorite = itemView.findViewById(R.id.button_favorite);
            layout_item_service = itemView.findViewById(R.id.layout_item_favorite);
            imageViewIconHeart = itemView.findViewById(R.id.imageViewIconHeart);
        }
    }
}
