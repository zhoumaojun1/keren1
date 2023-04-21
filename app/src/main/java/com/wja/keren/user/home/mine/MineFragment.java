package com.wja.keren.user.home.mine;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseFragment;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.UserInfoBean;
import com.wja.keren.user.home.home.CardAdapter;
import com.wja.keren.user.home.mine.card.CardShareActivity;
import com.wja.keren.user.home.mine.card.OnlineRepairActivity;
import com.wja.keren.user.home.mine.card.RunRecordActivity;
import com.wja.keren.user.home.network.HtlRetrofit;
import com.wja.keren.user.home.view.AvatarView;
import com.wja.keren.zxing.CustomCaptureActivity;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MineFragment extends BaseFragment<MineContact.Presenter> implements MineContact.View{

    @BindView(R.id.iv_user_set)
    ImageView ivUserSet;

    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;

    @BindView(R.id.iv_change_user_head)
    AvatarView headImg;

    @BindView(R.id.gridView_view)
    GridView gridView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MineContact.Presenter createPresenter() {
        return new MinePresenter(getContext());
    }

    @Override
    protected void init() {
        presenter = new MinePresenter(getContext());
        presenter.attachView(this);
        presenter.getUserInfo();
        cardGarage();

    }

    @SuppressLint("CheckResult")
    public void cardGarage() {
        HtlRetrofit.getInstance().getService(2).cardList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result != null) {
                        CardListBean cardList = JSONObject.parseObject(String.valueOf(result), CardListBean.class);
                        if (cardList != null && cardList.getData() != null && cardList.getData().size() > 0) {
                            ArrayList<CardListBean.CardList> myCardList = new ArrayList<>();
                            for (CardListBean.CardList cardListBean : cardList.getData()) {
                                myCardList.add(cardListBean);
                                showCardList(myCardList);

                            }
                        } else {

                        }
                    }


                });
    }
    public MineFragment(){}

    @Override
    public void showLogout(boolean success) {

    }
    private void showCardList(List<CardListBean.CardList> cardLists) {
        Log.d("showCardList ==",cardLists.size()+"");


        int size = cardLists.size();
        int length = 200;
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int gridviewWidth = (int) (size * (length + 1) * density);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        gridView.setLayoutParams(params); // 重点
//        gridView.setColumnWidth(gridviewWidth); // 重点
        gridView.setHorizontalSpacing(25); // 间距
//        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
      //  gridView.setNumColumns(size); // 重点
//        gridView.setVerticalScrollBarEnabled(false);
          CardAdapter adapter = new CardAdapter(getActivity().getApplicationContext(),
                cardLists);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                IntentUtil.get().goActivity(getActivity(),CardShareActivity.class);
                adapter.getItem(position);
            }
        });
    }

    UserInfoBean.User userInfo =new UserInfoBean.User();
    @Override
    public void showUserName(UserInfoBean.User userInfoBean) {
        userInfo = userInfoBean;
        userInfo.setName(userInfoBean.getName());
        userInfo.setPhone(userInfoBean.getPhone());
        userInfo.setId(userInfoBean.getId());
        if (!TextUtils.isEmpty(userInfoBean.getPhone()) && userInfoBean.getPhone().length() > 6) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < userInfoBean.getPhone().length(); i++) {
                char c = userInfoBean.getPhone().charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            tvUserPhone.setText(sb);
            if (null != userInfo.getPicture()) {
                Bitmap map = BitmapFactory.decodeFile(userInfo.getPicture());
                headImg.setBitmap(map);
            }

        }
    }
    @OnClick({R.id.iv_user_set,R.id.iv__mine_card_img,R.id.iv__mine_card_add,R.id.rl_mine_shop,R.id.rl_mine_msg_notify,R.id.rl_mine_on_line_request,R.id.rl_mine_run_report,R.id.rl_mine_service_help})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.iv_user_set:
                IntentUtil.get().goActivity(getActivity(), MineSetActivity.class);
                break;
            case R.id.iv__mine_card_add:

                IntentUtil.get().goActivity(getActivity(), CustomCaptureActivity.class);
                break;
            case R.id.rl_mine_shop:

                break;
            case R.id.iv__mine_card_img:
                IntentUtil.get().goActivity(getActivity(), CardShareActivity.class);
                break;


            case R.id.rl_mine_msg_notify:

                break;
            case R.id.rl_mine_on_line_request:
                IntentUtil.get().goActivity(getActivity(), OnlineRepairActivity.class);
                break;
            case R.id.rl_mine_run_report:
                IntentUtil.get().goActivity(getActivity(), RunRecordActivity.class);


                break;
            case R.id.rl_mine_service_help:
                IntentUtil.get().goActivity(getActivity(), HelpAndServiceActivity.class);
                break;

            default:
                break;
        }
    }

}
