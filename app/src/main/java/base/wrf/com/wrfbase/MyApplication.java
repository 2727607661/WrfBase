package base.wrf.com.wrfbase;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;

/**
 * Created by wrf on 2016/1/25.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
        initImageLoader();
    }

    /**
     * 初始化oKhttp
     * https://github.com/hongyangAndroid/okhttp-utils
     */
    private  void initOkHttp(){
        OkHttpUtils.getInstance().getOkHttpClient();
        OkHttpUtils.getInstance().debug("testDebug");
    }

    /**
     * 初始化ImageLoader
     * https://github.com/nostra13/Android-Universal-Image-Loader
     */
    private void  initImageLoader() {
        //缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // 由原先的discCache -> diskCache
//                .diskCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

}
