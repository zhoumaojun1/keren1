package com.wja.keren.user.home.mine.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseFragment;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.mine.CardShareCom;
import com.wja.keren.user.home.mine.CardSharePresenter;
import com.wja.keren.user.home.util.FormatUtil;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CardShareFragment extends BaseFragment<CardShareCom.Presenter> implements CardShareCom.View {

    @BindView(R.id.card_share_recyclerView)
    RecyclerView recyclerView;
    Adapter adapter;

    public static CardShareFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        CardShareFragment fragment = new CardShareFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_share_list;
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
        presenter.cardShare(Config.DEVICE_ID,0 );
        if (adapter == null) {
            adapter = new Adapter();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }


    class Adapter extends RecyclerView.Adapter {

        private final List<CardShareBean.CardShare.CardInfo> allList = new ArrayList<>();


        public void refresh(List<CardShareBean.CardShare.CardInfo> allList) {
            this.allList.clear();
            this.allList.addAll(allList);
            notifyDataSetChanged();
        }

        @androidx.annotation.NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_card_share_item, viewGroup, false);
            view.setOnClickListener(v -> {
                int tag = (int) view.getTag();
                Bundle bundle = new Bundle();
                CardShareBean.CardShare.CardInfo oneList = allList.get(position);
                bundle.putSerializable("oneList", oneList);
                Intent intent = new Intent();
                intent.putExtras(bundle);
               // IntentUtil.get().goActivityResult(getActivity(), TrackDetailsActivity.class, intent);
            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ImageView ivCardPhoto = viewHolder.itemView.findViewById(R.id.iv_card_share_photo);

            TextView tvShareName = viewHolder.itemView.findViewById(R.id.iv_card_share_name);
            TextView tvShareTime = viewHolder.itemView.findViewById(R.id.iv_card_share_time);
            TextView tvShareNumber = viewHolder.itemView.findViewById(R.id.iv_card_share_number);



            tvShareName.setText(allList.get(i).getName());
            tvShareTime.setText(FormatUtil.formatDate(allList.get(i).getShare_time()));
            tvShareNumber.setText(allList.get(i).getEbike_id() + "人");

//            Glide.with(getActivity())
//                    .load(allList.get(i).getPhoto())
//                    .into(ivCardPhoto);
            viewHolder.itemView.setTag(i);

        }

        @Override
        public int getItemCount() {
            return allList.size();
        }
    }

    @Override
    public void showError(int resId) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showCardShare(CardShareBean.CardShare cardCardShare) {
        adapter.refresh(cardCardShare.getList());
    }

    @Override
    public void showShareAgree(CardShareBean.CardShare cardShareAgree) {
        if (null != cardShareAgree.getList() && cardShareAgree.getList().size() > 0) {
            adapter.refresh(cardShareAgree.getList());
        }


    }

    @Override
    public void showGarage(List<CardListBean.CardList> cardRunBean) {

    }
}
