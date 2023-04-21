package com.wja.keren.user.home.mine;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wja.keren.R;
import com.wja.keren.user.home.mine.card.AddShareAccountActivity;
import com.wja.keren.user.home.mine.card.CardShareInviteActivity;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.List;

public class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.ContentsHolder>{
    List<Contents> mContents;
    Activity mActivity;

    public ContentsAdapter(List<Contents> contents, Activity activity) {
        mContents = contents;
        mActivity =activity;
    }

    @NonNull

    @Override
    public ContentsHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_phone_list, parent, false);
        ContentsHolder holder = new ContentsHolder(view);



        view.setOnClickListener(v -> {
            int tag = (int) view.getTag();
            IntentUtil.get().goActivityResult(mActivity, CardShareInviteActivity.class,holder.number.getText().toString());


        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ContentsHolder holder, int position) {
        Contents contents = mContents.get(position);
        holder.name.setText(contents.getName());
        holder.number.setText(contents.getTel());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    class ContentsHolder extends RecyclerView.ViewHolder{
        public TextView number;
        public TextView name;

        public ContentsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.phone_name);
            number = itemView.findViewById(R.id.phone_number);
        }
    }

}
