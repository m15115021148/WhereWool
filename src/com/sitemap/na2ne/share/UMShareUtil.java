package com.sitemap.na2ne.share;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;

/**
 * 
 * Description: ���˷���������
 * 
 * @author chenhao
 * @date 2016-3-25
 */
public class UMShareUtil {

	private Activity context;// �����Ķ���
	private UMSharePlatformListener listener;// �����ص�����
	// ����ƽ̨�б�
	private final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[] {
			SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
			SHARE_MEDIA.QZONE };

	// SHARE_MEDIA.SINA,����΢����ʱ����

	public UMShareUtil(Activity context) {
		this.context = context;
		UMShareConfig.init();
		listener = new UMSharePlatformListener(context);
	}

	/**
	 * �򿪷�������,��������ͼƬ��ʽ��url
	 * 
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 * 
	 * @param imageUrl
	 *            ����ʱչʾ��ͼƬ��URL��
	 * @param url Ҫ������URL
	 */
	public void shareNetImage(String title, String content, String imageUrl,
			String url) {
		// ����ͼƬ
		UMImage image = new UMImage(context, imageUrl);
		new ShareAction(context).setDisplayList(displaylist).withTitle(title)
				.withMedia(image).withText(content).withTargetUrl(url)
				.setCallback(listener).open();
	}

	/**
	 * �򿪷�������,����appϵͳ���Դ���ͼƬ��ʽ��url
	 * 
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 * @param drawableID
	 *            Ҫ������ͼƬid
	 * @param url
	 *            Ҫ������URL
	 */
	public void shareDrawableImage(String title, String content,
			int drawableID, String url) {
		// �����е�ͼƬ
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				drawableID);
		UMImage image = new UMImage(context, bitmap);
		new ShareAction(context).setDisplayList(displaylist).withTitle(title)
				.withMedia(image).withText(content).withTargetUrl(url)
				.setCallback(listener).open();
	}

	/**
	 * �򿪷�������,�����ⲿ�洢��ͼƬ��ʽ��url
	 * 
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 * @param path
	 *            Ҫ������ͼƬ·��
	 * @param url
	 *            Ҫ������URL
	 */
	public void shareFileImage(String title, String content, String path,
			String url) {
		UMImage image = new UMImage(context, new File(path));
		new ShareAction(context).setDisplayList(displaylist).withTitle(title)
				.withMedia(image).withText(content).withTargetUrl(url)
				.setCallback(listener).open();
	}

	/**
	 * �򿪷�������,����music
	 * 
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 * @param musicTitle
	 *            ���ֱ���
	 * @param musicUrl
	 *            ����·��url
	 * @param musicImageUrl
	 *            ����ͼƬurl
	 */
	public void shareMusic(String title, String content, String musicUrl,
			String musicTitle, String musicImageUrl) {
		UMusic music = new UMusic(musicUrl);
		music.setTitle(musicTitle);
		music.setThumb(new UMImage(context, musicImageUrl));
		new ShareAction(context).setDisplayList(displaylist).withTitle(title)
				.withMedia(music).withText(content).setCallback(listener)
				.open();
	}

	/**
	 * �򿪷�������,����video
	 * 
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 * @param videoTitle
	 *            video����
	 * @param videoUrl
	 *            video·��url
	 * @param videoImageUrl
	 *            videoͼƬurl
	 */
	public void shareVideo(String title, String content, String videoUrl,
			String videoTitle, String videoImageUrl) {
		UMVideo video = new UMVideo(videoUrl);
		video.setTitle(videoTitle);
		video.setThumb(new UMImage(context, videoImageUrl));
		new ShareAction(context).setDisplayList(displaylist).withTitle(title)
				.withMedia(video).withText(content).setCallback(listener)
				.open();
	}
}