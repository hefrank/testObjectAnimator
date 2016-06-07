package com.pptips.uianimation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.pptips.uianimation.R;

/**
 * sun view
 */
public class SunView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private Paint mSunPaint,mSunShinePaint;
    private Paint mPaintARc;
    private RectF mRectArc = new RectF();

    public SunView(Context context) {
        super(context);
        init(null, 0);
    }

    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SunView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SunView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.SunView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.SunView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.SunView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.SunView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.SunView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();

        initMine();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mRectArc,270,200,false,mPaintARc);
        drawSun(canvas);

    }
    private final static int MAX_SUNSHINE = 8;

    private int screenHeight,screenWidth;
    private Matrix mSunMatrix,mSunShineMatrix;

    private int sunHeight = 220;
    private int sunWidth = 220;

    private int sunShineHeight = 92;
    private int sunShineWidth = 18;

    private RectF mArc = new RectF();

    private boolean mInit = true;

    private void initMine(){

        setBackgroundResource(R.drawable.bg);
        mPaintARc = new Paint();
        mPaintARc.setStrokeWidth(15);
        mPaintARc.setStyle(Paint.Style.STROKE);
        mPaintARc.setColor(0x66ffffff);
        mRectArc.set(100,100,300,300);
        mPaintARc.setStrokeCap(Paint.Cap.ROUND);

        mSunMatrix = new Matrix();
        mSunShineMatrix = new Matrix();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;

        mSunPaint = new Paint();
        mSunPaint.setAntiAlias(true);
        mSunPaint.setStyle(Paint.Style.STROKE);
        mSunPaint.setStrokeWidth(10);
        mSunPaint.setColor(0xffffffff);

        mSunShinePaint = new Paint();
        mSunShinePaint.setAntiAlias(true);
        mSunShinePaint.setStyle(Paint.Style.FILL);
        mSunShinePaint.setColor(0xffffffff);

        ssLeft =  - (sunShineWidth>>1);
        ssTop =  - (sunHeight>>1) - sunShineHeight - 20;
        ssRight = (sunShineWidth>>1);
        ssBottom = - (sunHeight>>1) - 20;

        mSunShineNew.left = ssLeft;
        mSunShineNew.top = ssTop;
        mSunShineNew.right = ssRight;
        mSunShineNew.bottom = ssBottom;

    }

    private int ssLeft;
    private int ssTop ;
    private int ssRight ;
    private int ssBottom ;

    private RectF mSunShineNew = new RectF();

    private void drawSun(Canvas canvas){
        final float progressRotation = getCurrentRotation();

        canvas.translate(screenWidth>>1,screenHeight>>1);
        canvas.drawCircle(0,0,sunHeight>>1,mSunPaint);

        canvas.rotate(progressRotation,0,0);

        for (int i = 0;i < MAX_SUNSHINE;i++){
            canvas.drawRoundRect(mSunShineNew,8,8,mSunShinePaint);
            canvas.rotate(45,0,0);
        }
    }

    private float mProgress;
    private float getCurrentRotation() {
        return 360 * mProgress;
    }
    public void setProgress(float progress){

        if (progress == 1){
            progress = 1;
        }else if (progress > 1){
            progress = progress%1;
        }
        mProgress = progress;
        invalidate();

    }

    public void setScalee(float progress){
        mSunShineNew.left = ssLeft;
        mSunShineNew.top = ssTop - 50*progress;
        mSunShineNew.right = ssRight;
        mSunShineNew.bottom = ssBottom;
        invalidate();
    }

    public float getProgress(){
        return mProgress;
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
