package android.slc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by on the way on 2017/9/17.
 */

public class RectProgressView extends View {
    private final static int DEF_BACK_COLOR = Color.parseColor("#FFFFFF");
    private final static int DEF_BACK_STROKE_COLOR = Color.parseColor("#3F51B5");
    private final static int DEF_BACK_PROGRESS_COLOR = Color.parseColor("#F0F0F0");
    private final static int DEF_PROGRESS_COLOR = Color.parseColor("#FF4081");
    private final static int DEF_PROGRESS_STROKE_COLOR = Color.parseColor("#FF4081");
    private final static int DEF_COMPLETE_COLOR = Color.parseColor("#FFFFFF");
    private final static int DEF_COMPLETE_STROKE_COLOR = Color.parseColor("#303F9F");
    protected Paint progressPaint, textPaint;//
    protected int strokeWidth, radius;
    protected int backColor, backStrokeColor, backProgressColor;
    protected int progressColor, progressStrokeColor;
    protected int completeColor, completeStrokeColor;
    protected int textColor, textProgressColor, completeTextColor, textSize;
    protected int screenWidth, screenHeight;
    protected int bitmapWidth, bitmapHeight;
    protected int max;
    protected int progress;
    protected String text;
    protected Canvas bitmapCanvas;
    protected Path path;
    protected Bitmap bitmap;
    protected GradientDrawable bgDrawable, strokeDrawable;
/*    public final static int NORMAL = 0, PROCEED = 1, COMPLETE = 2;
    private int progressState = NORMAL;*/

    public RectProgressView(Context context) {
        this(context, null);
    }

    public RectProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyAttributes(context, attrs);
        init();
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        backColor = DEF_BACK_COLOR;
        backStrokeColor = DEF_BACK_STROKE_COLOR;
        backProgressColor = DEF_BACK_PROGRESS_COLOR;

        progressColor = DEF_PROGRESS_COLOR;
        progressStrokeColor = DEF_PROGRESS_STROKE_COLOR;

        completeColor = DEF_COMPLETE_COLOR;
        completeStrokeColor = DEF_COMPLETE_STROKE_COLOR;

        strokeWidth = dip2px(1);
        radius = dip2px(4);

