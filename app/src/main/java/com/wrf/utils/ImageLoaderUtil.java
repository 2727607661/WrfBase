package com.wrf.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageLoaderUtil {
	private static DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
	    .cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.EXACTLY);

	
	public static void displayImage(ImageView iv, String uri)
	{
//		builderForImageRes.showStubImage(R.drawable.pic_food_default);
//		builderForImageRes.showImageForEmptyUri(R.drawable.pic_food_default);
		ImageLoader.getInstance().displayImage(uri, iv, builder.build());
	}
	
	public static void setImage(ImageView iv, String uri) {
		ImageLoader.getInstance().displayImage(uri, iv, builder.build());
	}
	
	/**
	 * 图片下载未完成时，显示指定默认图
	 * @param iv
	 * @param uri
	 * @param res
	 */
	public static void setImage(ImageView iv, String uri, int res) {
		builder.showStubImage(res);
		builder.showImageForEmptyUri(res);
		ImageLoader.getInstance().displayImage(uri, iv, builder.build());
	}
	/**
	 * 显示本地图片
	* @param iv
	* @param uri
	 */
	public static void setFileImage(ImageView iv, String uri){
		uri = "file://" + uri;
		setImage(iv, uri);
	}
	/**
	 * 显示本地图片
	* @param iv
	* @param uri
	* @param res
	 */
	public static void setFileImage(ImageView iv, String uri, int res){
		uri = "file://" + uri;
		setImage(iv, uri, res);
	}

	/**
	 * 算出需要缩放的比例
	 */
	public static float calculateScale(int widthOrg,int heightOrg,int widthDes,int heightDes){
		float widthScale = (float)widthDes/widthOrg;
		float heightScale = (float)heightDes/heightOrg;
		return widthScale > heightScale ? widthScale:heightScale;
	}
	
}
