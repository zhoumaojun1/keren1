package com.wja.keren.user.home.mine.card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseFragment;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.mine.CardShareCom;
import com.wja.keren.user.home.mine.CardSharePresenter;
import com.wja.keren.user.home.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CardGarageFragment extends BaseFragment<CardShareCom.Presenter> implements CardShareCom.View {

    @BindView(R.id.card_garage_recyclerView)
    RecyclerView recyclerView;
    Adapter adapter;

    public static CardGarageFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        CardGarageFragment fragment = new CardGarageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_garage_list;
    }

    @Override
    public CardShareCom.Presenter createPresenter() {
        return new CardSharePresenter(getContext());
    }

    @Override
    protected void init() {


    }

    @Override
    public void onResume() {
        super.onResume();
           presenter.cardGarage();
        if (adapter == null) {
            adapter = new Adapter();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }


    class Adapter extends RecyclerView.Adapter {

        private final List<CardListBean.CardList> allList = new ArrayList<>();


        public void refresh( List<CardListBean.CardList> allList) {
            this.allList.clear();
            this.allList.addAll(allList);
            notifyDataSetChanged();
        }

        @androidx.annotation.NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_card_garage_item, viewGroup, false);
            view.setOnClickListener(v -> {
                int tag = (int) view.getTag();
            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ImageView ivCardPhoto = viewHolder.itemView.findViewById(R.id.iv_card_share_photo);
            TextView tvShareName = viewHolder.itemView.findViewById(R.id.iv_card_share_name);
            TextView tvShareTime = viewHolder.itemView.findViewById(R.id.iv_card_share_time);

            tvShareTime.setText(FormatUtil.formatDate(allList.get(i).getCreated_at() * 1000L));
            tvShareName.setText(allList.get(i).getName());
            Glide.with(getActivity())
                    .load(allList.get(i).getPhoto())
                    .into(ivCardPhoto);
            viewHolder.itemView.setTag(i);
        }

        @Override
        public int getItemCount() {
            return allList.size();
        }
    }


    @Override
    public void showCardShare(CardShareBean.CardShare userHead) {

    }

    @Override
    public void showShareAgree(CardShareBean.CardShare cardRunBean) {

    }

    @Override
    public void showGarage(List<CardListBean.CardList> cardRunBean) {
        adapter.refresh(cardRunBean);

    }
}
