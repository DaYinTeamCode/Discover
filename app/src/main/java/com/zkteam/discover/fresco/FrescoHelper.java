package com.zkteam.discover.fresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FrescoHelper {

    /**
     * 下载图片
     *
     * @param context
     * @param baseBitmapDataSubscriber onNewResultImpl进行对加载完成bitmap操作，下载失败回调onFailureImpl
     */
    public void downloadRequest(Context context, String url, BaseBitmapDataSubscriber baseBitmapDataSubscriber) {

        // 获取网络图片
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchDecodedImage(imageRequest, context);

        dataSource.subscribe(baseBitmapDataSubscriber, CallerThreadExecutor.getInstance());
    }

    /**
     * 下载图片监听未解码的原始数据的回调
     *
     * @param context
     * @param url
     * @param baseDataSubscriber
     * @return DataSource 被订阅对象 调用close可消息订阅释放资源
     */
    public DataSource downloadRequest(Context context, String url, BaseDataSubscriber baseDataSubscriber) {

        // 获取网络图片
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<PooledByteBuffer>> dataSource =
                imagePipeline.fetchEncodedImage(imageRequest, context);

        dataSource.subscribe(baseDataSubscriber, UiThreadImmediateExecutorService.getInstance());
        return dataSource;
    }

    /**
     * 高斯模糊处理器
     *
     * @return
     */
    public Postprocessor newBlurPostProcessor(final Context context, final int blurRadius) {

        return new BasePostprocessor() {

            @Override
            public void process(Bitmap destBitmap, Bitmap sourceBitmap) {

//                BitmapUtil.blurThisImage(context, sourceBitmap, destBitmap, blurRadius);
            }
        };
    }

    public void clearMemoryCache(Uri uri) {

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        if (isInMemoryCache(uri))
            imagePipeline.evictFromMemoryCache(uri);
    }

    public void clearDiskCache(Uri uri) {

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        if (isInDiskCache(uri.toString()))
            imagePipeline.evictFromDiskCache(uri);
    }

    public static void clearCache(Uri uri) {

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromCache(uri);
    }

    public static boolean isInMemoryCache(Uri uri) {

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        return imagePipeline.isInBitmapMemoryCache(uri);
    }

    public static boolean isInDiskCache(String uri) {

        return Fresco.getImagePipelineFactory().getMainFileCache().hasKey(new SimpleCacheKey(uri));
    }

    public static InputStream getDiskCacheFile(String uri) throws Exception {

        return Fresco.getImagePipelineFactory().getMainFileCache().getResource(new SimpleCacheKey(uri)).openStream();
    }

    public static boolean isInCache(Uri uri) {

        return isInMemoryCache(uri) || isInDiskCache(uri.toString());
    }

    public void asyncCheckInDiskCache(Uri uri, BaseDataSubscriber baseDataSubscriber) {

        //异步判断是否在磁盘中
        DataSource<Boolean> inDiskCacheSource = Fresco.getImagePipeline().isInDiskCache(uri);
        DataSubscriber<Boolean> subscriber = baseDataSubscriber;
//        DataSubscriber<Boolean> subscriber = new BaseDataSubscriber<Boolean>() {
//
//            @Override
//            protected void onNewResultImpl(DataSource<Boolean> dataSource) {
//                if (!dataSource.isFinished()) {
//                    return;
//                }
//                boolean isInCache = dataSource.getResult();
//            }
//
//            @Override
//            protected void onFailureImpl(DataSource<Boolean> dataSource) {
//
//            }
//        };
        inDiskCacheSource.subscribe(subscriber, new ThreadPoolExecutor(3, 127, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy()));
    }

    /**
     * 清除内存缓存数据
     */
    public static void clearMemoryCache() {

        try {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.clearMemoryCaches();
        } catch (Throwable t) {
        }
    }

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache() {

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearDiskCaches();
    }

    /**
     * 清除内存及磁盘缓存
     */
    public static void clearMemoryAndDiskCache() {

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }
}
