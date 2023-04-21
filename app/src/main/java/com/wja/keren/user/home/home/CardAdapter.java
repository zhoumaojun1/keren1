package com.wja.keren.user.home.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.bean.CardListBean;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    Context context;
    List<CardListBean.CardList> cardLists;

    public CardAdapter(Context _context, List<CardListBean.CardList> _list) {
        this.cardLists = _list;
        this.context = _context;
    }

    @Override
    public int getCount() {
        return cardLists.size();
    }

    @Override
    public Object getItem(int position) {
        return cardLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.activity_main_item1, null);
        TextView tvCardName =  convertView.findViewById(R.id.tvCardName);
        ImageView ivCardImg =convertView.findViewById(R.id.ivCardIcon);
        CardListBean.CardList cardListBean = cardLists.get(position);
        tvCardName.setText(cardListBean.getName());
        Config.DEVICE_ID = cardListBean.getId();
        Config.DEVICE_NAME = cardListBean.getName();

//
//        Glide.with(context)
//                .load(cardListBean.getPhoto())
//                .into(ivCardImg);
        return convertView;
    }


}
