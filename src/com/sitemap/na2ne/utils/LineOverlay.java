package com.sitemap.na2ne.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.inner.GeoPoint;

/**
 * com.sitemap.na2ne.utils.LineOverlay
 * @author zhang
 * create at 2016年4月5日 上午9:19:16
 */
public class LineOverlay extends Overlay {
	 private GeoPoint front;  
	    private GeoPoint back;  
	    Paint paint;  
	    public LineOverlay() {  
	    }  
	  
	    public LineOverlay(GeoPoint front, GeoPoint back) {  
	        this.front = front;  
	        this.back = back;  
	    }  
	  
	    public void draw(Canvas canvas, MapView mapview, boolean shadow) {  
//	        super.draw(canvas, mapview, shadow);  
	        paint = new Paint();  
	        paint.setColor(Color.BLUE);  
	        paint.setStyle(Paint.Style.FILL_AND_STROKE);  
	        paint.setStrokeWidth(2);  
	        paint.setAntiAlias(true);  
	  
	        Point point_f = new Point();  
	        Point point_b = new Point();  
	  
	        //Path path = new Path();  
//	        mapview.getProjection().toPixels(front, point_f);  
//	        mapview.getProjection().toPixels(back, point_b);  
	        // begin  
	        //path.moveTo(point_b.x, point_b.y);  
	        // end  
	        //path.lineTo(point_f.x, point_f.y);  
	        //canvas.drawPath(path, paint);  
	        //JudgeDrawArrow(canvas, point_b.x, point_b.y, point_f.x, point_f.y);  
	        JudgeDrawArrow(canvas, point_f.x, point_f.y, point_b.x, point_b.y);  
	    }  
	      
	    /* 
	     * x1 is the first geopoint marker point X,y1 is that Y; 
	     * x2 is the second geopoint marker point X,y2 is that Y; 
	     * judge and draw arrow line 
	     */  
	    public void JudgeDrawArrow(Canvas canvas, int x1, int y1, int x2, int y2) {  
	        double arrow_height = 10; // 箭头高度    
	        double arrow_btomline = 7; // 底边的一半    
	        int x3 = 0;    
	        int y3 = 0;    
	        int x4 = 0;    
	        int y4 = 0;  
	        double arctangent = Math.atan(arrow_btomline / arrow_height); // 箭头角度    
	        double arrow_len = Math.sqrt(arrow_btomline * arrow_btomline + arrow_height * arrow_height); // 箭头的长度    
	        double[] endPoint_1 = rotateVec(x2 - x1, y2 - y1, arctangent, true,    
	                arrow_len);  
	        double[] endPoint_2 = rotateVec(x2 - x1, y2 - y1, -arctangent, true,    
	                arrow_len);  
	        double x_3 = x2 - endPoint_1[0]; // (x_3,y_3)是第一端点    
	        double y_3 = y2 - endPoint_1[1];    
	        double x_4 = x2 - endPoint_2[0]; // (x_4,y_4)是第二端点    
	        double y_4 = y2 - endPoint_2[1];    
	        Double X3 = new Double(x_3);  
	        x3 = X3.intValue();    
	        Double Y3 = new Double(y_3);    
	        y3 = Y3.intValue();    
	        Double X4 = new Double(x_4);    
	        x4 = X4.intValue();    
	        Double Y4 = new Double(y_4);    
	        y4 = Y4.intValue();    
	        // 画线    
	        canvas.drawLine(x1, y1, x2, y2,paint);    
	        // 画箭头的一半    
	        canvas.drawLine(x2, y2, x3, y3,paint);    
	        // 画箭头的另一半    
	        canvas.drawLine(x2, y2, x4, y4,paint);    
	    }    
	    
	    public double[] rotateVec(int px, int py, double ang, boolean isChlen,  
	            double newLen) {    
	    
	        double rotateResult[] = new double[2];    
	        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度    
	        double vx = px * Math.cos(ang) - py * Math.sin(ang);    
	        double vy = px * Math.sin(ang) + py * Math.cos(ang);    
	        if (isChlen) {    
	            double d = Math.sqrt(vx * vx + vy * vy);    
	            vx = vx / d * newLen;    
	            vy = vy / d * newLen;    
	            rotateResult[0] = vx;    
	            rotateResult[1] = vy;    
	        }    
	        return rotateResult;    
	    }  
}
