package com.wja.keren.user.home.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.bean.CardListBean;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{
    private List<CardListBean.CardList> mContactList;
    Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View contactView;//存储解析到的view
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view){
            super(view);
            contactView = view;
            imageView = view.findViewById(R.id.iv_card_img);
            textView = view.findViewById(R.id.tv_card_name);
        }
    }

    public ContactAdapter(Context context,List<CardListBean.CardList> contactList){
        this.mContext = context;
        mContactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_list_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);//新建一个viewHolder绑定解析到的view
        int position = viewHolder.getAdapterPosition();
        CardListBean.CardList contact = mContactList.get(position);
        Config.DEVICE_ID = contact.getId();
        //监听每一项的点击事件
        viewHolder.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.DEVICE_ID = contact.getId();
                Toast.makeText(view.getContext(),contact.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Toast.makeText(view.getContext(),contact.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardListBean.CardList contact = mContactList.get(position);
        Glide.with(mContext)
                .load(contact.getPhoto())
                .into(holder.imageView);
        holder.textView.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }



}
