package com.nanyang.academy.utils.file;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangtao
 * @date 2021/5/6 14:07
 */
public class QiniuCloudUtil {

    /**
     * 设置需要操作的账号的AK和SK,均在七牛云中获得
     */
    private static final String ACCESS_KEY = "Otzs95KYf3kXThOa12LltYeux8WqedOL8fqQCjDm";

    private static final String SECRET_KEY = "fIuqvBVDLyFDvXny6aLpH64dKYhqiWLMhIrMk-d5";

    /**
     * 要上传的空间（刚刚新建空间的名称）
     */
    private static final String BUCKET_NAME = "minjian";

    /**
     * 密钥
     */
    private static final Auth AUTH = Auth.create(ACCESS_KEY, SECRET_KEY);

    /**
     * 华北空间使用
     */
    private static final String HUA_NORTH = "https://upload-z1.qiniu.com/putb64/";

    /**
     * 华南空间使用
     */
//    private static final String HUA_SOUTH = "https://upload-z1.qiniu.com/putb64/";
    private static final String HUA_SOUTH = "http://up-z2.qiniu.com/putb64/";

    /**
     * 新建空间时，七牛云分配出的域名 （自己可在万网购买域名解析后，绑定到加速域名）
     */
    private static final String DOMAIN = "http://minjian.hengyuangx.cn/";

    /**
     * 构造一个带指定Zone对象的配置类
     */
    private static Configuration cfg = new Configuration(Zone.zone2());

    /**
     * 创建上传对象
     */
    private static UploadManager uploadManager = new UploadManager(cfg);

    public static String getUpToken() {
        return AUTH.uploadToken(BUCKET_NAME, null, 3600, new StringMap().put("insertOnly", 1));
    }

    /**
     * base64方式上传
     *
     * @param base64
     * @param key
     * @return
     * @throws Exception
     */
    public static Map<String, String> put64image(byte[] base64, String key) throws Exception {
        Map<String, String> map = new HashMap<>(2);
        String file64 = Base64.encodeToString(base64, 0);
//        file64 = UrlSafeBase64.encodeToString(resizeImageTo40K(file64));
        Integer len = base64.length;
        String url = HUA_SOUTH + len + "/key/" + UrlSafeBase64.encodeToString(key);
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
//        OkHttpClient client = new OkHttpClient();
//        okhttp3.Response response = client.newCall(request).execute();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier(new AllowAllHostnameVerifier());
        OkHttpClient client = builder.build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        //返回图片地址   https://q5jhgxz4q.bkt.clouddn.com/812bbd78-62d3-44bc-836c-9ee27ba4866a
        //用此地址可在网页中访问到上传的图片
        map.put("url", DOMAIN + key);
        map.put("fileName", key);
        return map;
    }


    /**
     * @param fileName 图片的文件名
     * @Explain 删除空间中的图片
     */
    public static void delete(String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        String key = fileName;
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(BUCKET_NAME, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    /**
     * base64转换成BufferedImage:
     *
     * @param base64string
     * @return
     */
    public static BufferedImage base64String2BufferedImage(String base64string) {
        BufferedImage image = null;
        try {
            InputStream stream = BaseToInputStream(base64string);
            image = ImageIO.read(stream);
        } catch (IOException e) {

        }
        return image;
    }

    /**
     * BufferedImage转换成base64，在这里需要设置图片格式，如下是jpg格式图片
     *
     * @param bufferedImage
     * @return
     */
    public static String imageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", stream);
        } catch (IOException e) {

        }
        return Base64.encodeToString(stream.toByteArray(), 0);
    }

    /**
     * Base64转换成InputStream:
     *
     * @param base64string
     * @return
     */
    private static InputStream BaseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return stream;
    }

    /**
     * 压缩图片至40k之内，将原图片的长宽分别压缩为原图片的1/3，如果图片大小大于40k，则继续进行压缩。
     *
     * @param base64Img
     * @return
     */
    /**
    public static String resizeImageTo40K(String base64Img) {
        try {
            BufferedImage src = base64String2BufferedImage(base64Img);
            BufferedImage output = Thumbnails.of(src).size(src.getWidth() / 3, src.getHeight() / 3).asBufferedImage();
            String base64 = imageToBase64(output);
            if (base64.length() - base64.length() / 8 * 2 > 40000) {
                output = Thumbnails.of(output).scale(1 / (base64.length() / 40000)).asBufferedImage();
                base64 = imageToBase64(output);
            }
            return base64;
        } catch (Exception e) {
            return base64Img;
        }


    }
     */
}
