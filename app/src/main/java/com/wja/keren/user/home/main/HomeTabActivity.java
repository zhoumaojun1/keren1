package com.wja.keren.user.home.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.bean.LoginInfoBean;
import com.wja.keren.user.home.bean.UserShareCardListBean;
import com.wja.keren.user.home.find.FindDeviceFragment;
import com.wja.keren.user.home.home.HomeFragment;
import com.wja.keren.user.home.mine.MineFragment;
import com.wja.keren.user.home.util.AnimationUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeTabActivity extends BaseActivity <MainContact.Presenter> implements MainContact.View, HomeFragment.OnClickCloseDialog{
    private static final String TAG = HomeTabActivity.class.getName();
    public HomeFragment homeFragment;
    private FragmentManager fManager;

    public static final int RC_CAMERA = 0X01;

    // 定义一个变量，来标识是否退出
    private Boolean isExit = false;
    private Toast toast;
    @BindView(R.id.ll_main_tab)
    LinearLayout ivMainTabHome;

    @BindView(R.id.ll_find_tab)
    LinearLayout ivMainTabFind;
    @BindView(R.id.ll_mine_tab)
    LinearLayout ivMainTabMine;

    @BindView(R.id.iv_home_icon)
    ImageView ivMainIcon;

    @BindView(R.id.iv_find_icon)
    ImageView ivFindIcon;

    @BindView(R.id.iv_mine_icon)
    ImageView ivMineIcon;

    @BindView(R.id.ll_bottom_tab)
    LinearLayout ll_bottom_tab;

    private FindDeviceFragment findDeviceFragment;
    private MineFragment mineFragment;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_top_bottom_home;
    }
    int isTag ;
    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (getIntent() != null) {
            isTag = getIntent().getIntExtra("isTag", 0);
        }
        if (bundle != null) {
            if (bundle.getSerializable("userInfo") != null) {
                LoginInfoBean.UserInfo userInfoBean = (LoginInfoBean.UserInfo) bundle.getSerializable("userInfo");
                userInfoBean.getToken();
            }
        }
        HomeFragment.InfoService infoService = new HomeFragment.InfoService();
        infoService.setOnClickCloseDialog(this::closeDialog);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        presenter = new MainPresenter(this);
        presenter.attachView(this);


        fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        homeFragment =  HomeFragment.newInstance("this",isTag);
        fTransaction.add(R.id.monitor_frame, homeFragment);
        fTransaction.show(homeFragment);
        fTransaction.commit();
    }
    @OnClick({R.id.ll_bottom_tab, R.id.ll_find_tab, R.id.ll_mine_tab})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.ll_bottom_tab:
                ivMainIcon.setImageResource(R.mipmap.main_card);
                ivFindIcon.setImageResource(R.mipmap.main_find_default);//关于这两行：点击按键后按键本身的样子也会发生改变，实现被选中的状态
                ivMineIcon.setImageResource(R.mipmap.main_mine_default);

//                tvMainText.setTextColor(getResources().getColor(R.color.color_1FC8A9));
//                tvFindText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
//                tvMineText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
                homeFragment =  HomeFragment.newInstance("this",isTag);
                getSupportFragmentManager().beginTransaction().replace(R.id.monitor_frame, homeFragment).commit();
                break;
            case R.id.ll_find_tab:
                ivMainIcon.setImageResource(R.mipmap.main_card_default);
                ivFindIcon.setImageResource(R.mipmap.main_find_check);
                ivMineIcon.setImageResource(R.mipmap.main_mine_default);

//                tvFindText.setTextColor(getResources().getColor(R.color.color_1FC8A9));
//                tvMainText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
//                tvMineText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
//

                findDeviceFragment = new FindDeviceFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.monitor_frame, findDeviceFragment).commit();

                break;
            case R.id.ll_mine_tab:
                ivMainIcon.setImageResource(R.mipmap.main_card_default);
                ivFindIcon.setImageResource(R.mipmap.main_find_default);
                ivMineIcon.setImageResource(R.mipmap.main_mine_check);

//                tvMineText.setTextColor(getResources().getColor(R.color.color_1FC8A9));
//                tvMainText.setTextColor(getResources().getColor(R.color.color_AAAAAA));
//                tvFindText.setTextColor(getResources().getColor(R.color.color_AAAAAA));

                mineFragment = new MineFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.monitor_frame, mineFragment).commit();

                break;

            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(),"再按一次返回键退出程序",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }


    }

    @Override
    public void closeDialog(boolean isOnClickScreen) {
        if (isOnClickScreen == false) {
            ll_bottom_tab.setVisibility(View.INVISIBLE);
            AnimationUtils.slideToDown(this,ll_bottom_tab);
        }else {
            AnimationUtils.slideToUp(this,ll_bottom_tab);
            ll_bottom_tab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCardInfo(CardInfoBean.CardDetailed cardDetailed) {

    }


    @Override
    public void showCardShareList(CardShareBean.CardShare userHead) {

    }

    @Override
    public void updateShareNumberList() {

    }

    @Override
    public void showCardShareUserNumber(UserShareCardListBean.UserShareCard cardRunBean) {

    }


}
