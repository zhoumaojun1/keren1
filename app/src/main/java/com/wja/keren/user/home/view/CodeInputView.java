package com.wja.keren.user.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.wja.keren.R;

@SuppressLint("AppCompatCustomView")
public class CodeInputView extends EditText {
    // <!-- 最大输入长度 -->
    private int mMaxLength = 4;
    // <!-- 边框宽度 -->
    private int mBorderWidth = 100;
    // <!-- 边框高度 -->
    private int mBorderHeight = 100;
    // <!-- 边框间距 -->
    private int mBorderSpacing = 24;
    // <!-- 边框背景图 -->
    private Drawable mBorderImage;

    // 用矩形来保存方框的位置、大小信息
    private final Rect mRect = new Rect();

    // 文本颜色
    private int mTextColor;

    public CodeInputView(Context context) {
        super(context);
        init(context, null);
    }

    public CodeInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CodeInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CodeInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    // 初始化
    private void init(Context context, AttributeSet attrs) {
        if (null == mBorderImage) {
            mBorderImage = ContextCompat.getDrawable(context,
                    R.drawable.shape_add_share_account_card);
        }
        initAttrs(context, attrs);
        // 设置最大输入长度
        setMaxLength(mMaxLength);
        // 禁止长按
        setLongClickable(false);
        // 去掉背景颜色
        setBackgroundColor(Color.TRANSPARENT);
        // 不显示光标
        setCursorVisible(false);
    }

    // 设置最大长度
    private void setMaxLength(int maxLength) {
        if (maxLength >= 0) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            setFilters(new InputFilter[0]);
        }
    }

    // 初始化属性
    private void initAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            // AttributeSet 属性值的索引
            TypedArray o = context.obtainStyledAttributes(attrs, R.styleable.CodeInputView);
            // <!-- 最大输入长度 -->
            mMaxLength = o.getInteger(R.styleable.CodeInputView_android_maxLength, 4);
            // <!-- 边框宽度 -->
            mBorderWidth = (int) o.getDimension(R.styleable.CodeInputView_borderWidth, 100f);
            // <!-- 边框高度 -->
            mBorderHeight = (int) o.getDimension(R.styleable.CodeInputView_borderHeight, 100f);
            // <!-- 边框间距 -->
            mBorderSpacing = (int) o.getDimension(R.styleable.CodeInputView_borderSpacing, 24);
            // <!-- 边框背景图 -->
            Drawable drawable = o.getDrawable(R.styleable.CodeInputView_borderImage);
            if (null != drawable) {
                mBorderImage = drawable;
            }
            // 回收资源
            o.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 当前输入框的宽高信息
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 判断高度是否小于自定义边框高度
        height = height < mBorderHeight ? mBorderHeight : height;

        // 自定义输入框宽度 = 边框宽度 * 数量 + 边框间距 * (数量 - 1)
        int customWidth = mBorderWidth * mMaxLength
                + mBorderSpacing * ((mMaxLength - 1) > 0 ? (mMaxLength - 1) : 0);

        // 判断宽度是否小于自定义宽度
        width = width < customWidth ? customWidth : width;

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);

        // 重新设置测量布局
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取当前输入文本颜色
        mTextColor = getCurrentTextColor();
        // 屏蔽系统文本颜色，直接透明
        setTextColor(Color.TRANSPARENT);
        // 父类绘制
        super.onDraw(canvas);
        // 重新设置文本颜色
        setTextColor(mTextColor);
        // 重绘背景
        drawBorderBackground(canvas);
        // 重绘文本
        drawText(canvas);
    }

    // 绘制背景
    private void drawBorderBackground(Canvas canvas) {
        // 下面绘制方框背景颜色
        // 确定反馈位置
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mBorderWidth;
        mRect.bottom = mBorderHeight;
        // 当前画布保存的状态
        int count = canvas.getSaveCount();
        // 保存画布
        canvas.save();
        // 获取当前输入字符串长度
        int length = getEditableText().length();
        for (int i = 0; i < mMaxLength; i++) {
            // 设置位置
            mBorderImage.setBounds(mRect);
            // 设置图像状态
            if (i == length) {
                // 当前输入位高亮的索引
                mBorderImage.setState(new int[]{android.R.attr.state_focused});
            } else {
                // 其他输入位置默认
                mBorderImage.setState(new int[]{android.R.attr.state_enabled});
            }
            // 画到画布上
            mBorderImage.draw(canvas);
            // 确定下一个方框的位置
            // X坐标位置
            float dx = mRect.right + mBorderSpacing;
            // 保存画布
            canvas.save();
            // [注意细节] 移动画布到下一个位置
            canvas.translate(dx, 0);
        }
        // [注意细节] 把画布还原到画反馈之前的状态，这样就还原到最初位置了
        canvas.restoreToCount(count);
        // 画布归位
        canvas.translate(0, 0);
    }

    // 绘制文本
    private void drawText(Canvas canvas) {
        int count = canvas.getSaveCount();
        canvas.translate(0, 0);
        int length = getEditableText().length();
        for (int i = 0; i < length; i++) {
            String text = String.valueOf(getEditableText().charAt(i));
            TextPaint textPaint = getPaint();
            textPaint.setColor(mTextColor);
            // 获取文本大小
            textPaint.getTextBounds(text, 0, 1, mRect);
            // 计算(x,y) 坐标
            int x = mBorderWidth / 2 + (mBorderWidth + mBorderSpacing) * i - (mRect.centerX());
            int y = canvas.getHeight() / 2 + mRect.height() / 2;
            canvas.drawText(text, x, y, textPaint);
        }
        canvas.restoreToCount(count);
    }

}
