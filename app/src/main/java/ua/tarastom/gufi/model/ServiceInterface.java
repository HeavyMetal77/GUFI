package ua.tarastom.gufi.model;

import android.widget.ImageView;

public interface ServiceInterface {

    String getTypeService();

    String getCategory();

    String getName();

    String getNumber();

    ImageView getImg();

    String getPayment();

    String getBusinessHours();
}
