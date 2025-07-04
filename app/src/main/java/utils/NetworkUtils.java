package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import model.ResponseModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private static final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static final String REGISTER_URL = "http://10.34.44.64:5190/register";
    public static final String LOGIN_URL = "http://10.34.44.64:5190/user";

    // 登录请求（回调：onResult 成功 / onError 失败）
    public static void login(String phone, String password, final LoginCallback callback) {
        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("pho", phone);
            jsonBody.addProperty("pwd", password);
            jsonBody.addProperty("action", "login");
            String json = gson.toJson(jsonBody);

            RequestBody requestBody = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(LOGIN_URL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) callback.onError("网络失败：" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseModel model = new ResponseModel();
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            model = gson.fromJson(response.body().string(), ResponseModel.class);
                        } else {
                            model.setMessage("状态码：" + response.code());
                        }
                    } catch (JsonSyntaxException e) {
                        model.setMessage("解析失败");
                    }
                    if (callback != null) {
                        if (model.isSuccess()) callback.onResult(model);
                        else callback.onError(model.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) callback.onError("请求异常：" + e.getMessage());
        }
    }

    // 注册请求（回调：onRegisterSuccess 成功 / onRegisterFailed 失败）
    public static void register(String phone, String password, String name, final RegisterCallback callback) {
        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("pho", phone);
            jsonBody.addProperty("pwd", password);
            jsonBody.addProperty("name", name);
            jsonBody.addProperty("action", "register");
            String json = gson.toJson(jsonBody);

            RequestBody requestBody = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url(REGISTER_URL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) callback.onRegisterFailed("网络失败：" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseModel responseModel = new ResponseModel();
                    responseModel.setSuccess(false);

                    try {
                        String responseData = "";
                        if (response.body() != null) { // 读取响应体（无论状态码）
                            responseData = response.body().string();
                        }
                        // 优先解析后端返回的 JSON（包含 msg）
                        responseModel = gson.fromJson(responseData, ResponseModel.class);

                        // 若后端未返回 msg，补充状态码提示
                        if (responseModel.getMessage() == null || responseModel.getMessage().trim().isEmpty()) {
                            responseModel.setMessage("请求失败，状态码：" + response.code());
                        }
                    } catch (JsonSyntaxException e) { // JSON 解析失败
                        responseModel.setSuccess(false);
                        responseModel.setMessage("数据格式错误（状态码：" + response.code() + "）");
                    } catch (IOException e) { // 读取响应失败
                        responseModel.setSuccess(false);
                        responseModel.setMessage("网络响应异常：" + e.getMessage());
                    } finally {
                        if (callback != null) {
                            if (responseModel.isSuccess()) {
                                callback.onRegisterSuccess(responseModel);
                            } else {
                                callback.onRegisterFailed(responseModel.getMessage()); // 直接传后端 msg
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) callback.onRegisterFailed("请求异常：" + e.getMessage());
        }
    }

    // 登录回调（原始）
    public interface LoginCallback {
        void onResult(ResponseModel responseModel);
        void onError(String errorMsg);
    }

    // 注册回调（与业务层对齐）
    public interface RegisterCallback {
        void onRegisterSuccess(ResponseModel responseModel);
        void onRegisterFailed(String errorMsg);
    }
}