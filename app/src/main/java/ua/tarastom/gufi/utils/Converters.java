package ua.tarastom.gufi.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import ua.tarastom.gufi.model.Service;

public class Converters {
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        map.put("imgProfilePicPath", fromArrayList(service.getImgProfilePicPath()));
        map.put("payment", service.getPayment());
        map.put("businessHours", service.getBusinessHours());
        map.put("aboutMe", service.getAboutMe());
        return map;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public Service fromStringToServices(Map<String, String> map) {
        Service service = new Service();
        service.setIdService(Integer.parseInt(Objects.requireNonNull(map.get("idService"))));
        service.setCategory(map.get("category"));
        service.setName(map.get("name"));
        service.setItem(map.get("item"));
        service.setSurname(map.get("surname"));
        service.setSex(map.get("sex"));
        service.setNumberPhone(map.get("numberPhone"));
        service.setImgProfilePicPath(fromString(map.get("imgProfilePicPath")));
        service.setPayment(map.get("payment"));
        service.setBusinessHours(map.get("businessHours"));
        service.setAboutMe(map.get("aboutMe"));
        return service;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public String fromArrayList(List<String> data) {
        return data.stream().reduce((a, e) -> a + "-" + e).orElse(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public List<String> fromString(String data) {
        if (data != null && data.length() > 0) {
            return Arrays.stream(data.split("-")).filter(e -> !e.equals("")).collect(Collectors.toList());
        } else {
            return null;
        }
    }


}
