package service;

import model.Conf;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImageHandler implements QuickHandler {

    private ThreadPoolExecutor poolExecutor;


    public ImageHandler() {

        BlockingQueue<Runnable> runnableBlockingQueue = new LinkedBlockingQueue<>();
        int CORE_COUNT = Runtime.getRuntime().availableProcessors();
        int CORE_POOL_SIZE = CORE_COUNT + 1;
        int CORE_POOL_MAX_SIZE = CORE_COUNT * 2 + 1;
        int KEEP_ALIVE = 10;
        poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_MAX_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, runnableBlockingQueue);

    }

    @Override
    public void handlerImage(Conf conf, File file) {

        Runnable runnable = () -> {
            try {
                thumbnail(conf, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        poolExecutor.execute(runnable);
    }


    private void thumbnail(Conf conf, File file) throws IOException {


        /**
         * 优先处理翻转，因为如果有水印添加，则翻转过后水印也会被翻转
         */

        BufferedImage source = ImageIO.read(file);

        int rw = source.getWidth();
        int rh = source.getHeight();

        //翻转图片
        if (conf.isRetro()) {


            AffineTransform transform = null;

            switch (conf.getRetroType()) {


                case "上下翻转":

                    transform = new AffineTransform(1, 0, 0, -1, 0, source.getHeight() - 1);

                    break;

                case "左右翻转":

                    transform = new AffineTransform(-1, 0, 0, 1, source.getWidth() - 1, 0);

                    break;
            }

            if (transform != null) {
                AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

                BufferedImage image = op.filter(source, null);

                file = new File(conf.getOutputPath() + "/" + file.getName());

                ImageIO.write(image, "jpg", file);

            }

        }


        Thumbnails.Builder<File> builder = Thumbnails.of(file);

        //缩放方式
        switch (conf.getSCALE_TYPE()) {
            case 10:

                builder.size(rw, rh);
                break;
            case 20:

                double d = conf.getScaleB() / 100.0;
                builder.scale(d);

                break;
            case 30:
                int w = (int) conf.getScaleW();
                int h = (int) conf.getScaleH();

                if (w == 0 || h == 0) {
                    builder.size(rw, rh);
                } else {
                    builder.size(w, h);
                }

                break;
        }

        //旋转图片

        if (conf.isRotation()) {

            double r = conf.getRotationCount();

            builder.rotate(r);

        }


        //添加水印
        if (conf.isAddWater()) {

            //添加文字水印
            if (conf.isAddTextWater()) {

                String text = conf.getTextWater();


                builder.watermark(conf.getPositions(), drawTranslucentStringPic(text), conf.getWaterTrans());


            } else {//添加图片水印

                try {

                    builder.watermark(conf.getPositions(), ImageIO.read(new File(conf.getWaterPath())), conf.getWaterTrans());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        //输出质量


        System.out.println(conf.getImageQuality());
        builder.outputQuality(conf.getImageQuality());
        String name = "";

        if (!conf.getFormat().equals("不改变")) {
            builder.outputFormat(conf.getFormat());

            int l = file.getName().lastIndexOf(".");

            name = file.getName().substring(0, l);
        } else {

            name = file.getName();
        }


        builder.toFile(conf.getOutputPath() + "/" + name);

    }


    public BufferedImage createImage(String text) {

        Font font = new Font("宋体", Font.PLAIN, 30);

        // 获取font的样式应用在str上的整个矩形
        int[] arr = getWidthAndHeight(text, font);
        int width = arr[0];
        int height = arr[1];
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);//创建图片画布
        Graphics g = image.getGraphics();


        g.setColor(Color.WHITE); // 先用白色填充整张图片,也就是背景
        g.fillRect(0, 0, width, height);//画出矩形区域，以便于在矩形区域内写入文字
        g.setColor(Color.black);// 再换成黑色，以便于写入文字
        g.setFont(font);// 设置画笔字体
        g.drawString(text, 0, font.getSize());// 画出一行字符串
        g.drawString(text, 0, 2 * font.getSize());// 画出第二行字符串，注意y轴坐标需要变动
        g.dispose();

//        File file=new File("/Users/legend/Desktop/test.png");

//        ImageIO.write(image, "png", file);// 输出png图片

        return image;
    }


    private static int[] getWidthAndHeight(String text, Font font) {
        Rectangle2D r = font.getStringBounds(text, new FontRenderContext(
                AffineTransform.getScaleInstance(1, 1), false, false));
        int unitHeight = (int) Math.floor(r.getHeight());//
        // 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
        int width = (int) Math.round(r.getWidth()) + 1;
        // 把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
        int height = unitHeight + 3;

        return new int[]{width, height};
    }


    private BufferedImage drawTranslucentStringPic(String drawStr) {

        Font font = new Font("宋体", Font.PLAIN, 30);

        int[] arr = getWidthAndHeight(drawStr, font);
        int width = arr[0];
        int height = arr[1];
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();


        //设置透明  start
        buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        gd = buffImg.createGraphics();
        //设置透明  end
        gd.setFont(font); //设置字体
        gd.setColor(Color.WHITE); //设置颜色
//        gd.drawRect(0, 0, width - 1, height - 1); //画边框
        gd.drawString(drawStr, 0, font.getSize()); //输出文字（中文横向居中）
        return buffImg;
    }


}
