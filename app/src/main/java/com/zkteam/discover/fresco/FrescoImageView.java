package com.zkteam.discover.fresco;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.zkteam.discover.util.TextUtil;


/**
 * 自定义fresco图片加载框架视图
 * <p>
 * Scaling 是一种画布操作，通常是由硬件加速的。图片实际大小保持不变，它只不过在绘制时被放大或缩小。
 * Resizing 是一种软件执行的管道操作。它返回一张新的，尺寸不同的图片。
 * Downsampling 同样是软件实现的管道操作。它不是创建一张新的图片，而是在解码时改变图片的大小。
 */
public class FrescoImageView extends GenericDraweeView {

    private static Supplier<? extends PipelineDraweeControllerBuilder> sDraweeControllerBuilderSupplier;
    private PipelineDraweeControllerBuilder mDraweeControllerBuilder;

    private BaseControllerListener mBaseControllerListener;//临听下载
    private Postprocessor mPostprocessor;//后置处理图片
    private boolean mSmallCache;//是否为小图，缓存至小图目录
    private static boolean mGlobalWindowVisibilityChangedAutoDetachEnable;
    private boolean mWindowVisibilityChangedAutoDetachEnable;
    private boolean mAutoDetached;

    public FrescoImageView(Context context) {

        super(context);
        init();
    }

