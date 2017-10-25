package com.bawie.myrecyclerview2;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bawie.myrecyclerview2.bean.Bean;
import com.bawie.myrecyclerview2.presenter.UserPresenter;
import com.bawie.myrecyclerview2.view.UserView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserView {

    private UserPresenter presenter;
    private RecyclerView main_rv;
    private SwipeRefreshLayout main_srl;
    //新建一个
    private List<Bean.DataBean> data1 = new ArrayList<>();
    private int page = 1;
    private MyAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initData() {
        presenter = new UserPresenter(this);
        presenter.initUser(1);
    }

    private void initView() {
        //寻找控件
        main_srl = (SwipeRefreshLayout) findViewById(R.id.main_srl);
        main_rv = (RecyclerView) findViewById(R.id.main_rv);
        manager = new LinearLayoutManager(MainActivity.this);
        main_rv.setLayoutManager(manager);
        main_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount()-1){
                        page=page+1;
                        presenter.initUser(page);
                        System.out.println("page = " + page);
                        Toast.makeText(MainActivity.this, "下拉加载", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void getUserSuccess(final List<Bean.DataBean> data) {
        //请求数据成功
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("data.toString() = " + data.toString());
                //全部添加进去
                data1.addAll(data);
                if(adapter == null){
                    adapter = new MyAdapter(MainActivity.this,data1);
                   main_rv.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();//刷新
                }
                adapter.setToastMsg(new MyAdapter.ToastMsg() {
                    @Override
                    public void toastMsg(View view, int pos) {
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, data1.get(pos).getOccupation(), Toast.LENGTH_SHORT).show();
                    }
                });
                main_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        data.clear();
                        presenter.initUser(1);
                        Toast.makeText(MainActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
                        main_srl.setRefreshing(false);
                    }
                });
            }
        });

    }

    @Override
    public void getUserFailure(String message) {

    }

    @Override
    public void onFailure() {

    }
}
