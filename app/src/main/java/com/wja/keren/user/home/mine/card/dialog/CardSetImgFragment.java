package com.wja.keren.user.home.mine.card.dialog;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_PICTURES;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.king.zxing.util.CodeUtils;
import com.king.zxing.util.LogUtils;
import com.wja.keren.R;
import com.wja.keren.user.home.device.DeviceBindFragment;
import com.wja.keren.user.home.login.MainBottomFragment;
import com.wja.keren.user.home.util.AnimationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CardSetImgFragment extends BottomSheetDialogFragment {
    private static final String TAG = MainBottomFragment.class.getName();
    private String picturePath;
    //载箭的路径
    private String cropPath;

    boolean isAlbum;
    public static final int REQUEST_CODE_PHOTO = 0X02;
    public static final int REQUEST_CODE_CAMERA = 222;
    public static final int REQUEST_CODE_CROP = 223;
    boolean isOnClickScreen = false;//是否点击屏幕任意处
    public static CardSetImgFragment newInstance() {
        CardSetImgFragment fragment = new CardSetImgFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }
    private static OnClickCloseDialog onDismissCallback;
    public static class InfoService {


        public void setOnClickCloseDialog(OnClickCloseDialog onDismissBack) {
            onDismissCallback = onDismissBack;
        }

    }

    public interface  OnClickCloseDialog{
        void closeDialog(View view);
    }




    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogStyle);
        View  view = View.inflate(getContext(), R.layout.dialog_card_set_img, null);
        ImageView btnOpenBle = view.findViewById(R.id.iv_delete_right);
        TextView tvZoomPhoto = view.findViewById(R.id.tv_zoom_photo);
        TextView tvSelectPhoto = view.findViewById(R.id.tv_select_photo);
        TextView tvCameraPhoto = view.findViewById(R.id.tv_camera_photo);


        AnimationUtils.slideToUp(getActivity(),view);
        if (bottomSheetDialog.getWindow() != null) {//灰色的阴影效果去除
            WindowManager.LayoutParams params = bottomSheetDialog.getWindow().getAttributes();
            params.dimAmount = 0.0f;
            bottomSheetDialog.getWindow().setAttributes(params);
        }
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);

        AnimationUtils.slideToUp1(getActivity(),view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);//屏幕高的90%
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);


        btnOpenBle.setOnClickListener(view1 -> {
            dismiss();
        });

        tvZoomPhoto.setOnClickListener(view1 -> {
           dismiss();
        });

        tvSelectPhoto.setOnClickListener(view1 -> {
            dismiss();
            startPhotoCode();
           // onAlbum();
        });

        tvCameraPhoto.setOnClickListener(view1 -> {
            dismiss();
            onCamera();
        });
        return bottomSheetDialog;

    }
    private void startPhotoCode() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }
    private void onCamera() {
        if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);

            return;
        }

        isAlbum = false;
        picturePath = avatarPath();

        File file = new File(picturePath);

        if(file.exists()) {
            file.delete();
        }

        Uri uri2 = fileUri(file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
        getActivity().startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    public void onCameraResult(Intent intent) {
        if(!isAlbum)
            cropPhotoFromCamera(fileUri(new File(picturePath)));
        else {
            cropPhotoFromAlbum(intent.getData());
        }
    }

    private void cropPhotoFromAlbum( Uri uri) {

        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri,columns,null,null,null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(columns[0]);
        String path =  cursor.getString(index);

        cursor.close();

        picturePath  = avatarPath();
        File file = new File(picturePath);

        try {

            FileOutputStream os = new FileOutputStream(picturePath);
            FileInputStream is = new FileInputStream(path);

            byte[] imageData = new byte[is.available()];
            is.read(imageData);
            os.write(imageData);
            os.flush();
            is.close();
            os.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        cropPhotoFromCamera(fileUri(file));

    }

    public void onCrop(Intent data) {
        try {
            Bitmap map = BitmapFactory.decodeFile(picturePath);
           // ivAvatar.setBitmap(map);
           // presenter.uploadAvatar(picturePath);
        }
        catch (Exception e) {

        }
    }

    private void cropPhotoFromCamera(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent


        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);//黑边


        // 设置裁剪区域的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);


        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        // 取消人脸识别
        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        getActivity().startActivityForResult(intent, REQUEST_CODE_CROP);
    }
    private String avatarPath() {


        File file = getContext().getExternalFilesDir(DIRECTORY_PICTURES);
        if(!file.exists()) {
            file.mkdir();
        }
        return (file.getPath() + File.separator +"avatar.jpg");


    }
    private void onAlbum() {

        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},22);
            return;
        }

        isAlbum = true;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");

        cropPath = avatarPath("avatar_crop.jpg");

        File file = new File(cropPath);
        Uri uri2 = fileUri(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri2);

        getActivity().startActivityForResult(intent,REQUEST_CODE_CAMERA);
    }
    private Uri fileUri(File file) {
        return FileProvider.getUriForFile(getContext(),"com.htl.ipc.FileProvider",file);

    }

    private String avatarPath(String name) {
        File file = getContext().getExternalFilesDir(DIRECTORY_PICTURES);
        if(!file.exists()) {
            file.mkdir();
        }
        return (file.getPath() + File.separator +name);


    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setClipToOutline(true);
        window.setAttributes(params);
        window.setAllowEnterTransitionOverlap(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }

        }
    }
    private void parsePhoto(Intent data) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), data.getData());
            //异步解析
            asyncThread(() -> {
                final String result = CodeUtils.parseCode(bitmap);
               getActivity().runOnUiThread(() -> {
                    LogUtils.d("result:" + result);
                    Toast.makeText( getActivity(), result, Toast.LENGTH_SHORT).show();
                });

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissCallback != null) {

        }

    }
}