    public FrescoImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public FrescoImageView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        mDraweeControllerBuilder = sDraweeControllerBuilderSupplier.get();
    }

    public static void initialize(Supplier<? extends PipelineDraweeControllerBuilder> draweeControllerBuilderSupplier) {

        sDraweeControllerBuilderSupplier = draweeControllerBuilderSupplier;
    }

    public static void shutDown() {

        sDraweeControllerBuilderSupplier = null;
    }

    public static void setGlobalOnWindowVisibilityChangedAutoDetachEnable(boolean enable){

        mGlobalWindowVisibilityChangedAutoDetachEnable = enable;
    }

    //###################### 初始化配置项 #########################


    /**
     * 默认占位图
     *
     * @param resourceId
     */
    public void setDefPlaceholderImage(int resourceId) {

        getHierarchy().setPlaceholderImage(resourceId);
    }

    /**
     * 失败默认占位图
     *
     * @param resourceId
     */
    public void setDefFailureImage(int resourceId) {

        getHierarchy().setFailureImage(resourceId);
    }

    /**
     * 设置圆角
     *
     * @param roundingParams
     */
    public void setRoundingParams(RoundingParams roundingParams) {

        getHierarchy().setRoundingParams(roundingParams);
    }

    /**
     * 设置fadeIn 效果
     *
     * @param fadeIn
     */
    public void setFadeIn(boolean fadeIn) {

        if (!fadeIn)
            getHierarchy().setFadeDuration(0);
    }

    /**
     * fresco 支持的缩放模式不支持ImageView ScaleType。
     *
     * @param scaleType
     */
    public void setFrescoScaleType(ScalingUtils.ScaleType scaleType) {

        getHierarchy().setActualImageScaleType(scaleType);
    }

    public ScalingUtils.ScaleType getFrescoScaleType() {

        return getHierarchy().getActualImageScaleType();
    }

    /**
     * 监听下载
     *
     * @param baseControllerListener 下载成功回调 onIntermediateImageSet,失败回调 onFailure
     */
    public void setBaseControllerListener(BaseControllerListener baseControllerListener) {

        mBaseControllerListener = baseControllerListener;
    }

    /**
     * 图片下载完成后图片处理
     *
     * @param postprocessor 下载加载完成后图片处理：process方法处理bitmap
     */
    public void setPostprocessor(Postprocessor postprocessor) {

        mPostprocessor = postprocessor;
    }

    /**
     * 设置加载图片进度显示效果
     *
     * @param progressBarDrawable
     */
    public void setProgressBarDrawable(ProgressBarDrawable progressBarDrawable) {

        getHierarchy().setProgressBarImage(progressBarDrawable);
    }

    public void setSmallCache() {

        mSmallCache = true;
    }

    public void setWindowVisibilityChangedAutoDetach(boolean autoDetach){

        mWindowVisibilityChangedAutoDetachEnable = autoDetach;
    }

    //###################### 图片加载项 #########################

    /* 加载res本地图片 */
    public void setImageResId(int resourceId) {

        setImageResIdResize(resourceId, null);
    }

    public void setImageResIdByLp(int resourceId) {

        if (getLayoutParams().width > 0 && getLayoutParams().height > 0)
            setImageResIdResize(resourceId, new ResizeOptions(getLayoutParams().width, getLayoutParams().height));
        else
            setImageResIdResize(resourceId, null);
    }

    public void setImageResIdResize(int resourceId, ResizeOptions resizeOptions) {

        setImageUriResize(Uri.parse("res:///" + resourceId), resizeOptions);
    }

    /* 加载图片url or sdcard图片路径 */
    public void setImageUri(String imageUri) {

        setImageUriResize(imageUri, null);
    }

    public void setImageUriByLp(String imageUri) {

        if (getLayoutParams().width > 0 && getLayoutParams().height > 0)
            setImageUriResize(imageUri, new ResizeOptions(getLayoutParams().width, getLayoutParams().height));
        else
            setImageUriResize(imageUri, null);
    }

    public void setImageUriResize(String imageUri, ResizeOptions resizeOptions) {

        imageUri = TextUtil.filterNull(imageUri);

        if (imageUri.startsWith("/"))
            imageUri = "file://" + imageUri;

        setImageUriResize(Uri.parse(imageUri), resizeOptions);
    }

    /* 加载uri图片 */
    public void setImageUri(Uri uri) {

        if (uri == null)
            uri = Uri.parse(TextUtil.TEXT_EMPTY);

        setImageUriResize(uri, null);
    }

    public void setImageUriByLp(Uri uri) {

        if (uri == null)
            uri = Uri.parse(TextUtil.TEXT_EMPTY);

        setImageUriResize(uri, new ResizeOptions(getLayoutParams().width, getLayoutParams().height));
    }

    public void setImageUriResize(Uri uri, ResizeOptions resizeOptions) {

        if (uri == null)
            uri = Uri.parse(TextUtil.TEXT_EMPTY);

        loadImageUri(uri, resizeOptions);
    }

    /**
     * 通过uri加载图片
     *
     * @param uri
     * @param resizeOptions
     */
    private void loadImageUri(final Uri uri, ResizeOptions resizeOptions) {

        ImageRequest request;
        if (mSmallCache) {

            request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(resizeOptions)//Resize 并不改变原始图片，它只在解码前修改内存中的图片大小
                    .setPostprocessor(mPostprocessor)
                    .setCacheChoice(ImageRequest.CacheChoice.SMALL)
                    .build();
        } else {

            request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(resizeOptions)
                    .setPostprocessor(mPostprocessor)
                    .build();
        }

        PipelineDraweeController controller = (PipelineDraweeController) mDraweeControllerBuilder.setControllerListener(mBaseControllerListener)
                .setUri(uri)
                .setImageRequest(request)
                .setTapToRetryEnabled(false)
                .setOldController(getController())  // 列表滚动 优化
                .setAutoPlayAnimations(true) //自动播放gif动画
                .build();

        setController(controller);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {

        super.onWindowVisibilityChanged(visibility);

        if (View.VISIBLE == visibility) {

            if(mAutoDetached){

                doAttach();
                mAutoDetached = false;
            }

        } else {

            if (mWindowVisibilityChangedAutoDetachEnable ||
                mGlobalWindowVisibilityChangedAutoDetachEnable) {

                doDetach();
                mAutoDetached = true;
            }
        }
    }

    public void attachImage(){

        doAttach();
    }

    public void detachImage(){

        doDetach();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        try {
            super.onDraw(canvas);
        } catch (Exception e) {
        }
    }
}
