package com.deng.recipes;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import timber.log.Timber;

/**
 * Created by froger_mcs on 05.11.14.
 */
public class InstaMaterialApplication extends Application {

    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        initImageLoader();
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "UniversalImageLoader/Cache");

        imageLoader = ImageLoader.getInstance();
        // Create configuration for ImageLoader (all options are optional, use only those you really want to customize)
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // max width, max height
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation
                .discCache(new UnlimitedDiscCache(cacheDir)) // You can pass your own disc cache implementation
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(this)) // connectTimeout (5 s), readTimeout (20 s)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        imageLoader.init(config);
    }
}
