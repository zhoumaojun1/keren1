package com.wja.keren.user.home.base;

public interface BaseView {
    public void showError(int resId);
    public void showError(String error);
    public void showMessage(int resId);
    public void showMessage(String message);
    public void showDialog(String message);
    public void dismissDialog();
}
