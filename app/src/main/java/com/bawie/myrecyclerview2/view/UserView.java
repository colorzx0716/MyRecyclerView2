package com.bawie.myrecyclerview2.view;

import com.bawie.myrecyclerview2.bean.Bean;

import java.util.List;

/**
 * Created by 张肖肖 on 2017/10/25.
 */

public interface UserView {
    void getUserSuccess(List<Bean.DataBean> data);
    void getUserFailure(String message);
    void onFailure();
}
