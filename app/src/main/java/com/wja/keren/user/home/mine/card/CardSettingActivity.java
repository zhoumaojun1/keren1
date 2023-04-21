package com.wja.keren.user.home.mine.card;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.king.zxing.util.CodeUtils;
import com.king.zxing.util.LogUtils;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.mine.card.dialog.CardLockFragment;
import com.wja.keren.user.home.mine.card.dialog.CardSetImgFragment;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.view.AvatarView;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardSettingActivity extends BaseActivity {
    public static final int REQUEST_CODE_CAMERA = 222;
    public static final int REQUEST_CODE_CROP = 223;


    @BindView(R.id.btn_card_lock)
    Button btnCardStatus;
    CardListBean.CardList cardListBean =new CardListBean.CardList();

    @BindView(R.id.iv_change_user_head)
    AvatarView headImg;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_card_setting;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_setting);
        queryCardInfo(Config.DEVICE_ID);


        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (null != bundle.getSerializable("cardListBean")) {
                cardListBean = ( CardListBean.CardList) bundle.getSerializable("cardListBean");
                //initCardInfo(cardListBean);
            }

        }
    }

    @OnClick({R.id.rl_card_set_head,R.id.rl_card_set_name,R.id.rl_card_share,R.id.rl_ai_config,R.id.rl_run_info,R.id.rl_card_info
            ,R.id.btn_card_lock,R.id.tv_now_renew})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.rl_card_set_head:
                showBottomCardImgSheetDialog();
                break;
            case R.id.rl_card_set_name:
                IntentUtil.get().goActivityResult(this, CardNameEditActivity.class, Config.DEVICE_ID);
                break;
            case R.id.rl_card_share:
                Intent intent =new Intent();
                Bundle bundle =new Bundle();
                bundle.putSerializable("cardListBean",cardListBean);
                intent.putExtras(bundle);
                IntentUtil.get().goActivityResult(this, UseCardShareActivity.class,intent);
                break;
            case R.id.rl_ai_config:
                IntentUtil.get().goActivity(this, AiConfigActivity.class);
                break;
            case R.id.rl_run_info:
                IntentUtil.get().goActivity(this, CardRunInfoActivity.class);
                break;
            case R.id.rl_card_info:
                Intent intent1 =new Intent();
                Bundle bundle1 =new Bundle();
                bundle1.putSerializable("cardListBean",cardListBean);
                intent1.putExtras(bundle1);
                IntentUtil.get().goActivityResult(this, CardInfoActivity.class,intent1);
                break;
            case R.id.btn_card_lock:
                showBottomCardLockSheetDialog(btnCardStatus.getText().toString());
                break;
            case R.id.tv_now_renew:
                IntentUtil.get().goActivity(this, MyTrafficActivity.class);
                break;

            default:
                break;
        }
    }


    public    void toast (int status){
        if (status ==1){
            ToastUtils.ToastMessage(this,"锁定成功");
        }else {
            ToastUtils.ToastMessage(this,"解锁成功");
        }


    }

    CardInfoBean.CardDetailed cardDetailed = new CardInfoBean.CardDetailed();
    /**
     * 查询车辆详情
     */
    @SuppressLint("CheckResult")
    public void queryCardInfo(int deviceId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",deviceId);
        HtlRetrofit.getInstance().getService(2).cardInfo(
                        hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardInfo -> {
                    if (cardInfo != null) {
                         cardDetailed = JSONObject.parseObject(String.valueOf(cardInfo), CardInfoBean.CardDetailed.class);
                        if (cardDetailed != null) {
                            cardDetailed.setColor(cardDetailed.getColor());
                            cardDetailed.setEngine_code(cardDetailed.getEngine_code());
                            cardDetailed.setSn_code(cardDetailed.getSn_code());
                            cardDetailed.setFrame_code(cardDetailed.getFrame_code());
                            cardDetailed.setName(cardDetailed.getName());
                            cardDetailed.setId(cardDetailed.getId());
                            cardDetailed.setIs_bind(cardDetailed.getIs_bind());
                            cardDetailed.setImei(cardDetailed.getImei());
                            int status = cardDetailed.getStatus();
                            Log.d("当前车辆状态==", cardDetailed.getStatus() + "");
                            Log.d("当前车辆Sn==", cardDetailed.getSn_code() + "");
                            Log.d("当前车辆电机号==", cardDetailed.getFrame_code() + "");
                            Log.d("当前车辆车架号==", cardDetailed.getEngine_code() + "");
                            Log.d("当前车辆名字==", cardDetailed.getName() + "");
                            if (status == 0) {
                                btnCardStatus.setText("车辆锁定");
                            } else if (status == 1) {
                                btnCardStatus.setText("车辆解锁");
                            } else if (status == 2) {
                                btnCardStatus.setText("车辆故障");
                            }
                           // btnCardStatus.setText(cardDetailed.getStatus() == 1 ? "车辆解锁" : "车辆锁定");
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            // mFilePath即是拍照完成后的图片
            // 这里可以进入裁剪页面了
            CardSetImgFragment fragment = CardSetImgFragment.newInstance();
            if (fragment != null) {
                fragment.onCameraResult(data);
            }
        } else if (requestCode == REQUEST_CODE_CROP && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                //  Bitmap map = BitmapFactory.decodeFile(picturePath);
                headImg.setBitmap(bitmap);
                //异步解析
//                asyncThread(() -> {
//                    final String result = CodeUtils.parseCode(bitmap);
////                Bitmap map1 = BitmapFactory.decodeFile(result);
////                headImg.setBitmap(map1);
//                    runOnUiThread(() -> {
//                        LogUtils.d("result:" + result);
//                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//                    });
//
//                });

            } catch (Exception e) {
                e.printStackTrace();
            }


//            CardSetImgFragment fragment = CardSetImgFragment.newInstance();
//            if (fragment != null) {
//                fragment.onCrop(data);
//            }
        }
    }
    private void showBottomCardImgSheetDialog() {
        CardSetImgFragment fragment = CardSetImgFragment.newInstance();
        fragment.show(getSupportFragmentManager(), CardSetImgFragment.class.getSimpleName());
    }
    private void showBottomCardLockSheetDialog(String status) {
        CardLockFragment fragment = CardLockFragment.newInstance(status);
        fragment.show(getSupportFragmentManager(), CardLockFragment.class.getSimpleName());
    }

}
