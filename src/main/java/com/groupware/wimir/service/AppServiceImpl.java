//package com.groupware.wimir.service;
//
//import com.groupware.wimir.entity.App;
//import com.groupware.wimir.repository.AppRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AppServiceImpl implements AppService {
//
//    private final AppRepository appRepository;
//
//    @Autowired
//    public AppServiceImpl(AppRepository appRepository) {
//        this.appRepository = appRepository;
//    }
//
//    @Override
//    public App createApp(App app) {
//        return appRepository.save(app);
//    }
//
//    @Override
//    public App updateApp(App app) {
//        return appRepository.save(app);
//    }
//
//    @Override
//    public void deleteApp(Long appId) {
//        appRepository.deleteById(appId);
//    }
//
//    @Override
//    public App getAppById(Long appId) {
//        return appRepository.findById(appId).orElse(null);
//    }
//}
