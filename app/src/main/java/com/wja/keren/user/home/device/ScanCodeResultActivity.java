package com.wja.keren.user.home.device;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.ScanCodeBindBean;
import com.wja.keren.user.home.main.MainPresenter;

import butterknife.BindView;

public class ScanCodeResultActivity extends BaseActivity<ScanCode.Presenter> implements ScanCode.View {

    @BindView(R.id.tv_card_name)
    TextView tvCardName;

    @BindView(R.id.tv_card_now_bind)
    TextView tvBind;
    @BindView(R.id.tv_card_frame_code)
    TextView tvCardFrameCode;


    @BindView(R.id.iv_card_photo)
    ImageView ivCardPhoto;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ble_scan_result_item;
    }

    @Override
    protected void init() {
        setToolbarTitle("扫码结果");

        setLeftIcon(R.mipmap.card_back);
        presenter = new ScanCodePresenter(this);
        presenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setOnclick();
           presenter.snQueryDeviceInfo(getIntent().getStringExtra("bundle"));

    }

    private void showBottomSheetDialog() {
        DeviceListFragment fragment = DeviceListFragment.newInstance();
        fragment.show(getSupportFragmentManager(), DeviceListFragment.class.getSimpleName());
    }

    void setOnclick() {
        tvBind.setOnClickListener(view -> {
            tvBind.setText("已绑定");
            tvBind.setTextColor(getColor(R.color.white));
            tvBind.setBackgroundResource(R.drawable.shape_btn_confirm_pressed);
            presenter.nowBindDevice(getIntent().getStringExtra("bundle"));
            //finish();
        });
    }

    @Override
    public void showQueryDeviceList(ScanCodeBindBean.ScanCodeBind scanCodeBind) {
        tvCardName.setText(scanCodeBind.getName());

        //tvBind.setText(scanCodeBind.getIs_bind() == 0 ? "已绑定":"未绑定");

        tvCardFrameCode.setText(scanCodeBind.getName());
//        Glide.with(ScanCodeResultActivity.this)
//                .load(scanCodeBind.getImage())
//                .into(ivCardPhoto);
    }

    @Override
    public void updateBindDevice() {
        showBottomSheetDialog();
    }
}
