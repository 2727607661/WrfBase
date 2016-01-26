/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uk.co.senab.photoview.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import base.wrf.com.wrfbase.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Lock/Unlock button is added to the ActionBar.
 * Use it to temporarily disable ViewPager navigation in order to correctly interact with ImageView by gestures.
 * Lock/Unlock state of ViewPager is saved and restored on configuration changes.
 * 
 * Julia Zudikova
 */

public class ImagePagerActivity extends Activity {

	private static final String ISLOCKED_ARG = "isLocked";
	
	private ViewPager mViewPager;
	private MenuItem menuLockItem;
	private String[] imageUrls;
	DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
		setContentView(mViewPager);

		Bundle bundle = getIntent().getExtras();
		imageUrls = bundle.getStringArray("images");
		if(imageUrls == null){
			this.finish();
			return;
		}
		int pagerPosition = bundle.getInt("position", 0);

		SamplePagerAdapter adapter = new SamplePagerAdapter(imageUrls);
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(pagerPosition);

		if (savedInstanceState != null) {
			boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
			((HackyViewPager) mViewPager).setLocked(isLocked);
		}


		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.mipmap.ic_empty)
				.showImageOnFail(R.mipmap.ic_error)
				.resetViewBeforeLoading(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();

	}


	class SamplePagerAdapter extends PagerAdapter {
		private String[] imageUrls;

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		public SamplePagerAdapter(String[] imagUrls) {
			super();
			this.imageUrls = imagUrls;
		}

		public void setImagUrls(String[] imagUrls) {
			this.imageUrls = imagUrls;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			View imageLayout = LayoutInflater.from(ImagePagerActivity.this).inflate(R.layout.item_pager_image, container, false);
			PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);
			imageLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					ImagePagerActivity.this.finish();
				}
			});
			imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {

				@Override
				public void onViewTap(View view, float x, float y) {
					ImagePagerActivity.this.finish();
				}
			});
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
//			ImageLoader.getInstance().getDiscCache().get(imageUrls[position]).getPath();

			ImageLoader.getInstance().displayImage(imageUrls[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "网络异常";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "内存不足";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					Toast.makeText(ImagePagerActivity.this, message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			container.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewpager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuLockItem = menu.findItem(R.id.menu_lock);
        toggleLockBtnTitle();
        menuLockItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				toggleViewPagerScrolling();
				toggleLockBtnTitle();
				return true;
			}
		});

        return super.onPrepareOptionsMenu(menu);
    }
    
    private void toggleViewPagerScrolling() {
    	if (isViewPagerActive()) {
    		((HackyViewPager) mViewPager).toggleLock();
    	}
    }
    
    private void toggleLockBtnTitle() {
    	boolean isLocked = false;
    	if (isViewPagerActive()) {
    		isLocked = ((HackyViewPager) mViewPager).isLocked();
    	}
    	String title = (isLocked) ? getString(R.string.menu_unlock) : getString(R.string.menu_lock);
    	if (menuLockItem != null) {
    		menuLockItem.setTitle(title);
    	}
    }

    private boolean isViewPagerActive() {
    	return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }
    
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		if (isViewPagerActive()) {
			outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
    	}
		super.onSaveInstanceState(outState);
	}
    
}
