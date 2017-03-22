package com.leo.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.leo.thmsocial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17 0017.
 */
public class Background extends View {
    private int pointNumber;
    private int currentPoint;
    private int length;
    private int height;
    private float padding = 5;
    private float percent;
    //是否隔一个显示一个
    private boolean isSkip = false;
    private List<Object> mList = new ArrayList<>();
    public Background(Context context) {
        super(context);
        init(null);
    }
    public Background(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public Background(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        if(this.mList == null || this.mList.size() == 0);{
            this.mList.add("请稍后...");
            this.pointNumber = mList.size();
        }
        setPadding(5);
//        pointNumber = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto","point_number",1);
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }
    public float getPercent(){
        if(this.pointNumber > 1) {
            float per = (1f / (this.pointNumber - 1)) / 2;
            for (int i = 0; i < this.pointNumber; i++) {
                float currPoint = i * 1f / (this.pointNumber - 1);
                if ((this.percent + per) >= currPoint && (this.percent + per) < currPoint + per * 2)
                    return i * per * 2;
            }
        }else{
            return 1;
        }
        return 0;
    }

    /**
     * 获得当前应该在的点
     * @param currentPoint
     */
    public void setCurrentNumber(int currentPoint){
        this.currentPoint = currentPoint;
        if(this.pointNumber>1){
            this.percent = (currentPoint-1)*1f/(pointNumber -1);
        }else{
            this.percent = 1;
        }
        invalidate();
    }

    public void setSkip(boolean isSkip){
        this.isSkip = isSkip;
    }

    public void setText(Object [] texts){
        this.mList.clear();
        for(int i = 0; i<texts.length; i++){
            this.mList.add(texts[i]);
        }
        this.pointNumber = texts.length;
        this.invalidate();
    }
    public void setPadding(float padding){
        this.padding = padding;
    }
    /**
     * 获得seekbar的进度
     * @return
     */
    public int getCurrentPointNumber(){
        return (int) ((this.pointNumber - 1) * getPercent());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int padding = dip2px(this.getContext(),this.padding);
        length =  this.getWidth() - padding * 2;
        height = this.getMeasuredHeight()  / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int trokeWidths = 10;
        paint.setStrokeWidth(trokeWidths);

        int distanceIn = 10;
        int distanceOut = 5;
        int radius = height/2 - distanceIn;

        int distancePoint = 0;
        int left = padding;
        int top = 0;
        int right = left + height;
        int bottom = height;
        if(pointNumber > 1) {
            distancePoint =(length - height) / (pointNumber - 1);

            //右开口圆弧
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(ContextCompat.getColor(this.getContext(), R.color.black_1));
            RectF outside = new RectF(left + distanceOut, top + distanceOut,
                    right - distanceOut, bottom - distanceOut);
            canvas.drawArc(outside, 30, 300, false, paint);

            //中间的
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(this.getContext(), R.color.blue));
            RectF inside = new RectF(left + distanceIn, top + distanceIn,
                    right - distanceIn, bottom - distanceIn);
            canvas.drawArc(inside, 30, 360, false, paint);
            drawText(canvas,this.mList.get(0),left,bottom,paint);

            int distanceLeftLine = (int) (height/2 + radius*(Math.sin(60*Math.PI/180)) + 0.5f);
            int topLine = height / 4;
            int bottomLine = height * 3 / 4;
            int rightLine, leftLine;
            for(int i = 1; i < pointNumber; i++){
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(ContextCompat.getColor(this.getContext(), R.color.black_1));
                left = distancePoint * i + padding;
                //上边线
                leftLine = (left - distancePoint) + distanceLeftLine;
                rightLine = (int) (left + height/2 - radius*(Math.sin(60*Math.PI/180)) + 0.5f);
                canvas.drawLine(leftLine,topLine,rightLine,topLine,paint);
                canvas.drawLine(leftLine,bottomLine,rightLine,bottomLine,paint);

                //两个圆弧
                right = left + height;
                RectF outside2 = new RectF(left + distanceOut, top + distanceOut,
                        right - distanceOut, bottom - distanceOut);
                canvas.drawArc(outside2, 210, 120, false, paint);
                canvas.drawArc(outside2, 30, 120, false, paint);
                //中间的
                int color =(float) i/(pointNumber - 1) < percent ? R.color.blue : R.color.main_gray;
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(this.getContext(), color));
                RectF inside2 = new RectF(left + distanceIn, top + distanceIn,
                        right - distanceIn, bottom - distanceIn);
                canvas.drawArc(inside2, 30, 360, false, paint);
                if(!isSkip || i % 2==0)
                    drawText(canvas,this.mList.get(i),left,bottom,paint);
            }
        }else{
//            this.percent = 100;
            int disOut = height/4 ;
            int disIn = height/4 +distanceIn/2;
            //右开口圆弧
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(ContextCompat.getColor(this.getContext(), R.color.black_1));
            RectF outside = new RectF(left + disOut, top + disOut,
                    right - disOut, bottom - disOut);
            canvas.drawArc(outside, 30, 300, false, paint);

            //中间的
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(this.getContext(), R.color.blue));
            RectF inside = new RectF(left + disIn, top + disIn,
                    right - disIn, bottom - disIn);
            canvas.drawArc(inside, 30, 360, false, paint);

            int topLine = height / 4;
            int bottomLine = height * 3 / 4;
            int rightLine, leftLine;
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(ContextCompat.getColor(this.getContext(), R.color.black_1));
            //上边线
            leftLine = height/2 + padding;
            rightLine = (int) (length - height/2 + padding + radius*(Math.sin(60*Math.PI/180)) + 0.5f);
            canvas.drawLine(leftLine,topLine,rightLine,topLine,paint);
            canvas.drawLine(leftLine,bottomLine,rightLine,bottomLine,paint);
        }

        //左开口圆弧
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.black_1));
        left = length - height + padding;
        right = length + padding;
        RectF outside1 = new RectF(left + distanceOut, top + distanceOut,
                right - distanceOut, bottom - distanceOut);
        canvas.drawArc(outside1, 210, 300, false, paint);

        //中间的
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.main_gray));
        RectF inside1 = new RectF(left + distanceIn, top + distanceIn,
                right - distanceIn, bottom - distanceIn);
        canvas.drawArc(inside1, 210, 360, false, paint);
        if(this.pointNumber == 1)
            drawText(canvas,this.mList.get(0),left,bottom,paint);
        paint.setStrokeWidth(height/2 - distanceOut*2);
        float end = percent*(length - height) + height / 2 + padding;
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.blue));
        canvas.drawLine(height/2 + padding,height/2, end,height/2,paint);

        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.main_gray));
        canvas.drawLine(end,height/2,length - height/2 + padding,height/2,paint);
    }

    private void drawText(Canvas canvas, Object text, float x ,float y, Paint paint) {
        paint.setTextSize(sp2px(this.getContext(),12));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.low_blue));
        int px = dip2px(this.getContext(),15);
        canvas.drawText(text.toString(), x + height/2, y + px,paint);
    }
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}