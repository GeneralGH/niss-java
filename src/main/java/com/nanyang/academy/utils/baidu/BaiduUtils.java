package com.nanyang.academy.utils.baidu;

import com.baidu.aip.speech.AipSpeech;
import com.nanyang.academy.utils.file.ImageUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Base64;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/9/15
 * @Version 1.0
 **/
public class BaiduUtils {
    //设置APPID/AK/SK
    public static final String APP_ID = "32538556";//"27551680";


    //  填写网页上申请的appkey
    public static final String API_KEY = "HTqyLZp7cwyCpRyD56egiBEw";//"gbPx0kzQWtLooSVvrZh6VAin";


    // 填写网页上申请的APP SECRET
    public static final String SECRET_KEY = "1Uj4fCUwDWVV4B1Mk1YT4U2TzzTG8Beu";//"kUXSg3TTMgUAMeU4l6CQGupZteL0WgnO";


    private static boolean METHOD_RAW = false; // 默认以json方式上传音频文件
    // 需要识别的文件
    //private static String FILENAME = "D:\\test\\tmp_a05505.wav";
    // 文件格式, 支持pcm/wav/amr 格式，极速版额外支持m4a 格式
    private static String FORMAT = "wav";
    private static String CUID = "1234567JAVA";
    // 采样率固定值
    private static int RATE = 16000;

    private static String URL = "http://vop.baidu.com/server_api"; // 可以改为https

    private static int DEV_PID = 1537;

    //private int LM_ID;//测试自训练平台需要打开此注释

    private static String SCOPE = "audio_voice_assistant_get";
    public static String wav2TextByUrl(String url) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        byte[] data = ImageUtils.readFile(url);
        JSONObject res = client.asr(data, "wav", 16000, null);
        //JSONObject res = client.asr(filePath, "wav", 16000, null);
        String msg = res.get("result").toString();
        return msg.substring(2,msg.length()-2);
    }

    public static String wav2Text(String filePath) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        JSONObject res = client.asr(filePath, "wav", 16000, null);
        System.out.println(res);
        String msg = res.get("result").toString();
        return msg.substring(2,msg.length()-2);
    }

    public static void main(String[] args) throws IOException, DemoException {
        String fileName = "D:\\test\\tmp_a05505.wav";
        String res = wav2Text(fileName);
        System.out.println(res);
        BaiduUtils demo = new BaiduUtils();
        // 填写下面信息
        String result = wav2TextNew(fileName);
        System.out.println("识别结束：结果是：");
        System.out.println(result);

        /*// 如果显示乱码，请打开result.txt查看
        File file = new File("result.txt");
        FileWriter fo = new FileWriter(file);
        fo.write(result);
        fo.close();
        System.out.println("Result also wrote into " + file.getAbsolutePath());*/
    }


    public static String wav2TextNew(String fileName) throws IOException, DemoException {
        TokenHolder holder = new TokenHolder(API_KEY, SECRET_KEY, SCOPE);
        holder.resfresh();
        String token = holder.getToken();
        String result = null;
        if (METHOD_RAW) {
            result = runRawPostMethod(token,fileName);
        } else {
            result = runJsonPostMethod(token,fileName);
        }
        return result;
    }

    private static String runRawPostMethod(String token,String fileName) throws IOException, DemoException {
        String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&token=" + token;
        //测试自训练平台需要打开以下信息
        //String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&lm_id="+ LM_ID + "&token=" + token;
        String contentTypeStr = "audio/" + FORMAT + "; rate=" + RATE;
        //System.out.println(url2);
        byte[] content = getFileContent(fileName);
        HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", contentTypeStr);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(content);
        conn.getOutputStream().close();
        System.out.println("url is " + url2);
        System.out.println("header is  " + "Content-Type :" + contentTypeStr);
        String result = ConnUtil.getResponseString(conn);
        return result;
    }

    public static String runJsonPostMethod(String token,String fileName) throws DemoException, IOException {

        byte[] content = getFileContent(fileName);
        String speech = base64Encode(content);

        JSONObject params = new JSONObject();
        params.put("dev_pid", DEV_PID);
        //params.put("lm_id",LM_ID);//测试自训练平台需要打开注释
        params.put("format", FORMAT);
        params.put("rate", RATE);
        params.put("token", token);
        params.put("cuid", CUID);
        params.put("channel", "1");
        params.put("len", content.length);
        params.put("speech", speech);

        // System.out.println(params.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);


        params.put("speech", "base64Encode(getFileContent(FILENAME))");
        System.out.println("url is : " + URL);
        System.out.println("params is :" + params.toString());


        return result;
    }

    private static byte[] getFileContent(String filename) throws DemoException, IOException {
        File file = new File(filename);
        if (!file.canRead()) {
            System.err.println("文件不存在或者不可读: " + file.getAbsolutePath());
            throw new DemoException("file cannot read: " + file.getAbsolutePath());
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String base64Encode(byte[] content) {
         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
         String str = encoder.encodeToString(content);


        /*char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
        String str = new String(chars);*/

        return str;
    }
}
