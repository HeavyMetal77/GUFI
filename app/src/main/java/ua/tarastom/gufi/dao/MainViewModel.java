package ua.tarastom.gufi.dao;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ua.tarastom.gufi.model.Service;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Service>> allServices;

    private static ServiceDataBase serviceDataBase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        serviceDataBase = ServiceDataBase.getInstance(getApplication());
        allServices = serviceDataBase.serviceDao().getAllServices();
    }

    public LiveData<List<Service>> getAllServices() {
        return allServices;
    }

    public Service getServiceById(int id) {
        GetServiceByIdTask service = new GetServiceByIdTask(id);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return service.serviceById;
    }

    private static class GetServiceByIdTask implements Runnable {
        private Service serviceById;
        private static int id;

        public GetServiceByIdTask(int id) {
            GetServiceByIdTask.id = id;
        }

        @Override
        public void run() {
            serviceById = serviceDataBase.serviceDao().getServiceById(id);
        }
    }


    public void deleteAllServices() {
        Thread thread = new Thread(new DeleteServicesTask());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DeleteServicesTask implements Runnable {
        @Override
        public void run() {
            serviceDataBase.serviceDao().deleteAllServices();
        }
    }


    public void insertService(Service service) {
        Thread thread = new Thread(new InsertServiceTask(service));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class InsertServiceTask implements Runnable {
        private final Service service;

        public InsertServiceTask(Service service) {
            this.service = service;
        }

        @Override
        public void run() {
            if (service != null) {
                serviceDataBase.serviceDao().insertService(service);
            }
        }
    }


    public void deleteService(Service service) {
        Thread thread = new Thread(new DeleteServiceTask(service));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DeleteServiceTask implements Runnable {
        private final Service service;

        public DeleteServiceTask(Service service) {
            this.service = service;
        }

        @Override
        public void run() {
            if (service != null) {
                serviceDataBase.serviceDao().deleteService(service);
            }
        }
    }

}
