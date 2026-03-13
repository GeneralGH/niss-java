package com.nanyang.academy.utils.file;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2023/10/25
 * @Version 1.0
 **/
public class PhotoUtils {

    public static void main(String[] args) {
        try {
            zoomImage(1000,800,"D:\\test\\123456.heic","D:\\test\\123456.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zoomImage(Integer width, Integer height, String srcPath, String newPath) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        if(width == null){//根据高度缩放图片
            op.resize(null, height);
        }else if(height == null){//根据宽度缩放图片
            op.resize(width, null);
        }else {
            op.resize(width, height);
        }
        op.addImage(newPath);
        //这里不加参数或者参数为false是使用ImageMagick，true是使用GraphicsMagick
        ConvertCmd convert = new ConvertCmd();
        //convert.setSearchPath("D:\\Program Files\\ImageMagick-7.1.1-Q16");
        //convert.setSearchPath("/usr/local/ImageMagick-7.1.1-Q16");
        try{
            convert.run(op);
            System.out.println("图片转换成功");
        }catch (Exception e){
            System.out.println("图片转换失败");
            e.printStackTrace();
        }
    }


    /**
     * 根据尺寸缩放图片
     * @param width 缩放后的图片宽度
     * @param height 缩放后的图片高度
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     * @param type 1为比例处理，2为大小处理，如（比例：1024x1024,大小：50%x50%）
     */
    public static String cutImage(int width, int height, String srcPath, String newPath,int type,String quality) throws Exception {
        IMOperation op = new IMOperation();
        ConvertCmd cmd = new ConvertCmd(true);
        op.addImage();
        String raw = "";
        if(type == 1){
            //按像素
            raw = width+"x"+height+"^";
        }else{
            //按像素百分比
            raw = width+"%x"+height+"%";
        }
        op.addRawArgs("-sample" ,  raw );
        if((quality !=null && quality.equals(""))){
            op.addRawArgs("-quality" ,  quality );
        }
        op.addImage();

        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.indexOf("win") != -1) {
            //linux下不要设置此值，不然会报错
            cmd.setSearchPath("C:\\Program Files\\GraphicsMagick-1.3.19-Q8");
        }

        try{
            cmd.run(op, srcPath, newPath);
        }catch(Exception e){
            e.printStackTrace();
        }
        return newPath;
    }
}
