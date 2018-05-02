package com.zkteam.discover.fresco;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.zkteam.discover.app.BaseApp;
import com.zkteam.discover.util.StorageUtil;

public class FrescoInitUtil {

    //分配的可用内存
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    //使用的缓存数量
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

    //默认图所放路径的文件夹名
    private static final String IMAGE_CACHE_DEFAULT_DIR = "frescoCacheDefault";

    //默认图磁盘缓存的最大值
    private static final int MAX_DISK_CACHE_SIZE = 80 * ByteConstants.MB;

    //默认图低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_LOW_SIZE = 50 * ByteConstants.MB;

    //默认图极低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_VERYLOW_SIZE = 30 * ByteConstants.MB;

    //小图所放路径的文件夹名
    private static final String IMAGE_CACHE_SMALL_DIR = "frescoCacheSmall";

    //小图低磁盘空间缓存的最大值
    //如何区分小图片需要在ImageRequestBuilder手动调用setCacheChoice(ImageRequest.CacheChoice.SMALL)指定
    private static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 20 * ByteConstants.MB;

    //小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 10 * ByteConstants.MB;

    private static final int MAX_CACHE_ENTRIES = Integer.MAX_VALUE;
    //    private static final int MAX_EVICTION_QUEUE_SIZE = 6*ByteConstants.MB;//Integer.MAX_VALUE;
    private static final int MAX_EVICTION_QUEUE_ENTRIES = 128;//Integer.MAX_VALUE;
    private static final int MAX_CACHE_ENTRY_SIZE = Integer.MAX_VALUE;

    private static ActivityManager mActivityManager;

    public static void initFrescoConfig() {

        ImagePipelineConfig pipeConfig = ImagePipelineConfig.newBuilder(BaseApp.getContext())
                .setBitmapMemoryCacheParamsSupplier(new BitmapCacheParamsSupplier())//bitmap内存缓存数据的策略
                .setCacheKeyFactory(DefaultCacheKeyFactory.getInstance())//缓存键值对的获取 参考DefaultCacheKeyFactory进行修改缓存key
                .setDownsampleEnabled(true)//缩放图片 同样是软件实现的管道操作。它不是创建一张新的图片，而是在解码时改变图片的大小。
                .setEncodedMemoryCacheParamsSupplier(new EncodedMemoryCacheParamsSupplier())//未解码（原始压缩格式的图片）内存设置
                //设置本地读写线程池，网络数据线程池 解码线程池，以及后台线程池. DefaultExecutorSupplier默认设置
                //.setExecutorSupplier(executorSupplier)
                //NoOpImageCacheStatsTracker  追踪图片缓存相关状态
                //.setImageCacheStatsTracker(imageCacheStatsTracker)
                //设置磁盘缓存项配置
                .setMainDiskCacheConfig(getDisCacheConfigDefault(BaseApp.getContext()))
                .setSmallImageDiskCacheConfig(getDisCacheConfigSmall(BaseApp.getContext()))
                //设置管理池，默认为PoolFactory 存放了相应的bitmap，nativememory,shareByte数据池管理
                //.setPoolFactory(poolFactory)
                //渐进式JPEG配置  SimpleProgressiveJpegConfig
                //.setProgressiveJpegConfig(progressiveJpegConfig)
                //设置请求监听集合
                //.setRequestListeners(requestListeners)
                .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
                .build();

        FrescoIniter.initialize(BaseApp.getContext(), pipeConfig);

    }

    public static DiskCacheConfig getDisCacheConfigDefault(final Context context) {

        return DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(StorageUtil.getAppPicDir())
                .setBaseDirectoryName(IMAGE_CACHE_DEFAULT_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();
    }

    public static DiskCacheConfig getDisCacheConfigSmall(final Context context) {

        return DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(StorageUtil.getAppPicDir())
                .setBaseDirectoryName(IMAGE_CACHE_SMALL_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();
    }

    public static class BitmapCacheParamsSupplier implements Supplier<MemoryCacheParams> {

        @Override
        public MemoryCacheParams get() {

            mActivityManager = (ActivityManager) BaseApp.getContext().getSystemService(Context.ACTIVITY_SERVICE);

            final int maxCacheSize = getMaxCacheSize();
            final int maxCacheEntrySize = maxCacheSize / 2;

            return new MemoryCacheParams(
                    maxCacheSize,  // 内存缓存中总图片的最大大小,以字节为单位。
                    MAX_CACHE_ENTRIES,  // 内存缓存中图片的最大数量
                    maxCacheSize / 2,    // 内存缓存待回收 但尚未被回收的最大大小,以字节为单位。
                    MAX_EVICTION_QUEUE_ENTRIES,//// 内存缓存待回收图片的最大数量
                    maxCacheEntrySize);// 内存缓存中单个图片的最大大小
        }

        private int getMaxCacheSize() {

            try {

                final int maxMemory =
                        Math.min(mActivityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);
                if (maxMemory < 32 * ByteConstants.MB) {
                    return 4 * ByteConstants.MB;
                } else if (maxMemory < 64 * ByteConstants.MB) {
                    return 6 * ByteConstants.MB;
                } else {
                    // We don't want to use more ashmem on Gingerbread for now, since it doesn't respond well to
                    // native memory pressure (doesn't throw exceptions, crashes app, crashes phone)
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        return 8 * ByteConstants.MB;
                    } else {
                        return maxMemory / 2;
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            return 8 * ByteConstants.MB;
        }
    }

    public static class EncodedMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {

        private static final int MAX_CACHE_ENTRIES = Integer.MAX_VALUE;
        private static final int MAX_EVICTION_QUEUE_ENTRIES = MAX_CACHE_ENTRIES;

        @Override
        public MemoryCacheParams get() {

            final int maxCacheSize = getMaxCacheSize();
            final int maxCacheEntrySize = maxCacheSize / 4;

            return new MemoryCacheParams(
                    maxCacheSize,
                    MAX_CACHE_ENTRIES,
                    maxCacheSize,
                    MAX_EVICTION_QUEUE_ENTRIES,
                    maxCacheEntrySize);
        }

        private int getMaxCacheSize() {

            final int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), Integer.MAX_VALUE);

            if (maxMemory < 16 * ByteConstants.MB) {
                return 1 * ByteConstants.MB;
            } else if (maxMemory < 32 * ByteConstants.MB) {
                return 2 * ByteConstants.MB;
            } else {
                return 4 * ByteConstants.MB;
            }
        }
    }
}
