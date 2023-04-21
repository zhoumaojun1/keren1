package com.wja.keren.user.home.mine.card;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.bean.UserShareCardListBean;
import com.wja.keren.user.home.main.MainContact;
import com.wja.keren.user.home.main.MainPresenter;
import com.wja.keren.user.home.mine.card.dialog.CardUnbingFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class CardInfoActivity extends BaseActivity<MainContact.Presenter> implements  MainContact.View{
    @BindView(R.id.tv_card_model_type)
    TextView tvModel;

    @BindView(R.id.tv_card_imei)
    TextView tvImei;

    @BindView(R.id.tv_card_sn)
    TextView tvSn;

    @BindView(R.id.tv_card_frame_no)
    TextView tvFrameNo;

    @BindView(R.id.tv_card_motor_number)
    TextView tvMotor;

    @BindView(R.id.tv_card_central_control_version)
    TextView tvControlVersion;

    @BindView(R.id.tv_card_buy_position)
    TextView tvBuyPosition;

    @BindView(R.id.tv_card_buy_time)
    TextView tvBuyTime;

    @BindView(R.id.tv_card_register_time)
    TextView tvRegisTime;


    @BindView(R.id.tv_card_three_package_time)
    TextView tvThreePacTime;

    @BindView(R.id.tv_update_version)
    TextView tvVersion;
    CardListBean.CardList cardListBean =new CardListBean.CardList();
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_card_info;
    }

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_info);
        presenter = new MainPresenter(this);
        presenter.attachView(this);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (null != bundle.getSerializable("cardListBean")) {
                cardListBean = ( CardListBean.CardList) bundle.getSerializable("cardListBean");
                initCardInfo(cardListBean);
            }

        }
    }

    void initCardInfo(CardListBean.CardList cardDetailed) {
        tvModel.setText(cardDetailed.getName());
        tvSn.setText(cardDetailed.getSn_code());
        tvFrameNo.setText(cardDetailed.getFrame_code());
        tvMotor.setText(cardDetailed.getEngine_code());

        tvBuyTime.setText(cardDetailed.getEngine_code());
        tvRegisTime.setText(cardDetailed.getEngine_code());
        tvThreePacTime.setText(cardDetailed.getEngine_code());
    }
    @OnClick({ R.id.btn_remove_bind, R.id.tv_update_version})
    void onClickBtn(View v) {
        switch (v.getId()) {

            case R.id.btn_remove_bind:
                showBottomCardUnBindSheetDialog();
                break;

            case R.id.tv_update_version:

                break;
            default:
                break;
        }
    }

    private void showBottomCardUnBindSheetDialog() {
        CardUnbingFragment fragment = CardUnbingFragment.newInstance();
        fragment.show(getSupportFragmentManager(), CardUnbingFragment.class.getSimpleName());
    }



    @Override
    public void showCardInfo(CardInfoBean.CardDetailed cardDetailed) {
      //  initCardInfo(cardDetailed);
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
