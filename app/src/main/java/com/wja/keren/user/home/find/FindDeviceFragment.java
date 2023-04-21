package com.wja.keren.user.home.find;

import android.view.View;


import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseFragment;



public class FindDeviceFragment extends BaseFragment<FindDevice.Presenter> implements FindDevice.View {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected FindDevice.Presenter createPresenter() {
        return new FindDevicePresenter(getContext());
    }

public  FindDeviceFragment() {

}
    @Override
    protected void init() {
        setToolbarTitle(R.string.card_find);

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
    public void onRight(View view) {
        if(getActivity() != null) {
//            Intent intent = new Intent(getActivity(),MessageSetActivity.class);
//            getActivity().startActivity(intent);
        }
    }

    @Override
    public void showError(String error) {
        super.showError(error);

    }


    @Override
    public void onUpdateMessage(String list) {

    }

    @Override
    public void refreshMessage() {

    }
}
