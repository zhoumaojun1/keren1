package com.wja.keren.user.home.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

public class FrameAnimationManager {
    private final static String TAG = "FrameAnimationUtils";
    private static volatile FrameAnimationManager mInstance;
    private HashMap<Integer, Integer> hashMap = new HashMap();

    /**
     * 获取单例
     */
    public static FrameAnimationManager getInstance() {
        if (mInstance == null) {
            synchronized (FrameAnimationManager.class) {
                if (mInstance == null) {
                    mInstance = new FrameAnimationManager();
                }
            }
        }
        return mInstance;
    }


    public FramesAnimation createFramesAnimation() {
        return new FramesAnimation();
    }

    /**
     * 循环读取帧---循环播放帧
     */
    public class FramesAnimation {
        /**
         * 帧数组
         */
        private int[] mFrames;
        /**
         * 当前帧
         */
        private volatile int mIndex;
        /**
         * 开始/停止播放用
         */
        private volatile boolean mShouldRun;
        /**
         * 动画是否正在播放，防止重复播放
         */
        private boolean mIsRunning;
        /**
         * 软引用ImageView，以便及时释放掉
         */
        private ImageView mSoftReferenceImageView;
        private Handler mHandler;
        private int mDelayMillis;
        private int fps;
        private boolean isRepeat;
        private int lastPictureId = -1;//播放的最后一帧的图片ID

        private Bitmap mBitmap;
        /**
         * Bitmap管理类，可有效减少Bitmap的OOM问题
         */
        private BitmapFactory.Options mBitmapOptions;
        /**
         * 播放监听
         */
        private AnimationListener animationListener;

        FramesAnimation() {
            mHandler = new Handler();
        }

        /**
         * @param imageView image组件
         * @param fps       FPS为每秒播放帧数，FPS = 1/T，（T--每帧间隔时间秒）
         * @param isRepeat  是否重复播放
         * @return 帧动画
         */
        public void setFrameData(ImageView imageView, int[] frames, int fps, boolean isRepeat) {
            mFrames = frames;
            this.fps = fps;
            mIndex = -1;
            mSoftReferenceImageView = imageView;
            mShouldRun = false;
            mIsRunning = false;
            //帧动画时间间隔，毫秒
            mDelayMillis = 1000 / fps;
            this.isRepeat = isRepeat;

            // 当图片大小类型相同时进行复用，避免频繁GC
            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//            Bitmap bmp = ((BitmapDrawable) imageView.getBackground()).getBitmap();
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            Bitmap.Config config = bmp.getConfig();
            mBitmap = Bitmap.createBitmap(width, height, config);
            mBitmapOptions = new BitmapFactory.Options();
            //设置Bitmap内存复用

            //Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
            mBitmapOptions.inBitmap = mBitmap;
            //解码时返回可变Bitmap
            mBitmapOptions.inMutable = true;
            //缩放比例
            mBitmapOptions.inSampleSize = 1;
        }

        /**
         * 设置帧数
         *
         * @param fps 帧数
         */
        public synchronized void setFps(int fps) {
            this.fps = fps;
            mDelayMillis = 1000 / fps;
        }

        /**
         * 设置帧数
         *
         * @param fpsRat 速率
         */
        public synchronized void setFpsRat(float fpsRat) {
            mDelayMillis = (int) (1000 / (fps * fpsRat));
        }

        /**
         * 循环读取下一帧
         *
         * @return 下一帧
         */
        private int getNext() {
            mIndex++;
            if (mIndex >= mFrames.length) {
                mIndex = 0;
                if (!isRepeat) {
                    mShouldRun = false;
                }
            }
            return mFrames[mIndex];
        }

        /**
         * 播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        public synchronized void start() {
            mShouldRun = true;
            if (mIsRunning) {
                return;
            }
            if (animationListener != null) {
                animationListener.onAnimationStarted();
            }
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    int imageRes = getNext();
                    ImageView imageView = mSoftReferenceImageView;
                    if (!mShouldRun || imageView == null) {
                        mIsRunning = false;
                        if (animationListener != null) {
                            animationListener.onAnimationStopped();
                        }
                        return;
                    }
                    mIsRunning = true;
                    //新开线程去读下一帧
                    mHandler.postDelayed(this, mDelayMillis);
                    setFrameImage(imageView, imageRes);
                }
            };
            mHandler.post(runnable);
        }

        /**
         * 停止播放
         */
        public synchronized void stop() {
            if (!mShouldRun) {
                return;
            }
            mShouldRun = false;
            mIndex = 0;
        }

        private void setFrameImage(ImageView imageView, int imageRes) {
            lastPictureId = imageRes;
            if (!imageView.isShown()) {
                imageView.setVisibility(View.VISIBLE);
            }
            if (mBitmap != null) {
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeResource(imageView.getResources(), imageRes,
                            mBitmapOptions);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(imageRes);
                    mBitmap.recycle();
                    mBitmap = null;
                }
            } else {
                imageView.setImageResource(imageRes);
            }
        }

        public int getLastPictureId() {
            return lastPictureId;
        }

        /**
         *
         */
        public synchronized boolean isRunning() {
            return mShouldRun;
        }

        /**
         * 设置播放监听
         *
         * @param listener 动画停止的监听
         */
        public FramesAnimation setAnimationListener(AnimationListener listener) {
            this.animationListener = listener;
            return this;
        }
    }


    /**
     * 从xml中读取帧数组
     *
     * @param resId 动画资源arrayIds
     */
    private int[] getData(Context context, int resId) {
        TypedArray array = context.getResources().obtainTypedArray(resId);

        int len = array.length();
        int[] intArray = new int[len];

        for (int i = 0; i < len; i++) {
            intArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return intArray;
    }

    /**
     * 播放监听
     */
    public interface AnimationListener {
        /**
         * 开始播放
         */
        void onAnimationStarted();

        /**
         * 停止播放
         */
        void onAnimationStopped();
    }


}
