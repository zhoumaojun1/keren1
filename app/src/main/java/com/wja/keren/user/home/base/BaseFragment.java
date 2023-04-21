package com.wja.keren.user.home.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wja.keren.R;

import butterknife.ButterKnife;


/**
 * 1.直接加上个abstract来修饰， 变成抽象类， 这样就不能通过new的方式来实现BaseFragment,保证他是一个纯粹的基类。
 * 2.实现了BaseView接口， 然后重写了showToast等方法，子类就不需要多次重复的写这些方法了。
 * 这里有一个 抽象方法 createPresenter，返回的是T，这个是泛型，T extends BasePresenter
 * 就是BasePresenter的子类，需要在子类实现，我们在onCreateView里面调用后拿到对象，
 * 然后通过mPresenter.attachView(this);绑定了presenter和view
 * @param <T>
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {


    protected  final String TAG = this.getClass().getSimpleName();

    protected T presenter;
    protected View rootView;


    protected abstract int getLayoutId();
    protected abstract T createPresenter();
    protected abstract void init();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);

        if(rootView == null)
        {
            rootView  =  inflater.inflate(getLayoutId(),container,false);
            ButterKnife.bind(this, rootView);
            presenter = createPresenter();
            //然后通过mPresenter.attachView(this);绑定了presenter和view
            presenter.attachView(this);
//
//            rootView.setOnTouchListener((v,event)->{
//                v.performClick();
//                return true;
//            });
        }



        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        rootView = null;
        if(presenter != null) {
            presenter.detachView();
        }
        presenter = null;
    }

    public void setRightIcon(int resId) {
        ImageView ivRight = rootView.findViewById(R.id.iv_toolbar_right);
        if(ivRight != null) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setOnClickListener(this::onRight);
            ivRight.setBackgroundResource(resId);
        }

    }


    public void setRightText(String text) {
        TextView tvRight = rootView.findViewById(R.id.tv_toolbar_right);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(this::onRight);
        }

    }

    public void setRightText(int text) {
        TextView tvRight = rootView.findViewById(R.id.tv_toolbar_right);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(this::onRight);
        }
    }

    public void setRightTextColor(int resId) {
        TextView tvRight = rootView.findViewById(R.id.tv_toolbar_right);
        if(tvRight != null)
            tvRight.setTextColor(getResources().getColor(resId));

    }

    public void setLeftIcon(int resId) {
        ImageView ivRight = rootView.findViewById(R.id.iv_toolbar_left);
        if(ivRight != null) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setOnClickListener(this::onLeft);
            ivRight.setBackgroundResource(resId);
        }

    }


    public void setLeftText(String text) {
        TextView tvRight = rootView.findViewById(R.id.tv_toolbar_left);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);

            tvRight.setText(text);
            tvRight.setOnClickListener(this::onLeft);
        }
    }

    public void setLeftText(int text) {
        TextView tvRight = rootView.findViewById(R.id.tv_toolbar_left);
        if(tvRight != null) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(this::onLeft);
        }
    }

    public void setToolbarTitle(String title) {
        TextView tv = rootView.findViewById(R.id.tv_toolbar_title);
        if(tv != null)
            tv.setText(title);
    }

    public void setToolbarTitle(int resid) {
        TextView tv = rootView.findViewById(R.id.tv_toolbar_title);
        if(tv != null)
            tv.setText(resid);
    }

    public void onRight(View view) {

    }

    public void onLeft(View view) {
        BaseActivity activity = (BaseActivity) getActivity();
        if(activity != null)
            activity.popFragment();
    }


    @Override
    public void showError(int resId) {
        Toast.makeText(getContext(),resId,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getContext(),resId,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();

    }


    @Override
    public void showDialog(String message){

    }
    @Override
    public void dismissDialog() {

    }
}
