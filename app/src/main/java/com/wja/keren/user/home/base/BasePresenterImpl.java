package com.wja.keren.user.home.base;

import android.content.Context;


public abstract class BasePresenterImpl< V extends BaseView> implements BasePresenter{



    protected Context context;
    protected V  view = null;


    public BasePresenterImpl(Context context) {
        this.context = context;
    }

    public void attachView(BaseView view){
        this.view = (V)view;
    }

    public void detachView()
    {
        this.view = null;
    }



}
