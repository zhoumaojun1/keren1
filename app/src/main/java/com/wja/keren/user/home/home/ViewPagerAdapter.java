package com.wja.keren.user.home.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wja.keren.R;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.mine.card.CardInfoActivity;
import com.wja.keren.user.home.mine.card.CardSettingActivity;
import com.wja.keren.user.home.mine.card.UseCardShareActivity;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<CardListBean.CardList> allList;
    private  Activity activity;
    ViewPagerAdapter(Activity activity, List<CardListBean.CardList> allList){
        this.allList = allList;
        this.activity = activity;
    }

    public void refresh( List<CardListBean.CardList> allList) {
        this.allList.clear();
        this.allList.addAll(allList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
     View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        CardListBean.CardList cardListBean=  allList.get(position);
        Intent intent =new Intent();
        Bundle bundle =new Bundle();
        bundle.putSerializable("cardListBean",cardListBean);
        intent.putExtras(bundle);
        view.setOnClickListener(view1 -> {
            IntentUtil.get().goActivityResult(activity, CardSettingActivity.class,intent);
        });


        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = position % 4;
        holder.tvCardName.setText(allList.get(position).getName());
        holder.itemView.setTag(i);

//                    Glide.with(activity)
//                    .load(allList.get(position).getPhoto())
//                    .into(holder.container);
    }

    @Override
    public int getItemCount() {
        //实现无限轮播
        return allList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView container;
        TextView tvCardName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.ivCardIcon);
            tvCardName = itemView.findViewById(R.id.tvCardName);
        }
    }

}
