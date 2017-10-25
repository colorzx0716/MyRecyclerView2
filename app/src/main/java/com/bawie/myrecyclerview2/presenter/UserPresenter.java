package com.bawie.myrecyclerview2.presenter;

import android.content.Context;

import com.bawie.myrecyclerview2.bean.Bean;
import com.bawie.myrecyclerview2.model.UserModel;
import com.bawie.myrecyclerview2.view.UserView;

import java.util.List;

/**
 * Created by 张肖肖 on 2017/10/25.
 */

public class UserPresenter implements UserModel.onUser {

    private UserModel userModel;
    private Context context;
    private UserView userView;

    public UserPresenter(UserView userView) {
        this.userView = userView;
        userModel = new UserModel();
        userModel.setOnUser(this);
    }

    //自己定义一个方法
    public void initUser(int page){
       userModel.getUserData(page);
    }


    @Override
    public void getUserSuccess(List<Bean.DataBean> data) {
        userView.getUserSuccess(data);

    }

    @Override
    public void getUserFailure(String message) {
      userView.getUserFailure(message);
    }

    @Override
    public void onFailure() {
        userView.onFailure();

    }
}
