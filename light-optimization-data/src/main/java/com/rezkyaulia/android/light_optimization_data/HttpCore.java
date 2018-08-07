package com.rezkyaulia.android.light_optimization_data;

import android.support.annotation.WorkerThread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rezkyaulia.android.light_optimization_data.RequestListener.ParsedCallback;
import com.rezkyaulia.android.light_optimization_data.parser.parser.ParseUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 7/4/2017.
 */
@SuppressWarnings({"unchecked", "unused"})
public class HttpCore<T> {
    public final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient mClient;
    private String mURL ;
    private Class<?> mClass;
    private Headers mHeader;
    private String mTag;
    private StringBuilder mRequestBody;
    private List<Part> parts = new ArrayList<>();


    private Gson mGson;

    String mMethod;

    T t = null;
    IOException e = null;


    //private constructor
    public HttpCore(OkHttpClient client,Class<T> aClass,String url) {
        this.mClient = client;
        this.mClass = aClass;
        this.mURL = url;
        this.mMethod = null;
        mGson = provideGsonBuilder().create();

    }

    //private constructor
    public HttpCore(OkHttpClient client,Class<T> aClass,String url,String method) {
        this.mClient = client;
        this.mClass = aClass;
        this.mURL = url;
        this.mMethod = method;
        mGson = provideGsonBuilder().create();
    }

    GsonBuilder provideGsonBuilder(){
        return new GsonBuilder();
    }
    //private constructor
    public HttpCore(OkHttpClient client,Type type,String url,String method) {
        this.mClient = client;
        this.mClass = type.getClass();
        this.mURL = url;
        this.mMethod = method;

        GsonBuilder builder = new GsonBuilder();
        mGson = builder.create();
    }

    public HttpCore<T> setHeaders(Headers headers) {
        this.mHeader = headers;
        return this;
    }

    public HttpCore<T> setTag(String tag) {
        this.mTag = tag;
        return this;
    }

    public HttpCore<T> setMultipartFile(String key, File file) {

        if (mMethod == null) {
            this.mMethod = NetworkClient.POST ;
        }
        parts.add(new FilePart(key, file));

        return this;
    }

    public HttpCore<T> setJsonPojoBody(Object request) {
        mRequestBody = new StringBuilder();
        mGson.toJson(request,mRequestBody);
        return this;
    }

    Request.Builder provideRequestBuilder(){
        return new Request.Builder()
                .url(mURL);
    }
    private Request getRequest(){
        Request.Builder requestBuilder = provideRequestBuilder();

        if (mHeader != null)
            requestBuilder.headers(mHeader);

        if (mTag != null)
            requestBuilder.tag(mTag);

        RequestBody requestBody = RequestBody.create(JSON, mRequestBody == null ? "" : mRequestBody.toString());
        if (parts != null) {
            if (parts.size() > 0) {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                for (Part part : parts) {

//                    MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
                    MediaType mediaType = MediaType.parse("application/octet-stream");
                    if (part.getFile() == null)
                        continue;
                    RequestBody body = RequestBody.create(mediaType, part.getFile());
                    builder = builder.addFormDataPart(part.getKey(), part.getFile().getName(), body);
                }
                if (mRequestBody != null)
                    builder.addPart(requestBody);
                requestBody = builder.build();
            }
        }
        Timber.e("REQUEST BODY : "+new Gson().toJson(mRequestBody));

        if (mMethod == null){
            if (mRequestBody == null) {
                requestBuilder.get().build();
            }else {
                requestBuilder.post(requestBody).build();
            }
        }else{
            requestBuilder.method(mMethod.toUpperCase(), requestBody).build();
        }

        Timber.e("REQUEST : "+new Gson().toJson(requestBuilder));
        return requestBuilder.build();
    }


    @WorkerThread
    public T getSyncFuture() throws IOException {

        if (mURL.isEmpty()){
            throw new IOException("URL is null");
        }

        Request request = getRequest();

        Timber.e("GNS URL : " + mURL);
        if (mClient != null){
            Response response = mClient.newCall(request).execute();
            if (!response.isSuccessful()){
                e = new IOException("Unexpected code " + response);
                throw e;
            }

            t = (T) ParseUtil.getParserFactory().responseBodyParser(mClass).convert(response.body());

            try {
                response.body().close();
            } catch (Exception ignored) {
            }
        }else{
            e = new IOException("Client is null");
            throw e;
        }


        return t;
    }


    public HttpCore<T> getAsFuture(final ParsedCallback<T> callback) {

        if (mURL.isEmpty()){
            callback.onFailure(new IOException("URL is null"));
            return this;
        }

        Request request = getRequest();
        Timber.e("ASYNC URL : " + mURL);

        if (mClient != null){
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException ex) {
                    e = ex;
                    callback.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (mClass != String.class) {
                        Timber.e("mClass != String");
                        t = (T) ParseUtil.getParserFactory().responseBodyParser(mClass).convert(response.body());
                    } else {
                        Timber.e("mClass == String");
                        t = (T) response.body().string();
                    }
                    callback.onCompleted(t);
                }
            });
        }else{
            e = new IOException("Client is null");
            callback.onFailure(e);

        }
        return this;
    }

    public HttpCore getAsString(){
        mClass = String.class;
        Request request = provideRequestBuilder()
                .build();

        if (mClient != null){
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    t = (T) response.body().string();
                }
            });
        }else{
            e = new IOException("Client is null");
        }

        return this;
    }


}