        textSize = dip2px(14);
        textColor = DEF_BACK_STROKE_COLOR;
        textProgressColor = DEF_PROGRESS_STROKE_COLOR;
        completeTextColor = DEF_COMPLETE_STROKE_COLOR;
        max = 100;
        progress = -1;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
            int n = typedArray.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.ProgressView_max)
                    max = typedArray.getInteger(attr, 100);
                else if (attr == R.styleable.ProgressView_vProgress)
                    progress = typedArray.getInteger(attr, -1);
                else if (attr == R.styleable.ProgressView_backColor)
                    backColor = typedArray.getColor(attr, DEF_BACK_COLOR);
                else if (attr == R.styleable.ProgressView_backStrokeColor)
                    backStrokeColor = typedArray.getColor(attr, DEF_BACK_STROKE_COLOR);
                else if (attr == R.styleable.ProgressView_backProgressColor)
                    backProgressColor = typedArray.getColor(attr, DEF_BACK_PROGRESS_COLOR);
                else if (attr == R.styleable.ProgressView_progressColor)
                    progressColor = typedArray.getColor(attr, DEF_PROGRESS_COLOR);
                else if (attr == R.styleable.ProgressView_progressStrokeColor)
                    progressStrokeColor = typedArray.getColor(attr, DEF_PROGRESS_STROKE_COLOR);
                else if (attr == R.styleable.ProgressView_completeColor)
                    completeColor = typedArray.getColor(attr, DEF_COMPLETE_COLOR);
                else if (attr == R.styleable.ProgressView_completeStrokeColor)
                    completeStrokeColor = typedArray.getColor(attr, DEF_COMPLETE_STROKE_COLOR);
                else if (attr == R.styleable.ProgressView_strokeWidth)
                    strokeWidth = typedArray.getDimensionPixelSize(attr, dip2px(1));
                else if (attr == R.styleable.ProgressView_radius)
                    radius = typedArray.getDimensionPixelSize(attr, dip2px(4));
                else if (attr == R.styleable.ProgressView_textColor)
                    textColor = typedArray.getColor(attr, DEF_BACK_STROKE_COLOR);
                else if (attr == R.styleable.ProgressView_textProgressColor)
                    textProgressColor = typedArray.getColor(attr, DEF_PROGRESS_STROKE_COLOR);
                else if (attr == R.styleable.ProgressView_completeTextColor)
                    completeTextColor = typedArray.getColor(attr, DEF_COMPLETE_STROKE_COLOR);
                else if (attr == R.styleable.ProgressView_textSize)
                    textSize = typedArray.getDimensionPixelSize(attr, dip2px(14));
                else if (attr == R.styleable.ProgressView_text)
                    text = typedArray.getString(attr);
            }
            typedArray.recycle();
        }
    }

    protected void init() {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setDither(true);
        progressPaint.setColor(progressColor);
        progressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        bgDrawable = new GradientDrawable();
        strokeDrawable = new GradientDrawable();
        path = new Path();
    }

    protected void initBgAndCompleteDrawable() {
        bgDrawable.setSize(bitmapWidth, bitmapHeight);
        bgDrawable.setShape(GradientDrawable.RECTANGLE);
        bgDrawable.setColor(backColor);
        bgDrawable.setCornerRadius(radius);
        bgDrawable.setBounds(0, 0, bitmapWidth, bitmapHeight);

        strokeDrawable.setSize(bitmapWidth, bitmapHeight);
        strokeDrawable.setCornerRadius(radius);
        strokeDrawable.setStroke(strokeWidth, backStrokeColor);
        strokeDrawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        screenWidth = measureWidth(getSuggestedMinimumWidth(), widthMeasureSpec);
        screenHeight = measureHeight(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(screenWidth, screenHeight);
        initBitmap();
    }

    private void initBitmap() {
        bitmapWidth = screenWidth - getPaddingLeft() - getPaddingRight();
        bitmapHeight = screenHeight - getPaddingTop() - getPaddingBottom();
        if (bitmapWidth > 0 && bitmapHeight > 0) {
            bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_4444);
            bitmapCanvas = new Canvas(bitmap);
            bitmapCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            initBgAndCompleteDrawable();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapCanvas != null) {
            //绘制背景
            bitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            bgDrawable.setColor(progress < 0 ? backColor : progress >= max ? completeColor : backProgressColor);
            bgDrawable.draw(bitmapCanvas);
            //绘制进度
            if (progress >= 0 && progress < max) {
                path.reset();
                float progressWidth = ((float) progress / max) * bitmapWidth;
                path.moveTo(strokeWidth, strokeWidth);
                path.lineTo(progressWidth - strokeWidth, strokeWidth);
                path.lineTo(progressWidth - strokeWidth, bitmapHeight - strokeWidth);
                path.lineTo(strokeWidth, bitmapHeight - strokeWidth);
                path.close();
                bitmapCanvas.drawPath(path, progressPaint);
            }
            //绘制边框
            strokeDrawable.setStroke(strokeWidth, progress < 0 ? backStrokeColor : progress >= max ? completeStrokeColor : progressStrokeColor);
            strokeDrawable.draw(bitmapCanvas);
            //文字
            //text = progress >= 0 && progressState < 100 ? (int) (((float) progress / (float) max) * 100) + "%" : text;
            float textWidth = textPaint.measureText(text);
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            float dy = -(metrics.descent + metrics.ascent) / 2;
            bitmapCanvas.drawText(text, bitmapWidth / 2 - textWidth / 2, bitmapHeight / 2 + dy, textPaint);
            canvas.drawBitmap(bitmap, getPaddingLeft(), getPaddingTop(), null);
            //canvas.restore();
        }
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                //精确的
                defaultWidth = specSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (progress < 0)
            textPaint.setColor(textColor);
        else if (progress >= 0 && progress < max)
            textPaint.setColor(textProgressColor);
        else if (progress >= max)
            textPaint.setColor(completeTextColor);
        invalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

/*    public void setProgressState(int progressState) {
        if (progressState == NORMAL || progressState == PROCEED || progressState == COMPLETE)
            this.progressState = progressState;
        invalidate();
    }*/

    private int dip2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5);
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
