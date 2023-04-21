package com.wja.keren.user.home.home;

import static com.wja.keren.user.home.main.HomeTabActivity.RC_CAMERA;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.bumptech.glide.Glide;
import com.king.zxing.CameraScan;
import com.king.zxing.util.CodeUtils;
import com.king.zxing.util.LogUtils;
import com.wja.keren.R;
import com.wja.keren.user.home.Config;
import com.wja.keren.user.home.base.BaseFragment;
import com.wja.keren.user.home.base.ViewHolder;
import com.wja.keren.user.home.bean.BatteryBean;
import com.wja.keren.user.home.bean.CardListBean;
import com.wja.keren.user.home.mine.card.CardGarageFragment;
import com.wja.keren.user.home.mine.card.CardLocationActivity;
import com.wja.keren.user.home.mine.card.CardSettingActivity;
import com.wja.keren.user.home.mine.card.FenceActivity;
import com.wja.keren.user.home.mine.card.HelpServiceActivity;
import com.wja.keren.user.home.mine.card.OnlineRepairActivity;
import com.wja.keren.user.home.mine.card.RunNavigationActivity;
import com.wja.keren.user.home.mine.card.RunRecordActivity;
import com.wja.keren.user.home.mine.card.UseCardShareActivity;
import com.wja.keren.user.home.util.AnimationUtils;
import com.wja.keren.user.home.util.FormatUtil;
import com.wja.keren.user.home.view.BatteryView;
import com.wja.keren.user.home.view.CircleProgressView;
import com.wja.keren.user.home.view.ToastUtils;
import com.wja.keren.zxing.CustomCaptureActivity;
import com.wja.keren.zxing.util.CommonUtil;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeFragment extends BaseFragment<HomeContact.Presenter> implements HomeContact.View, WeatherSearch.OnWeatherSearchListener {
    private static final String TAG = HomeFragment.class.getName();
    public static final int REQUEST_CODE_SCAN = 0X86;
    public static final int REQUEST_CODE_PHOTO = 0X02;

    private Toast toast;
    TextView tvCurrentTmp;

    @BindView(R.id.tv_current_weather_01)
    TextView tvCurrentWeather;

    @BindView(R.id.tv_current_wind_speed_01)
    TextView tvCurrentWindSpeed;

    @BindView(R.id.horizontalBattery)
    BatteryView horizontalBattery;
    @BindView(R.id.iv_add_card)
    ImageView ivAddCard;
    @BindView(R.id.tv_unbound_card)
    TextView tvUnbindCard;

    @BindView(R.id.ivMenuSetting)
    ImageView ivMenuSetting;

    @BindView(R.id.iv_card_open)
    ImageView ivOpenCard;

    @BindView(R.id.iv_card_unbind_bg)
    ImageView ivCardUnbindBg;

    @BindView(R.id.iv_left_slide_menu)
    ImageView ivLeftSlideMenu;

    @BindView(R.id.iv_right_slide_menu)
    ImageView ivRightSlideMenu;

    @BindView(R.id.tvBatteryRemaining)
    TextView tvBatteryRemaining;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;

    @BindView(R.id.iv_card_share)
    ImageView ivShareCard;

    //    @BindView(R.id.iv_card_round)
//    ArcChart iv_card_round;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;

    @BindView(R.id.root_view)
    ConstraintLayout root_view;
    @BindView(R.id.tab_bottom_menu)
    ConstraintLayout csBottomBg;
//    @BindView(R.id.recycler_view)
//    RecyclerView gridView;

    @BindView(R.id.ll_one_view)
    LinearLayout llOneView;

    @BindView(R.id.ll_two_view)
    LinearLayout llTwoView;

    @BindView(R.id.ll_add_view)
    LinearLayout llAddView;

    @BindView(R.id.iv_add_card_01)
    ImageView iv_add_card_01;
    @BindView(R.id.progress_circular)
    CircleProgressView circleProgressView;
    @BindView(R.id.progress_tv1)
    TextView progressTv;

    @BindView(R.id.viewpager2)
     ViewPager2 viewPager2;


    private int lastPosition;                           //记录轮播图最后所在的位置
    private List<Integer> colors = new ArrayList<>();   //轮播图的颜色
    Adapter adapter;
    private boolean isCardTakeOn = false;
    private int power;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    public HomeFragment() {


    }

    boolean isOnClickScreen = false;//是否点击屏幕任意处

    public static HomeFragment newInstance(String param1, int param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("isTag", param1);
        args.putInt("isTag", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected HomeContact.Presenter createPresenter() {
        return new HomePresenter(getContext());
    }

    class Adapter extends RecyclerView.Adapter {

        private final List<CardListBean.CardList> allList = new ArrayList<>();


        public void refresh( List<CardListBean.CardList> allList) {
            this.allList.clear();
            this.allList.addAll(allList);
            notifyDataSetChanged();
        }

        @androidx.annotation.NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int position) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main_item, viewGroup, false);
            view.setOnClickListener(v -> {
                int tag = (int) view.getTag();
                CardListBean.CardList cardListBean=  allList.get(position);
                Intent intent =new Intent();
                Bundle bundle =new Bundle();
                bundle.putSerializable("cardListBean",cardListBean);
                intent.putExtras(bundle);
               // IntentUtil.get().goActivityResult(getActivity(), UseCardShareActivity.class, intent);
                IntentUtil.get().goActivityResult(getActivity(), CardSettingActivity.class, intent);

            });
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int position) {


            TextView tvCardName =   viewHolder.itemView.findViewById(R.id.tvCardName);
            ImageView ivCardImg = viewHolder.itemView.findViewById(R.id.ivCardIcon);
            CardListBean.CardList cardListBean = allList.get(position);
            tvCardName.setText(cardListBean.getName());
            Config.DEVICE_ID = cardListBean.getId();
            Config.DEVICE_NAME = cardListBean.getName();
//            Glide.with(getActivity())
//                    .load(allList.get(position).getPhoto())
//                    .into(ivCardImg);
            viewHolder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return allList.size();
        }
    }

    @Override
    protected void init() {

        AnimationUtils.slideToUp(getActivity(), csBottomBg);
        horizontalBattery.setColor(Color.WHITE);
        horizontalBattery.setPower(getActivity(), 85);
        tvBatteryRemaining.setText("100");
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
      //  horizontalScrollView.setVisibility(View.GONE);
        LinearLayout.LayoutParams paramsLlOne = new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        llOneView.setLayoutParams(paramsLlOne);
        LinearLayout.LayoutParams paramsLlTwo = new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsLlTwo.leftMargin = screenWidth; // 将第二个子 View 的左边距设置为屏幕宽度，使其隐藏在屏幕右侧
        llTwoView.setLayoutParams(paramsLlTwo);
        final int scrollWidth = screenWidth; // 每次滚动的距离为一个屏幕宽度
        // 这样就可以实现一个屏幕只显示一个子 View 的效果了。在滚动 HorizontalScrollView 时，会根据滚动的距离自动切换显示的子 View。重新生成
        horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollX < scrollWidth / 2) { // 滚动距离小于一个屏幕宽度的一半，显示第一个子 View
                    llOneView.setVisibility(View.VISIBLE);
                   llTwoView.setVisibility(View.INVISIBLE);
                } else { // 滚动距离大于等于一个屏幕宽度的一半，显示第二个子 View
                    llOneView.setVisibility(View.INVISIBLE);
                    llTwoView.setVisibility(View.VISIBLE);
                }
            }
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, 0, 100);

        WeatherSearchQuery mquery = new WeatherSearchQuery("深圳", WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch mweathersearch = null;
        try {
            mweathersearch = new WeatherSearch(getActivity());
        } catch (AMapException e) {
            throw new RuntimeException(e);
        }
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索


        if (adapter == null) {
            adapter = new Adapter();
        }
       // gridView.setAdapter(adapter);
      // gridView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
      //  initColors();
        //添加适配器
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(),ca);
//        viewPagerAdapter.refresh();



        //注册轮播图的滚动事件监听器
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //轮播时，改变指示点
                int current = position % 4;
                int last = lastPosition % 4;
                //    indicatorContainer.getChildAt(current).setBackgroundResource(R.drawable.bounder);
                //  indicatorContainer.getChildAt(last).setBackgroundResource(R.drawable.bounder1);
                lastPosition = position;
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            int isTag = getArguments().getInt("isTag", 0);

            if (isTag == 2) {
                horizontalBattery.setColor(Color.WHITE);
                horizontalBattery.setPower(getActivity(), 85);
                tvBatteryRemaining.setText("100");
                tvCardName.setVisibility(View.INVISIBLE);

                return;
            }
        }

        presenter.getCardList();
       // presenter.queryBattery(Config.DEVICE_ID,"Battery");
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();

                tvCurrentWeather.setText(getString(R.string.main_current_weather_01) + weatherlive.getWeather());
                tvCurrentTmp.setText(getString(R.string.main_current_temperature_01) + weatherlive.getTemperature() + "°C");
                tvCurrentWindSpeed.setText(getString(R.string.main_current_visibility_01) + weatherlive.getWindDirection() + "风 " + weatherlive.getWindPower() + "级");
            } else {
                ToastUtils.ToastMessage(getActivity(), "获取天气失败");
            }
        } else {
            ToastUtils.ToastMessage(getActivity(), "获取天气失败" + rCode);
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }

    List<CardListBean.CardList> cardListsList =new ArrayList<>();
    private void showCardList(List<CardListBean.CardList> cardLists, TextView textView) {
     cardListsList =cardLists;

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(),cardListsList);
      //  viewPagerAdapter.refresh(cardListsList);
        viewPager2.setAdapter(viewPagerAdapter);

        //设置轮播图初始位置在500,以保证可以手动前翻
        viewPager2.setCurrentItem(500);
        //最后所在的位置设置为500
        lastPosition = 500;
     //   adapter.refresh(cardLists);

