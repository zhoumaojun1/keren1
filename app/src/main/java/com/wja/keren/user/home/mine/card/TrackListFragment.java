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

import com.bumptech.glide.Glide;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseFragment;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.CardRunBean;
import com.wja.keren.user.home.bean.CardRunListBean;
import com.wja.keren.user.home.mine.CardRunPresenter;
import com.wja.keren.user.home.mine.CardRunSet;
import com.wja.keren.user.home.util.FormatUtil;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TrackListFragment extends BaseFragment<CardRunSet.Presenter> implements CardRunSet.View {
    @BindView(R.id.rl_run_record_recyclerView)
    RecyclerView recyclerView ;
    private final List<CardRunListBean.AllList.OneList> scanResultList = new ArrayList<>();
    Adapter adapter;
    public static TrackListFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        TrackListFragment fragment = new TrackListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test1;
    }

    @Override
    protected CardRunSet.Presenter createPresenter() {
        return new CardRunPresenter(getContext());
    }

    @Override
    protected void init() {


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.runRouteList(Config.DEVICE_ID,0,10,0,0);
        if(adapter == null) {
            adapter = new Adapter();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void showCardList(List<CardRunListBean.AllList.OneList> allList) {
        adapter.refresh(allList);
    }

    @Override
    public void showRouteAllData(CardRunBean userNick) {

    }

    class Adapter extends RecyclerView.Adapter {

        private final List<CardRunListBean.AllList.OneList> allList = new ArrayList<>();


        public void refresh(List<CardRunListBean.AllList.OneList> allList) {
            this.allList.clear();
            this.allList.addAll(allList);
            notifyDataSetChanged();
        }

        @androidx.annotation.NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_run_location_item,viewGroup,false);
            view.setOnClickListener(v->{
                int tag = (int) view.getTag();
                Bundle bundle = new Bundle();
                CardRunListBean.AllList.OneList oneList =allList.get(position);
                bundle.putSerializable("oneList", oneList);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                IntentUtil.get().goActivityResult(getActivity(),TrackDetailsActivity.class,intent);

            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder( @androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ImageView ivCardPhoto = viewHolder.itemView.findViewById(R.id.iv_card_photo);

            TextView tvCardTime = viewHolder.itemView.findViewById(R.id.tv_card_run_time);
            TextView tvStartLocation = viewHolder.itemView.findViewById(R.id.tv_card_run_start_location);
            TextView tvEndLocation = viewHolder.itemView.findViewById(R.id.tv_card_run_end_location);

            TextView tvRunMileage = viewHolder.itemView.findViewById(R.id.tv_run_mileage);
            TextView tvRunTime = viewHolder.itemView.findViewById(R.id.tv_run_time);
            TextView tvRunSpeed = viewHolder.itemView.findViewById(R.id.tv_run_speed);

            tvCardTime.setText( FormatUtil.formatDate(allList.get(i).getStart_time() * 1000L) );
            tvStartLocation.setText(allList.get(i).getStart_name());
            tvEndLocation.setText(allList.get(i).getEnd_name());


            tvRunMileage.setText(allList.get(i).getAvg_speed()+"");
           // tvRunTime.setText( FormatUtil.formatDate(allList.get(i).getEnd_time() * 1000L));
            tvRunSpeed.setText(allList.get(i).getAvg_speed()+"");

//            Glide.with(getActivity())
//                    .load(allList.get(i).getEnd_name())
//                    .into(ivCardPhoto);
            viewHolder.itemView.setTag(i);

        }

        @Override
        public int getItemCount() {
            return allList.size();
        }
    }
}
