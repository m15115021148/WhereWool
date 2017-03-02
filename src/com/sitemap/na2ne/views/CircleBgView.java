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
 * Բ�Ǳ������ֿؼ�
 * 
 * @author chenhao
 * @date 2016-10-31
 */
public class CircleBgView extends View {
	private Paint mPaint;// ����
	private RectF leftRectF, rightRectF, middleRectF;// ��������
	private int width, height;// �ؼ����
	private int backgroundColor, textColor ;// ������ɫ��������ɫ
	private String text ;// ��ʾ����
	private float textSize ;// ��ʾ���ִ�С
	
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
//		�Զ�������
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.circleBgView);
		int textColor=array.getColor(R.styleable.circleBgView_textColor, Color.RED);
		setTextColor(textColor);
		int background=array.getColor(R.styleable.circleBgView_background, Color.YELLOW);
		setBackgroundColor(background);
		// �������ΪDP�ȵ�λ����������ת��  
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
//		// ���Բ����
		leftRectF = new RectF(0, 0, height, height);
		// �м����
		middleRectF = new RectF(height / 2, 0, width - height / 2, height);
//		 �Ұ�Բ����
		rightRectF = new RectF(width - height, 0, width, height);
		
	}

	/**
	 * ��ˢ�Ŀ��StrokeWidth������Ϊ���ĵ㿪ʼ����
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// �汳��
		mPaint.setColor(backgroundColor);// ������ɫ
		mPaint.setStyle(Paint.Style.FILL);// ������ʽ
		mPaint.setAntiAlias(true);// �����
		canvas.drawArc(leftRectF, 90, 180, true, mPaint);
		canvas.drawArc(rightRectF, -90, 180, true, mPaint);
		canvas.drawRect(middleRectF, mPaint);
		
		// ������
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
	 * ת��Ϊ��Ļ��С
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
