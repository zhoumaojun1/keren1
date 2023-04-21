package com.wja.keren.user.home.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.wja.keren.R;
import com.wja.keren.user.home.lifecycle.HomeLifecycle;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 1.直接加上个abstract来修饰， 变成抽象类， 这样就不能通过new的方式来实现BaseFragment,保证他是一个纯粹的基类。
 * 2.实现了BaseView接口， 然后重写了showToast等方法，子类就不需要多次重复的写这些方法了。
 * 这里有一个 抽象方法 createPresenter，返回的是T，这个是泛型，T extends BasePresenter
 * 就是BasePresenter的子类，需要在子类实现，我们在onCreateView里面调用后拿到对象，
 * 然后通过mPresenter.attachView(this);绑定了presenter和view
 * @param <T>
 */
public abstract class BaseActivity<  T extends  BasePresenter> extends AppCompatActivity implements BaseView {
    private Unbinder unbinder;
    static LinkedList<BaseActivity> activityList = new LinkedList <>();

    protected T presenter;

    protected ProgressDialog dialog;
    private HomeLifecycle mHomeLifecycle;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutResourceId());
        unbinder = ButterKnife.bind(this);

        init();
        mHomeLifecycle = new HomeLifecycle(this);
        getLifecycle().addObserver(mHomeLifecycle);
        activityList.addLast(this);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
        activityList.removeLast();
    }
    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
        unbinder.unbind();
        if(presenter != null)
            presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if(!popFragment()) {
            super.onBackPressed();
        }
    }
    public boolean popFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            transaction.commit();
            return true;
        }
        return false;

    }

    public void pushFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.root_view,fragment,fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void setRightText(String text) {
        TextView tvRight = findViewById(R.id.tv_toolbar_right);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(this::onRight);
        }

    }

    public void setRightText(int text) {
        TextView tvRight = findViewById(R.id.tv_toolbar_right);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setTextColor(getColor(R.color.color_1FC8A9));
            tvRight.setOnClickListener(this::onRight);
        }

    }

    public void setLeftIcon(int resId) {
        ImageView ivRight = findViewById(R.id.iv_toolbar_left);
        if(ivRight != null) {
            ivRight.setVisibility(View.VISIBLE);

            ivRight.setOnClickListener(this::onLeft);
            ivRight.setBackgroundResource(resId);
        }
    }
    public void setCenterIcon(int resId) {
        ImageView ivRight = findViewById(R.id.iv_center);
        if(ivRight != null) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setBackgroundResource(resId);
        }
    }

    public void setRightIcon(int resId) {
        ImageView ivRight = findViewById(R.id.iv_toolbar_right);
        if(ivRight != null) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setOnClickListener(this::onRight);
            ivRight.setBackgroundResource(resId);
        }

    }

    public void setRightTextColor(int colorId) {
        TextView tvRight = findViewById(R.id.tv_toolbar_right);
        if(tvRight != null){
            tvRight.setTextColor(getColor(colorId));
        }
    }

    public void setLeftText(String text) {
        TextView tvRight = findViewById(R.id.tv_toolbar_left);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(this::onLeft);
        }

    }

    public void setLeftText(int text) {
        TextView tvRight = findViewById(R.id.tv_toolbar_left);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(this::onLeft);
        }

    }

    public void setToolbarTitle(String title) {
        TextView tv = findViewById(R.id.tv_toolbar_title);
        if(tv != null)
            tv.setText(title);

    }

    public void setToolbarTitle(int resid) {
        TextView tv = findViewById(R.id.tv_toolbar_title);
        if(tv != null)
            tv.setText(resid);
    }

    public void onRight(View view) {

    }

    public void onLeft(View view) {
        finish();
    }

    protected void setFullScreen()
    {
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void showError(int resId) {
        Toast.makeText(this,resId,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this,resId,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showDialog(String message) {

        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

    }

    @Override
    public void dismissDialog() {
        if(dialog != null)
            dialog.dismiss();
        dialog = null;
    }




    protected abstract int getLayoutResourceId();
    protected abstract void init();



}
