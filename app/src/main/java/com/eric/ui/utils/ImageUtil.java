package com.eric.ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 照片处理工具类
 *
 * @author Administrator
 */
public class ImageUtil {


    private static ImageUtil instance = new ImageUtil();
    /**
     * 密度
     */
    public float mdensity = 1.0f;
    /**
     * TAG
     */
    static final String TAG = "PhotoGrid_Utils";

    private ImageUtil() {

    }

    /**
     * 获取单例
     *
     * @return 单例
     */
    public static ImageUtil getInstance() {
        return instance;
    }

    /**
     * 根据指定宽高获取图片
     *
     * @param bitmap Bitmap图片对象
     * @param width  目标宽度
     * @param height 目标高度
     * @return 处理后的bitmap
     */

    public Bitmap getImageByWH(Bitmap bitmap, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        // 缩放图片的尺寸
        float scaleWidth = (float) width / bmpWidth; // 按固定大小缩放 sWidth 写多大就多大
        float scaleHeight = (float) height / bmpHeight; //
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 产生缩放后的Bitmap对象
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);
        bitmap.recycle();
        return resizeBitmap;
    }

    //

    /**
     * 按照固定的比例大小读取内存中的图片
     *
     * @param path   持久化文件路径
     * @param length 源bitmap尺寸
     * @return 处理后的Bitmap
     */
    public Bitmap getBitmapByScale(String path, int length) {
        Bitmap mBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 由于设置inJustDecodeBounds为true，因此执行下面代码后bitmap为空
        mBitmap = BitmapFactory.decodeFile(path, options);
        // 计算缩放比例，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int scale = 0;
        if (options.outWidth >= options.outHeight) {
            scale = (int) (options.outWidth / (float) length);
        } else {
            scale = (int) (options.outHeight / (float) length);
        }
        // 因为结果为int型，如果相除后值为0.n，则最终结果将是0
        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = scale;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds设回false
        options.inJustDecodeBounds = false;
        mBitmap = BitmapFactory.decodeFile(path, options);
        return mBitmap;
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap 源图片
     * @return 转成base64流
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {

        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {

            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data base64流
     * @return bitmap对象
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 判断对应的文件是否是bitmap
     *
     * @param file 文件
     * @return 判断对应的文件是否是bitmap
     */
    public static boolean isBitmapFromFile(File file) {
        boolean bflag = false;
        try {
            if (file != null && file.exists() && file.isFile() && file.length() > 0) {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
                bflag = opts.outWidth > 0 && opts.outHeight > 0;
            } else {
                bflag = false;
            }
//			if(!bflag){
//				LogUtil.e(TAG, "file:file is damaged");
//			}
        } catch (Exception e) {

        }
        return bflag;
    }

    /**
     * 针对旋转问题来创建图片
     *
     * @param baseBitmap 被处理的bitmap
     * @param degree     0,90,180,270,360
     * @return 旋转后的图片
     */
    public Bitmap createBitmapFixedDegree(Bitmap baseBitmap, int degree) {
        int des_width = 0;
        int des_height = 0;
        if (degree % 180 != 0) {
            des_width = baseBitmap.getHeight();
            des_height = baseBitmap.getWidth();
        } else {
            des_width = baseBitmap.getWidth();
            des_height = baseBitmap.getHeight();
        }
        Matrix matrix = new Matrix();
        matrix.postTranslate(-baseBitmap.getWidth() / 2.0f, -baseBitmap.getHeight() / 2.0f); //为旋转先做一次，不然旋转之后图片位移有问题
        matrix.postRotate(degree);
        matrix.postTranslate(des_width / 2.0f, des_height / 2.0f);
        Bitmap bitmap = null;
        try {
            bitmap = createBitmap(des_width, des_height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            bitmap = null;

        } catch (NullPointerException e) {
            bitmap = null;

        }
        if (bitmap == null) {
            try {
                bitmap = createBitmap(des_width / 2, des_height / 2, Bitmap.Config.ARGB_8888);
                matrix.postScale(0.5f, 0.5f);
            } catch (OutOfMemoryError ex) {
                bitmap = null;

            } catch (NullPointerException ex) {
                bitmap = null;

            }
        }
        if (bitmap == null) {
            return null;
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        baseBitmap.recycle();
        return bitmap;
    }

    /**
     * 针对旋转和缩放问题来创建图片
     *
     * @param baseBitmap 被处理的bitmap
     * @param x_scale    水平缩放比例
     * @param y_scale    垂直缩放比例
     * @param degree     旋转的角度
     * @return 针对旋转和缩放问题来创建图片
     */
    private Bitmap createBitmap(Bitmap baseBitmap, float x_scale, float y_scale, int degree) {
        int des_width = 0;
        int des_height = 0;
        if (degree % 180 != 0) {
            des_width = baseBitmap.getHeight();
            des_height = baseBitmap.getWidth();
        } else {
            des_width = baseBitmap.getWidth();
            des_height = baseBitmap.getHeight();
        }
        des_width = (int) (des_width * x_scale);
        des_height = (int) (des_height * y_scale);
        Matrix matrix = new Matrix();
        matrix.postTranslate(-baseBitmap.getWidth() / 2.0f, -baseBitmap.getHeight() / 2.0f); //为旋转先做一次，不然旋转之后图片位移有问题
        matrix.postRotate(degree);
        matrix.postScale(x_scale, y_scale);
        matrix.postTranslate(des_width / 2.0f, des_height / 2.0f);
        Bitmap bitmap = createBitmap(des_width, des_height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            return null;
        }
        Canvas canvas = new Canvas(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(baseBitmap, matrix, paint);
        baseBitmap.recycle();
        return bitmap;
    }

    /**
     * 封装了createBitmap的方法
     *
     * @param width  限定裁剪宽
     * @param height 限定裁剪高
     * @param config bitmap配置
     * @return 裁剪后的 bitmap
     */
    public Bitmap createBitmap(int width, int height, Bitmap.Config config) {
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        //第一次加载
        boolean isError = false;
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, config);
        } catch (NullPointerException e) {

            isError = true;
        } catch (IllegalArgumentException e) {

            isError = true;
        }
        if (bitmap == null || isError) {
            isError = false;
            //第二次加载
            try {
                bitmap = Bitmap.createBitmap(width, height, config);
            } catch (NullPointerException e) {

                isError = true;
            } catch (IllegalArgumentException e) {

                isError = true;
            }
        }
        if (bitmap == null || isError) {
            isError = false;
            //第三次加载
            try {
                bitmap = Bitmap.createBitmap(width, height, config);
            } catch (NullPointerException e) {

                isError = true;
            } catch (IllegalArgumentException e) {

                isError = true;
            }
        }

        return bitmap;
    }
}
