package com.bawie.myrecyclerview2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawie.myrecyclerview2.bean.Bean;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 张肖肖 on 2017/10/25.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final Context context;
    private final List<Bean.DataBean> data;
    private View view;

    public MyAdapter(Context context, List<Bean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = View.inflate(context, R.layout.list_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.list_age.setText(data.get(position).getUserAge()+"岁");
        holder.list_des.setText(data.get(position).getIntroduction());
        holder.list_work.setText(data.get(position).getOccupation());
        Glide.with(context).load(data.get(position).getUserImg()).into(holder.list_img);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toastMsg.toastMsg(view,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView list_img;
        private final TextView list_age;
        private final TextView list_work;
        private final TextView list_des;

        public MyViewHolder(View itemView) {
            super(itemView);
            list_img = itemView.findViewById(R.id.list_img);
            list_age = itemView.findViewById(R.id.list_age);
            list_work = itemView.findViewById(R.id.list_work);
            list_des = itemView.findViewById(R.id.list_des);
        }
    }

    private ToastMsg toastMsg;

    public void setToastMsg(ToastMsg toastMsg) {
        this.toastMsg = toastMsg;
    }

    public interface ToastMsg{
        void toastMsg(View view,int pos);
    }

}
