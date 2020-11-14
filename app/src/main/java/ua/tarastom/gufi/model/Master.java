package ua.tarastom.gufi.model;

import android.widget.ImageView;

public class Master extends ServiceItem implements ServiceInterface{


    public Master(String category, String nameServiceItem, String numberPhone, ImageView imgProfilePicPath, String payment, String businessHours) {
        super(category, nameServiceItem, numberPhone, imgProfilePicPath, payment, businessHours);
    }

    @Override
    public String getTypeService() {
        return "Мастер";
    }

    @Override
    public String getName() {
        return super.getNameServiceItem();
    }

    @Override
    public String getNumber() {
        return super.getNumberPhone();
    }

    @Override
    public ImageView getImg() {
        return super.getImgProfilePic();
    }

    public String getPayment() {
        return super.getPayment();
    }

    public String getBusinessHours() {
        return super.getBusinessHours();
    }

}
