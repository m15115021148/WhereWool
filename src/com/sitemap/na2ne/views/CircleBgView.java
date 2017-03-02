package com.sitemap.na2ne.views;

import com.saitu.na2ne.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 圆角背景文字控件
 * 
 * @author chenhao
 * @date 2016-10-31
 */
public class CircleBgView extends View {
	private Paint mPaint;// 画笔
	private RectF leftRectF, rightRectF, middleRectF;// 绘制区域
	private int width, height;// 控件宽高
	private int backgroundColor, textColor ;// 背景颜色、文字颜色
	private String text ;// 显示文字
	private float textSize ;// 显示文字大小
	
	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public CircleBgView(Context context) {
		this(context, null);
	}

	public CircleBgView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleBgView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
//		自定义属性
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.circleBgView);
		int textColor=array.getColor(R.styleable.circleBgView_textColor, Color.RED);
		setTextColor(textColor);
		int background=array.getColor(R.styleable.circleBgView_background, Color.YELLOW);
		setBackgroundColor(background);
		// 如果设置为DP等单位，会做像素转换  
		float textSize=array.getDimensionPixelSize(R.styleable.circleBgView_textSize, 15);
		textSize=pxTodp(context,textSize);
		setTextSize(textSize);
		String text=array.getString(R.styleable.circleBgView_text);
		setText(text);
		array.recycle();
		
		mPaint = new Paint();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = getWidth();
		height = getHeight();
//		// 左半圆矩形
		leftRectF = new RectF(0, 0, height, height);
		// 中间矩形
		middleRectF = new RectF(height / 2, 0, width - height / 2, height);
//		 右半圆矩形
		rightRectF = new RectF(width - height, 0, width, height);
		
	}

	/**
	 * 画刷的宽度StrokeWidth以坐标为中心点开始画起
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘背景
		mPaint.setColor(backgroundColor);// 画笔颜色
		mPaint.setStyle(Paint.Style.FILL);// 画笔样式
		mPaint.setAntiAlias(true);// 抗锯齿
		canvas.drawArc(leftRectF, 90, 180, true, mPaint);
		canvas.drawArc(rightRectF, -90, 180, true, mPaint);
		canvas.drawRect(middleRectF, mPaint);
		
		// 绘文字
		mPaint.setStrokeWidth(4);
		mPaint.setTextSize(textSize);
		mPaint.setColor(textColor);
		mPaint.setTextAlign(Align.CENTER);
		FontMetrics fontMetrics = mPaint.getFontMetrics();
		float fontHeight = fontMetrics.bottom - fontMetrics.top;
		float baseline = (height - fontHeight) / 2 + fontHeight
				- fontMetrics.bottom;
		canvas.drawText(text, width / 2, baseline, mPaint);

	}

	/**
	 * 转换为屏幕大小
	 * @param context
	 * @param size
	 * @return
	 */
	public float pxTodp(Context context, float size) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		float density=dm.density;
		if(density>1){
			density-=0.5;
		}
		return density * size;
	}

}
