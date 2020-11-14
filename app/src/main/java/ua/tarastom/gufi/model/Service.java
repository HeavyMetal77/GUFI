package ua.tarastom.gufi.model;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private String mainNameService;
    private List<ServiceItem> serviceItemArrayList;

    public Service(String mainNameService, ArrayList<ServiceItem> serviceItemArrayList) {
        this.mainNameService = mainNameService;
        this.serviceItemArrayList = serviceItemArrayList;
    }

    public String getMainNameService() {
        return mainNameService;
    }

    public void setMainNameService(String mainNameService) {
        this.mainNameService = mainNameService;
    }

    public List<ServiceItem> getServiceItemArrayList() {
        return serviceItemArrayList;
    }

    public void setServiceItemArrayList(ArrayList<ServiceItem> serviceItemArrayList) {
        this.serviceItemArrayList = serviceItemArrayList;
    }

    public void addServiceItem(ServiceItem serviceItem) {
        serviceItemArrayList.add(serviceItem);
    }
}
