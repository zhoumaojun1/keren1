package com.wja.keren.user.home.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.LoginInfoBean;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectRoleActivity extends BaseActivity {
    @BindView(R.id.tv_user_card)
    RelativeLayout rlUserCard;
    @BindView(R.id.tv_distributor_card)
    RelativeLayout rlDisCard;
    @BindView(R.id.tv_admin_card)
    RelativeLayout rlAdminCard;

    @BindView(R.id.tv_user_use_card)
    TextView tvUserCard;

    @BindView(R.id.tv_distributor_use_card)
    TextView tvDisCard;

    @BindView(R.id.tv_admin_use_card)
    TextView tvAdminCard;

    @BindView(R.id.iv_user_left)
    ImageView ivUserLeft;

    @BindView(R.id.iv_user_right)
    ImageView ivUserRight;

    @BindView(R.id.iv_dis_left)
    ImageView ivDisLeft;

    @BindView(R.id.iv_dis_right)
    ImageView ivDisRight;
    @BindView(R.id.iv_admin_left)
    ImageView ivAdminLeft;

    @BindView(R.id.iv_admin_right)
    ImageView ivAdminRight;
    @BindView(R.id.btn_next_login_home)
    Button btnNextLogin;
    LoginInfoBean.UserInfo userInfoBean;
    List<Integer> userList = new ArrayList<>();
    private int userType = 0;//默认用户用车
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_select_role;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("userInfo") != null) {
                 userInfoBean = (LoginInfoBean.UserInfo) bundle.getSerializable("userInfo");
                userInfoBean.getToken();
               userList =userInfoBean.getRoles();

            }
        }
        rlUserCard.setOnClickListener(view -> {
            userType = 1;
            rlUserCard.setBackgroundResource(R.drawable.shape_btn_ver_code);
            rlDisCard.setBackgroundResource(R.drawable.corners_bg_blue);
            rlAdminCard.setBackgroundResource(R.drawable.corners_bg_blue);

            tvUserCard.setTextColor(getResources().getColor(R.color.color_1FC8A9));
            tvDisCard.setTextColor(getResources().getColor(R.color.white));

            ivUserLeft.setVisibility(View.VISIBLE);
            ivUserRight.setVisibility(View.VISIBLE);

            ivDisLeft.setVisibility(View.INVISIBLE);
            ivDisRight.setVisibility(View.INVISIBLE);

            ivAdminLeft.setVisibility(View.INVISIBLE);
            ivAdminRight.setVisibility(View.INVISIBLE);

            ivDisLeft.setVisibility(View.INVISIBLE);
            ivDisRight.setVisibility(View.INVISIBLE);


        });
        rlDisCard.setOnClickListener(view -> {
            userType = 2;
            ivDisLeft.setVisibility(View.VISIBLE);
            ivDisRight.setVisibility(View.VISIBLE);

            ivUserLeft.setVisibility(View.INVISIBLE);
            ivUserRight.setVisibility(View.INVISIBLE);

            ivAdminLeft.setVisibility(View.INVISIBLE);
            ivAdminRight.setVisibility(View.INVISIBLE);


            rlUserCard.setBackgroundResource(R.drawable.corners_bg_blue);
            rlDisCard.setBackgroundResource(R.drawable.shape_btn_ver_code);
            rlAdminCard.setBackgroundResource(R.drawable.corners_bg_blue);

            tvUserCard.setTextColor(getResources().getColor(R.color.white));
            tvAdminCard.setTextColor(getResources().getColor(R.color.white));
            tvDisCard.setTextColor(getResources().getColor(R.color.color_1FC8A9));

        });
        rlAdminCard.setOnClickListener(view -> {
            userType = 3;
            rlUserCard.setBackgroundResource(R.drawable.corners_bg_blue);
            rlDisCard.setBackgroundResource(R.drawable.corners_bg_blue);
            rlAdminCard.setBackgroundResource(R.drawable.shape_btn_ver_code);

            tvAdminCard.setTextColor(getResources().getColor(R.color.color_1FC8A9));
            tvDisCard.setTextColor(getResources().getColor(R.color.white));
            tvUserCard.setTextColor(getResources().getColor(R.color.white));

            ivDisLeft.setVisibility(View.INVISIBLE);
            ivDisRight.setVisibility(View.INVISIBLE);

            ivUserLeft.setVisibility(View.INVISIBLE);
            ivUserRight.setVisibility(View.INVISIBLE);

            ivAdminLeft.setVisibility(View.VISIBLE);
            ivAdminRight.setVisibility(View.VISIBLE);


        });
        btnNextLogin.setOnClickListener(view -> {
            if (userType != 1){
                ToastUtils.ToastMessage(this,"目前仅支持用户版本");
                return;
            }
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("userInfo", userInfoBean);
            Intent intent = new Intent();
            intent.putExtras(bundle1);
            IntentUtil.get().goActivityResult(SelectRoleActivity.this, HomeTabActivity.class, intent);
        });
    }
}