//        int size = cardLists.size();
//        int length = 200;
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        //int gridviewWidth = (int) (size * (length + 1) * density);
//        int itemWidth = LinearLayout.LayoutParams.MATCH_PARENT;
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        gridView.setLayoutParams(params); // 重点
//        gridView.setColumnWidth(itemWidth); // 重点
//        gridView.setHorizontalSpacing(5); // 间距
//        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//        gridView.setNumColumns(size); // 重点
//        gridView.setVerticalScrollBarEnabled(false);
     //  CardAdapter adapter = new CardAdapter(getActivity().getApplicationContext(),
//                cardLists);
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                adapter.getItem(position);
//            }
//        });
    }






    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    horizontalBattery.setPower(getActivity(), power += 5);
                    if (power == 100) {
                        power = 0;
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private static OnClickCloseDialog onNetInfoCallback;

    public static class InfoService {
        public void setOnClickCloseDialog(OnClickCloseDialog onInfoFetchCallback) {
            onNetInfoCallback = onInfoFetchCallback;
        }
    }

    public interface OnClickCloseDialog {
        void closeDialog(boolean view);
    }

    @OnClick({R.id.ivMenuSetting, R.id.root_view, R.id.iv_right_slide_menu, R.id.iv_left_slide_menu, R.id.iv_card_open, R.id.iv_add_card, R.id.iv_card_location, R.id.iv_card_share, R.id.ll_service_point
            , R.id.ll_service_fence, R.id.ll_key_share, R.id.iv_add_card_01, R.id.ll_run_navigation, R.id.ll_card_location, R.id.ll_run_record})
    void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.ivMenuSetting:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");

                } else {
                    IntentUtil.get().goActivity(getActivity(), CardSettingActivity.class);
                }

                break;
            case R.id.root_view:
                if (isOnClickScreen == false) {
                    isOnClickScreen = true;
                    AnimationUtils.slideToUp(getActivity(), csBottomBg);
                    if (onNetInfoCallback != null) {
                        onNetInfoCallback.closeDialog(isOnClickScreen);
                    }
                } else {
                    isOnClickScreen = false;
                    if (onNetInfoCallback != null) {
                        onNetInfoCallback.closeDialog(false);
                    }
                    AnimationUtils.slideToDown(getActivity(), csBottomBg);
                }
                break;
            case R.id.iv_right_slide_menu:
                horizontalScrollView.arrowScroll(View.FOCUS_LEFT);

                break;
            case R.id.iv_left_slide_menu:
               horizontalScrollView.arrowScroll(View.FOCUS_RIGHT);

                break;
            case R.id.iv_add_card_01:

                checkCameraPermissions();
                break;
            case R.id.iv_card_open:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    openCard();
                }

                break;
            case R.id.iv_add_card:

                checkCameraPermissions();
                break;

            case R.id.iv_card_location:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    presenter.playCardSound(Config.DEVICE_ID,0 );
                    IntentUtil.get().goActivity(getActivity(), CardLocationActivity.class);
                }
                break;
            case R.id.iv_card_share:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    presenter.playCardSound(Config.DEVICE_ID,8 );
                    IntentUtil.get().goActivity(getActivity(), CardLocationActivity.class);
                }
                break;

            case R.id.ll_service_point:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    IntentUtil.get().goActivity(getActivity(), OnlineRepairActivity.class);
                }

                break;
            case R.id.ll_service_fence:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    IntentUtil.get().goActivity(getActivity(), FenceActivity.class);
                }

                break;
            case R.id.ll_key_share:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    presenter.playCardSound(Config.DEVICE_ID,2 );
                    // IntentUtil.get().goActivity(getActivity(), UseCardShareActivity.class);
                }
                break;
            case R.id.ll_run_navigation:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    IntentUtil.get().goActivity(getActivity(), RunNavigationActivity.class);
                }
                break;
            case R.id.ll_card_location:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    IntentUtil.get().goActivity(getActivity(), CardLocationActivity.class);
                }
                break;
            case R.id.ll_run_record:
                if (Config.DEVICE_SIZE == 0 || Config.DEVICE_SIZE == 10) {
                    ToastUtils.ToastMessage(getActivity(), "请绑定车辆后再进行操作");
                } else {
                    IntentUtil.get().goActivity(getActivity(), RunRecordActivity.class);
                }
                break;
            default:
                break;
        }
    }

   private void openCard(){
        ivOpenCard.setVisibility(View.INVISIBLE);
        circleProgressView.setVisibility(View.VISIBLE);
        progressTv.setVisibility(View.VISIBLE);
        circleProgressView.setStartAngle(Integer.valueOf("100"));
        circleProgressView.setProgressWidth(CommonUtil.dp2px(getActivity(), Integer.valueOf("15")));

        circleProgressView.setProgress(Integer.parseInt("100"), true);
        circleProgressView.setCap(true ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        circleProgressView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(float currentProgress) {
                if (currentProgress <= 99 && isCardTakeOn == false) {
                    progressTv.setText("启动中");
                } else if (currentProgress <= 99 && isCardTakeOn == true) {
                    progressTv.setText("关闭中");
                } else if (currentProgress == 100 && isCardTakeOn == false) {
                    progressTv.setText("启动成功");
                } else if (currentProgress == 100 && isCardTakeOn == true) {
                    progressTv.setText("关闭成功");
                }
                if (currentProgress == 100) {
                    if (Config.DEVICE_ID != 0) {
                        if (isCardTakeOn == false) {
                            isCardTakeOn = true;
                            presenter.openAndCloseCard(Config.DEVICE_ID, 1);
                        } else {
                            isCardTakeOn = false;
                            presenter.openAndCloseCard(Config.DEVICE_ID, 0);
                        }
                    }
                }


            }
        });
    }
    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions() {
        startHomeActivity();
    }

    private void startHomeActivity() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {//有权限
            Intent intent = new Intent(getActivity(), CustomCaptureActivity.class);
            if (getActivity() != null) {
                IntentUtil.get().goActivity(getActivity(), CustomCaptureActivity.class);
                //  startActivityForResult(intent,REQUEST_CODE_SCAN);
            }
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    RC_CAMERA, perms);
        }

    }

    @Override
    public void showDevice(List<CardListBean.CardList> list) {
        Log.d(TAG, "显示设备== " + list.size());
        if (list.size() > 0) {
            Config.DEVICE_SIZE = list.size();
            tvCardName.setVisibility(View.VISIBLE);
            if (!"".equals(Config.DEVICE_NAME)) {
                tvCardName.setText(Config.DEVICE_NAME);
            }
            horizontalScrollView.setVisibility(View.VISIBLE);
            ivCardUnbindBg.setVisibility(View.INVISIBLE);
            tvUnbindCard.setVisibility(View.INVISIBLE);
            ivAddCard.setVisibility(View.INVISIBLE);
            ivLeftSlideMenu.setVisibility(View.VISIBLE);
            ivRightSlideMenu.setVisibility(View.VISIBLE);
        } else {
            tvCardName.setVisibility(View.INVISIBLE);
            Config.DEVICE_SIZE = list.size();
            horizontalBattery.setVisibility(View.INVISIBLE);
            horizontalBattery.setVisibility(View.GONE);
            ivLeftSlideMenu.setVisibility(View.INVISIBLE);
            ivRightSlideMenu.setVisibility(View.INVISIBLE);
            ivCardUnbindBg.setVisibility(View.VISIBLE);
            tvUnbindCard.setVisibility(View.VISIBLE);
            ivAddCard.setVisibility(View.VISIBLE);
        }
        showCardList(list, tvCardName);
    }

    @Override
    public void updateOpenBg(int index) {
        ivOpenCard.setVisibility(View.VISIBLE);
        circleProgressView.setVisibility(View.INVISIBLE);
        progressTv.setVisibility(View.INVISIBLE);
        if (index == 1) {
            ivOpenCard.setBackgroundResource(R.mipmap.mian_card_success);
        } else {
            ivOpenCard.setBackgroundResource(R.mipmap.open_card);
        }

    }

    @Override
    public void showBattery(BatteryBean.Battery battery) {
        tvBatteryRemaining.setText(battery.getVal());

    }

    @Override
    public void updateCushionRebound() {

    }

    @Override
    public void updateHornFindCard() {

    }

    @Override
    public void updatePlayCardSound() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mHandler != null) {
//            mHandler.removeCallbacksAndMessages(null);
//            mHandler = null;
//        }
    }


}
