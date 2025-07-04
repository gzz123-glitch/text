package com.example.home;

import model.ResponseModel;
import utils.NetworkUtils;

public class UserDataManager {
    private static UserDataManager instance;

    private UserDataManager() {}

    public static synchronized UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    // 登录：桥接 NetworkUtils.LoginCallback → 业务 LoginCallback
    public void verifyLogin(String username, String password, final LoginCallback callback) {
        NetworkUtils.login(username, password, new NetworkUtils.LoginCallback() {
            @Override
            public void onResult(ResponseModel responseModel) {
                if (callback != null) callback.onLoginSuccess(responseModel);
            }

            @Override
            public void onError(String errorMsg) {
                if (callback != null) callback.onLoginFailed(errorMsg);
            }
        });
    }

    // 注册：桥接 NetworkUtils.RegisterCallback → 业务 RegisterCallback
    public void registerUser(String name, String password, String phone, final RegisterCallback callback) {
        NetworkUtils.register(phone, password, name, new NetworkUtils.RegisterCallback() {
            @Override
            public void onRegisterSuccess(ResponseModel responseModel) {
                if (callback != null) callback.onRegisterSuccess(responseModel);
            }

            @Override
            public void onRegisterFailed(String errorMsg) {
                if (callback != null) callback.onRegisterFailed(errorMsg);
            }
        });
    }

    // 业务层登录回调（对外暴露）
    public interface LoginCallback {
        void onLoginSuccess(ResponseModel responseModel);
        void onLoginFailed(String errorMsg);
    }

    // 业务层注册回调（对外暴露）
    public interface RegisterCallback {
        void onRegisterSuccess(ResponseModel responseModel);
        void onRegisterFailed(String errorMsg);
    }
}