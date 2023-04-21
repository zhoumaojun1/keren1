package com.wja.keren.user.home.mine.card;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.CardInfoBean;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.bean.CardShareBean;
import com.wja.keren.user.home.bean.UserShareCardListBean;
import com.wja.keren.user.home.main.MainContact;
import com.wja.keren.user.home.main.MainPresenter;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UseCardShareActivity extends BaseActivity<MainContact.Presenter> implements MainContact.View {
    private static final String TAG = UseCardShareActivity.class.getName();
    Adapter adapter;
    protected final int REQUEST_PERMISSION_ADDRESSBOOK = 10;
    protected final int REQUEST_ADDRESSBOOK = 11;
    @BindView(R.id.card_share_recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_card_ble_name)
    TextView tvCardName;

    @BindView(R.id.tv_card_color_value)
    TextView tvCardColor;

    @BindView(R.id.tv_card_sn_value)
    TextView tvCardSn;

    @BindView(R.id.tv_card_frame_code_value)
    TextView tvCardFrame;

    @BindView(R.id.tv_dian_ji_code_value)
    TextView tvDianji;

    @BindView(R.id.tv_card_share_number)
    TextView tvShareNumber;

    @BindView(R.id.iv_use_share_card_fail)
    ImageView ivUse;

    @BindView(R.id.tv_share_other)
    TextView tvShareOther;

    CardListBean.CardList cardListBean =new CardListBean.CardList();
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_use_card_share;
    }

    CardInfoBean.CardDetailed cardDetailed;

    @Override
    protected void init() {
        setLeftIcon(R.mipmap.card_back);
        setToolbarTitle(R.string.mine_card_share);
        presenter = new MainPresenter(this);
        presenter.attachView(this);
        Intent intent = this.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (null != bundle.getSerializable("cardListBean")) {
                cardListBean = ( CardListBean.CardList) bundle.getSerializable("cardListBean");
                initCardInfo(cardListBean);
            }

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.CardShareAgree(cardListBean.getId());
        presenter.CardShareList(cardListBean.getId(), 1);

        if (adapter == null) {
            adapter = new Adapter();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(UseCardShareActivity.this, LinearLayoutManager.VERTICAL, false));


    }

    protected void initCardInfo(CardListBean.CardList cardDetailed) {
        tvCardName.setText(cardDetailed.getName());
        tvCardColor.setText(cardDetailed.getColor());
        tvCardSn.setText(cardDetailed.getSn_code());
        tvCardFrame.setText(cardDetailed.getFrame_code());
        tvDianji.setText(cardDetailed.getEngine_code());
    }


    @OnClick({R.id.rl_sear_phone_share, R.id.rl_sear_phone_list_share})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.rl_sear_phone_share:
                IntentUtil.get().goActivity(this, AddShareAccountActivity.class);
                break;
            case R.id.rl_sear_phone_list_share:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_PERMISSION_ADDRESSBOOK);
                } else {
                    // 跳转到联系人界面
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.PICK");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setType("vnd.android.cursor.dir/phone_v2");
                    startActivityForResult(intent, REQUEST_ADDRESSBOOK);
                }
                break;


            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_ADDRESSBOOK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 跳转到联系人界面
                Intent intent = new Intent();
                intent.setAction("android.intent.action.PICK");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setType("vnd.android.cursor.dir/phone_v2");
                startActivityForResult(intent, REQUEST_ADDRESSBOOK);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("R.string.warm_tips")
                        .setMessage("您已经拒绝了获取联系人权限，请手动设置")
                        .setNegativeButton("不了", null)
                        .show();
            }
        }
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择联系人的回调
        if (requestCode == REQUEST_ADDRESSBOOK) {
            try {
                if (data != null) {
                    Uri uri = data.getData();
                    String phoneNum = null;
                    String contactName = null;
                    // 创建内容解析者
                    ContentResolver contentResolver = getContentResolver();
                    Cursor cursor = null;
                    if (uri != null) {
                        cursor = contentResolver.query(uri, new String[]{"display_name", "data1"}, null, null, null);
                    }
                    while (cursor.moveToNext()) {
                        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    cursor.close();
                    if (phoneNum != null) {
                        phoneNum = phoneNum.replaceAll("-", " ");
                        phoneNum = phoneNum.replaceAll(" ", "");
                        IntentUtil.get().goActivityResult(this, CardShareInviteActivity.class, phoneNum);
                    }

                } else {

                }
            } catch (Exception e) {
                Toast.makeText(this, "获取联系人失败", Toast.LENGTH_LONG).show();
            } //异常
        }
    }

    @Override
    public void showCardInfo(CardInfoBean.CardDetailed cardDetailed) {
       // initCardInfo(cardDetailed);
    }


    @Override
    public void showCardShareList(CardShareBean.CardShare cardShare) {
        //  System.out.println(TAG+"用车分享 == " +userHead.getList().size()+"---");
        if (cardShare == null) {
            return;
        }
        if (null != cardShare.getList() && cardShare.getList().size() > 0) {
            adapter.refresh(cardShare.getList());
            ivUse.setVisibility(View.INVISIBLE);
            tvShareOther.setVisibility(View.INVISIBLE);
            tvShareNumber.setText(cardShare.getTotal() + "");
        } else {
            ivUse.setVisibility(View.VISIBLE);
            tvShareOther.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void updateShareNumberList() {

    }

    @Override
    public void showCardShareUserNumber(UserShareCardListBean.UserShareCard cardRunBean) {

        tvShareNumber.setText(cardRunBean.getTotal() + "");
    }

    class Adapter extends RecyclerView.Adapter {
        private final List<CardShareBean.CardShare.CardInfo> scanResultList = new ArrayList<>();


        public void refresh(List<CardShareBean.CardShare.CardInfo> userHead) {
            this.scanResultList.clear();
            this.scanResultList.addAll(userHead);
            notifyDataSetChanged();
        }

        @androidx.annotation.NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(UseCardShareActivity.this).inflate(R.layout.activity_share_device_to_user_item, viewGroup, false);
            view.setOnClickListener(v -> {
                int tag = (int) view.getTag();
                if (tag==1){
                    presenter.CancelCardShare(1);
                }else if (tag ==2){
                    presenter.CancelCardShare(1);
                }

            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int i) {

            TextView tvUserNick = viewHolder.itemView.findViewById(R.id.tv_user_nick);
            TextView tvUserPhone = viewHolder.itemView.findViewById(R.id.tv_user_phone);
            TextView tvCardPairStatus = viewHolder.itemView.findViewById(R.id.tv_card_share_status);

            tvUserNick.setText(scanResultList.get(i).getName());
            tvUserPhone.setText(scanResultList.get(i).getPhone());
            if (scanResultList.get(i).getStatus() == 1) {
                tvCardPairStatus.setText("待接受");
                tvCardPairStatus.setTag(1);
            } else if (scanResultList.get(i).getStatus() == 2) {
                tvCardPairStatus.setText("已接受");
                tvCardPairStatus.setTag(2);
            } else if (scanResultList.get(i).getStatus() == 3) {
                tvCardPairStatus.setText("拒绝分享");
                tvCardPairStatus.setTag(3);
            }
            viewHolder.itemView.setTag(i);

        }

        @Override
        public int getItemCount() {
            return scanResultList.size();
        }
    }

}
