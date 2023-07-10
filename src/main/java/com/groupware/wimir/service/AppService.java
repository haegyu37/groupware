    package com.groupware.wimir.service;

    import com.groupware.wimir.entity.App;

    public interface AppService {
        App createApp(App app);
        App updateApp(App app);
        void deleteApp(Long appId);
        App getAppById(Long appId);
    }
