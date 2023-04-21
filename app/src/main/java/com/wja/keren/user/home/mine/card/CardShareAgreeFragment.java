package com.wja.keren.user.home.mine.card;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CardShareAgreeFragment extends BaseFragment<CardShareCom.Presenter> implements CardShareCom.View {

    @BindView(R.id.card_share_agree_recyclerView)
    RecyclerView recyclerView;
    Adapter adapter;

    public static CardShareAgreeFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        CardShareAgreeFragment fragment = new CardShareAgreeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_share_agree_list;
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
       presenter.CardShareAgree(Config.DEVICE_ID,1);
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

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_card_share_agree_item, viewGroup, false);
            view.setOnClickListener(v -> {
                int tag = (int) view.getTag();
                presenter.acceptCardShare(allList.get(tag).getId());

            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ImageView ivCardPhoto = viewHolder.itemView.findViewById(R.id.iv_card_share_photo);

            TextView tvShareFrom = viewHolder.itemView.findViewById(R.id.tv_from_share);
            TextView tvShareName = viewHolder.itemView.findViewById(R.id.iv_card_share_name);
            TextView tvShareTime = viewHolder.itemView.findViewById(R.id.iv_card_share_che_jia);
            TextView tvShareNumber = viewHolder.itemView.findViewById(R.id.iv_card_share_agree_status);

            tvShareFrom.setText(allList.get(i).getUser_id()+"");
            tvShareTime.setText(FormatUtil.formatDate(allList.get(i).getUpdated_at())+"");
            tvShareName.setText(allList.get(i).getName());
            if (allList.get(i).getStatus() == 1) {
                tvShareNumber.setText(allList.get(i).getStatus() + "接受接受");
            } else if (allList.get(i).getStatus() == 2) {
                tvShareNumber.setText(allList.get(i).getStatus() + "已接受");
            } else if (allList.get(i).getStatus() == 3) {
                tvShareNumber.setText(allList.get(i).getStatus() + "已接受");
            }
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
    public void showCardShare(CardShareBean.CardShare cardRunBean) {


    }

    @Override
    public void showShareAgree(CardShareBean.CardShare cardRunBean) {
        if (null != cardRunBean && null != cardRunBean.getList() && cardRunBean.getList().size() > 0) {
            adapter.refresh(cardRunBean.getList());
        }

    }

    @Override
    public void showGarage(List<CardListBean.CardList> cardRunBean) {

    }
}
