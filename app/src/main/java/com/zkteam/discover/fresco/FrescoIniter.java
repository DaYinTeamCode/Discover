package com.zkteam.discover.fresco;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

/**
 * Fresco 初始化类
 */
public class FrescoIniter {

    private static PipelineDraweeControllerBuilderSupplier sDraweeControllerBuilderSupplier;

    public static void initialize(Context context) {

        ImagePipelineFactory.initialize(context);
        initializeDrawee(context);
    }

    public static void initialize(Context context, ImagePipelineConfig imagePipelineConfig) {

        ImagePipelineFactory.initialize(imagePipelineConfig);
        initializeDrawee(context);
    }

    private static void initializeDrawee(Context context) {

        sDraweeControllerBuilderSupplier = new PipelineDraweeControllerBuilderSupplier(context);
        FrescoImageView.initialize(sDraweeControllerBuilderSupplier);
    }

    public static PipelineDraweeControllerBuilderSupplier getDraweeControllerBuilderSupplier() {

        return sDraweeControllerBuilderSupplier;
    }

    public static PipelineDraweeControllerBuilder newDraweeControllerBuilder() {

        return sDraweeControllerBuilderSupplier.get();
    }

    public static ImagePipelineFactory getImagePipelineFactory() {

        return ImagePipelineFactory.getInstance();
    }

    public static ImagePipeline getImagePipeline() {

        return getImagePipelineFactory().getImagePipeline();
    }

    /**
     * 此方法不需要调用，因在应用当中只初始化一次，如调用之后必须得重调用initialize 进行初始化
     */
    public static void shutDown() {

        sDraweeControllerBuilderSupplier = null;
        FrescoImageView.shutDown();
        ImagePipelineFactory.shutDown();
    }
}
