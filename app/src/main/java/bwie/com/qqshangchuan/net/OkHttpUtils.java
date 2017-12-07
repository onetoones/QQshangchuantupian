package bwie.com.qqshangchuan.net;

import java.io.File;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by 不将就 on 2017/12/7.
 */

public class OkHttpUtils {
    private static OkHttpUtils okHttpUtils;
    private final OkHttpClient client;

    private OkHttpUtils() {
        HttpLoggingInterceptor loog = new HttpLoggingInterceptor();
        loog.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient
                .Builder()
                .addInterceptor(loog)
                .build();
    }

    public static OkHttpUtils getOkHttpUtils() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {

                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    /**
     * GET请求
     */
    public void doGet(String url, Callback callback) {

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);

    }

    /**
     * post
     */
    public void doPost(String url, Map<String, String> params, Callback callback) {

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());


        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();

        client.newCall(request).enqueue(callback);
    }

/*
    public void uploadFile(String url, Map<String, Object> params, Callback callback) {
        if (params == null) {
            throw new RuntimeException("参数为空了");
        }
//1.new 一个MultipartBody.Builder
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (Object key : params.keySet()) {

            if (params.get(key) instanceof String) {

                builder.addFormDataPart(key.toString(), params.get(key).toString());
            } else if (params.get(key) instanceof File) {
                File file = (File) params.get(key);
                builder.addFormDataPart(key.toString(), file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),file);
            }

        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(callback);
    }
*/

public void upload(String url,Map<String,Object> params,Callback callback){

    MultipartBody.Builder builder = new MultipartBody.Builder();
builder.setType(MultipartBody.FORM);

    for(String key : params.keySet()){
        Object obj = params.get(key);
        if (obj instanceof String){
            builder.addFormDataPart(key,obj.toString());
        }else if (obj instanceof File){
            File file = (File) obj;
            builder.addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("image/jpeg"),file));

        }
    }
    MultipartBody build = builder.build();
    Request request = new Request.Builder().url(url).post(build).build();

    client.newCall(request).enqueue(callback);

}

}
