package com.bawie.myrecyclerview2.model;

import com.bawie.myrecyclerview2.Api;
import com.bawie.myrecyclerview2.bean.Bean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张肖肖 on 2017/10/25.
 */

public class UserModel {

    public void getUserData(int page){

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody build = builder.build();
        final Request request = new Request.Builder().url(Api.C_URL+page).post(build).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //彻底失败

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful() && response!= null){
                    final String result;
                    try {
                        result = response.body().string();
                        Gson gson = new Gson();
                        Bean bean = gson.fromJson(result, Bean.class);
                        List<Bean.DataBean> data = bean.getData();
                        int code = bean.getCode();
                        String message = (String) bean.getMessage();

                        if(code == 1){
                            //成功
                            onUser.getUserSuccess(data);

                        }else{
                            //失败
                            onUser.getUserFailure(message);
                        }
                    }catch (Exception e){

                    }
                }
            }
        });

    }

    private onUser onUser;

    public void setOnUser(UserModel.onUser onUser) {
        this.onUser = onUser;
    }

    public interface onUser{
        void getUserSuccess(List<Bean.DataBean> data);
        void getUserFailure(String message);
        void onFailure();
    }


}
