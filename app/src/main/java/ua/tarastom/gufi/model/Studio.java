package ua.tarastom.gufi.model;

import android.widget.ImageView;

public class Studio extends ServiceItem implements ServiceInterface{
    public Studio(String category, String nameServiceItem, String numberPhone, ImageView imgProfilePicPath, String payment, String businessHours) {
        super(category, nameServiceItem, numberPhone, imgProfilePicPath, payment, businessHours);
    }

    @Override
    public String getTypeService() {
        return "Студия";
    }

    @Override
    public String getName() {
        return getNameServiceItem();
    }

    @Override
    public String getNumber() {
        return getNumberPhone();
    }

    @Override
    public ImageView getImg() {
        return getImgProfilePic();
    }
}
