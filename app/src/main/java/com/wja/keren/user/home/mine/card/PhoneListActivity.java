package com.wja.keren.user.home.mine.card;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wja.keren.R;
import com.wja.keren.user.home.base.BaseActivity;
import com.wja.keren.user.home.mine.Contents;
import com.wja.keren.user.home.mine.ContentsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PhoneListActivity extends BaseActivity {


    List<Contents> mContentsList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private void readContacts() {
        Cursor cursor = null;
        /*用ContentResolver访问内容提供器中的共享数据*/
        /*ContactsContract.CommonDataKinds.Phone.CONTENT_URI就是通过Uri.parse()方法解析出来的结果*/
        /*cursor保存着联系人信息*/
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            /*多个cursor，每个里面存了名字和电话*/
            while (cursor.moveToNext()) {
                /*联系人名*/
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                /*电话*/
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mContentsList.add(new Contents(displayName, number));
            }
        }
        ContentsAdapter contentsAdapter = new ContentsAdapter(mContentsList,this);
        recyclerView.setAdapter(contentsAdapter);
        cursor.close();
    }

    /**
     * 请求权限后
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_phone;
    }

    @Override
    protected void init() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //判断用户是否已经授权给我们了 如果没有，调用下面方法向用户申请授权，之后系统就会弹出一个权限申请的对话框
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,new String[]{Manifest.permission.READ_CONTACTS},1);
        } else {
            readContacts();
        }
        /*权限*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            /*请求权限*/
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
    }
}
