package ua.tarastom.gufi.utils;

import androidx.room.TypeConverter;

import java.util.LinkedHashMap;
import java.util.Map;

import ua.tarastom.gufi.model.Service;

public class Converters {
    @TypeConverter
    public Map<String, String> fromServicesToString(Service service) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("idService", String.valueOf(service.getIdService()));
        map.put("category", service.getCategory());
        map.put("name", service.getName());
        map.put("item", service.getItem());
        map.put("surname", service.getSurname());
        map.put("sex", service.getSex());
        map.put("numberPhone", service.getNumberPhone());
        map.put("imgProfilePicPath", service.getImgProfilePicPath());
        map.put("payment", service.getPayment());
        map.put("businessHours", service.getBusinessHours());
        map.put("aboutMe", service.getAboutMe());
        return map;
    }

    @TypeConverter
    public Service fromStringToServices(Map<String, String> map) {
        Service service = new Service();
        service.setIdService(Integer.parseInt(map.get("idService")));
        service.setCategory(map.get("category"));
        service.setName(map.get("name"));
        service.setItem(map.get("item"));
        service.setSurname(map.get("surname"));
        service.setSex(map.get("sex"));
        service.setNumberPhone(map.get("numberPhone"));
        service.setImgProfilePicPath(map.get("imgProfilePicPath"));
        service.setPayment(map.get("payment"));
        service.setBusinessHours(map.get("businessHours"));
        service.setAboutMe(map.get("aboutMe"));
        return service;
    }
}
