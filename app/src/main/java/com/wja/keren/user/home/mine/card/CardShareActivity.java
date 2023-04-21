package com.wja.keren.user.home.mine.card;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.zxing.CustomCaptureActivity;
import com.wja.keren.zxing.util.IntentUtil;

import java.util.ArrayList;

public class CardShareActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private int activeColor = Color.parseColor("#7ACC00");
    private int normalColor = Color.parseColor("#86898E");

    private int activeSize = 14;
    private int normalSize = 12;

    CardGarageFragment testFragment;
    CardShareFragment testFragment1;
    CardShareAgreeFragment testFragment2;
    private TabLayoutMediator mediator;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected void init() {
        setToolbarTitle(R.string.card_che_ku_manager);
        setLeftIcon(R.mipmap.card_back);
        setRightIcon(R.mipmap.card_share_add);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        final String[] tabs = new String[]{"车库", "分享", "接受"};

        //禁用预加载
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //Adapter
        viewPager2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                // 所以不需要考虑复用的问题
                if (position == 0) {
                    testFragment = CardGarageFragment.newInstance(tabs[position]);
                    return testFragment;
                } else if (position == 1) {
                    testFragment1 = CardShareFragment.newInstance(tabs[position]);
                    return testFragment1;
                } else if (position == 2) {
                    testFragment2 = CardShareAgreeFragment.newInstance(tabs[position]);
                    return testFragment2;
                } else {
                    return testFragment;
                }

            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });
        //viewPager 页面切换监听监听
        viewPager2.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //这里可以自定义TabView
                TextView tabView = new TextView(CardShareActivity.this);

                int[][] states = new int[2][];
                states[0] = new int[]{android.R.attr.state_selected};
                states[1] = new int[]{};

                int[] colors = new int[]{activeColor, normalColor};
                ColorStateList colorStateList = new ColorStateList(states, colors);
                tabView.setText(tabs[position]);
                tabView.setTextSize(normalSize);
                tabView.setTextColor(colorStateList);

                tab.setCustomView(tabView);
            }
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();
    }

    @Override
    public void onRight(View view) {
        super.onRight(view);
        IntentUtil.get().goActivity(this, CustomCaptureActivity.class);
    }

    private ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    tabView.setTextSize(activeSize);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        mediator.detach();
        viewPager2.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }
}
