package com.wja.keren.user.home.device;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wja.keren.R;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.BleScanResultBean;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.util.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceListFragment1 extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();
    Adapter adapter;
    public static DeviceListFragment1 newInstance() {
        DeviceListFragment1 fragment = new DeviceListFragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View    view = View.inflate(getContext(), R.layout.bottom_tab_device_bind, null);
       TextView ivMainTabHome = view.findViewById(R.id.ll_main_tab);
        if(adapter == null) {
            adapter = new Adapter();
        }
        RecyclerView recyclerView = view.findViewById(R.id.card_ble_recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        AnimationUtils.slideToUp(getActivity(),view);
        if (bottomSheetDialog.getWindow() != null) {//灰色的阴影效果去除
            WindowManager.LayoutParams params = bottomSheetDialog.getWindow().getAttributes();
            params.dimAmount = 0.0f;
            bottomSheetDialog.getWindow().setAttributes(params);
        }
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);


        ivMainTabHome.setOnClickListener(view1 -> {
            dismiss();



        });
        return bottomSheetDialog;

    }

    public void updateHomeList(List <BleScanResultBean> scanResultList) {

        adapter.refresh(scanResultList);
    }

    class Adapter extends RecyclerView.Adapter {

        private final List<BleScanResultBean> scanResultList = new ArrayList<>();


        public void refresh(List<BleScanResultBean> scanResultList) {
            this.scanResultList.clear();
            this.scanResultList.addAll(scanResultList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_ble_scan_result_item,viewGroup,false);
            view.setOnClickListener(v->{
                int tag = (int) view.getTag();

              //  pushFragment( FamilyDetailFragment.newFragment(list.get(tag).homeId));

            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ImageView ivCardPhoto = viewHolder.itemView.findViewById(R.id.iv_card_photo);
            TextView tvCardCode = viewHolder.itemView.findViewById(R.id.tv_card_code);
            TextView tvCardName = viewHolder.itemView.findViewById(R.id.tv_card_name);
         //   TextView tvCardPairStatus = viewHolder.itemView.findViewById(R.id.tv_card_pair_status);
           // TextView tvCardPair = viewHolder.itemView.findViewById(R.id.tv_card_now_pair);
            tvCardName.setText(scanResultList.get(i).getDeviceName());
            tvCardCode.setText(scanResultList.get(i).getCardCode());
           // tvCardPairStatus.setText(scanResultList.get(i).getPairStatus());
          //  tvCardPair.setText(scanResultList.get(i).getPairStatus());
            Glide.with(getActivity())
                    .load(scanResultList.get(i).getCardPhoto())
                    .into(ivCardPhoto);
            viewHolder.itemView.setTag(i);

        }

        @Override
        public int getItemCount() {
            return scanResultList.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setClipToOutline(true);
        window.setAttributes(params);
        window.setAllowEnterTransitionOverlap(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}
