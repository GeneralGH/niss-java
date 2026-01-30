package com.nanyang.academy.utils.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nanyang.academy.common.Constant;
import com.nanyang.academy.config.HengYuanConfig;
import com.nanyang.academy.entity.pojo.HttpResult;
import com.nanyang.academy.utils.http.HttpUtil;
import com.nanyang.academy.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/8/2
 * @Version 1.0
 **/
public class WxUtil {
    static final Logger log = LoggerFactory.getLogger(WxUtil.class);

    /**
     * 推送消息
     *
     * @param openId
     * @param message
     * @return
     */
    public static boolean messageSend(String token,String openId, Object message) {
        String url = Constant.uniformSend + token;
        // 这里的参数要和下面的Map Key值对应
        JSONObject obj = new JSONObject();
        JSONObject mpTemplateMsg = new JSONObject();
        JSONObject mini = new JSONObject();
        mini.put("appid", Constant.APP_ID);
        mini.put("pagepath", Constant.pagepath);//pagepath
        mpTemplateMsg.put("data", message);
        mpTemplateMsg.put("miniprogram", mini);
        mpTemplateMsg.put("url", Constant.pagepath);
        ///微信公众号appid
        mpTemplateMsg.put("appid", Constant.appId);
        ///微信公众号模板id
        mpTemplateMsg.put("template_id", Constant.TEMP_ID);
        obj.put("touser", openId);
        obj.put("mp_template_msg", mpTemplateMsg);
        //obj.put("access_token",token);
        String res = HttpUtils.sendPost(url,obj.toJSONString());
        JSONObject object = JSON.parseObject(res);
        log.info(res);
        Integer errcode = (Integer) object.get("errcode");
        if (errcode == 0) {
            log.info("消息推送成功");
            return true;
        } else if (errcode == 40003) {
            log.error("推送消息的openid错误,openid:{},消息内容：{}", openId, message);
            return false;
        } else if (errcode == 43004) {
            log.error("该用户未关注公众号,openid:{},消息内容：{}", openId, message);
            return false;
        } else {
            return false;
        }
    }

    public static boolean interest(String token,String openId, Object message) {
        String url = Constant.uniformSend + token;
        // 这里的参数要和下面的Map Key值对应
        JSONObject obj = new JSONObject();
        JSONObject mpTemplateMsg = new JSONObject();
        JSONObject mini = new JSONObject();
        mini.put("appid", Constant.APP_ID);
        mini.put("pagepath", Constant.pagepath);//pagepath
        mpTemplateMsg.put("data", message);
        mpTemplateMsg.put("miniprogram", mini);
        mpTemplateMsg.put("url", Constant.pagepath);
        ///微信公众号appid
        mpTemplateMsg.put("appid", Constant.appId);
        ///微信公众号模板id
        mpTemplateMsg.put("template_id", "Constant.TEMP_ID");
        obj.put("touser", openId);
        obj.put("mp_template_msg", mpTemplateMsg);
        //obj.put("access_token",token);
        String res = HttpUtils.sendPost(url,obj.toJSONString());
        JSONObject object = JSON.parseObject(res);
        log.info(res);
        Integer errcode = (Integer) object.get("errcode");
        if (errcode == 43004) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean interestService(String token,String openId, Object message) {
        String url = Constant.uniformSend + token;
        // 这里的参数要和下面的Map Key值对应
        JSONObject obj = new JSONObject();
        JSONObject mpTemplateMsg = new JSONObject();
        JSONObject mini = new JSONObject();
        mini.put("appid", Constant.APP_ID_SERVICE);
        mini.put("pagepath", Constant.pagepathService);//pagepath
        mpTemplateMsg.put("data", message);
        mpTemplateMsg.put("miniprogram", mini);
        mpTemplateMsg.put("url", Constant.pagepathService);
        ///微信公众号appid
        mpTemplateMsg.put("appid", Constant.appId);
        ///微信公众号模板id
        mpTemplateMsg.put("template_id", "Constant.TEMP_ID");
        obj.put("touser", openId);
        obj.put("mp_template_msg", mpTemplateMsg);
        //obj.put("access_token",token);
        String res = HttpUtils.sendPost(url,obj.toJSONString());
        JSONObject object = JSON.parseObject(res);
        log.info(res);
        Integer errcode = (Integer) object.get("errcode");
        if (errcode == 43004) {
            return false;
        } else {
            return true;
        }
    }

    public static String getAccessToken() {
        //String urltoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                //+ Constant.APP_ID + "&secret=" + Constant.APP_SECT;
                //+ Constant.appId + "&secret=" + Constant.appSecret;
        String url = "https://api.weixin.qq.com/cgi-bin/stable_token";
        Map map = new HashMap();
        map.put("appid",Constant.APP_ID);
        map.put("secret",Constant.APP_SECT);
        map.put("grant_type","client_credential");
        String token = "";
        try {
            String res = HttpUtils.sendPost(url,JSON.toJSONString(map));
            //String res1 = HttpUtils.sendGet(urltoken);
            JSONObject obj = JSON.parseObject(res);
            token = obj.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static String getAccessTokenService() {
        //String urltoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
        //+ Constant.APP_ID + "&secret=" + Constant.APP_SECT;
        //+ Constant.appId + "&secret=" + Constant.appSecret;
        String url = "https://api.weixin.qq.com/cgi-bin/stable_token";
        Map map = new HashMap();
        map.put("appid",Constant.APP_ID_SERVICE);
        map.put("secret",Constant.APP_SECT_SERVICE);
        map.put("grant_type","client_credential");
        String token = "";
        try {
            String res = HttpUtils.sendPost(url,JSON.toJSONString(map));
            //String res1 = HttpUtils.sendGet(urltoken);
            JSONObject obj = JSON.parseObject(res);
            token = obj.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static void main(String[] args) {
        //String code = "021lIqml2XzNqa43WFll2GpdwB0lIqml";
        //String openid = getOpenId(code);
        //log.info("====openid====" + openid);
        //String openid = "oYMQF5KW4V1XQPHBM4RloL6hwg_8";
        String openid = "oYMQF5PVpyACV7bmEFFUy0uTPEBo";

        String accessToken = getAccessTokenService();
        WxMessageBean messageBean = new WxMessageBean();
        messageBean.setFirst(new MessageBean("国云智能服务会话转交提醒", "#173177"));
        messageBean.setKeyword1(new MessageBean("国云智能服务", "#173177"));
        messageBean.setKeyword2(new MessageBean("您有一条来自客服转交会话，请及时接收", "#173177"));
        System.out.println(messageSendService(accessToken,openid, messageBean));

        //getAccessToken();
    }
    public static String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.APP_ID +
                "&secret=" + Constant.APP_SECT + "&js_code=" + code + "&grant_type=authorization_code";
        String openId = "";
        HttpUtil util = new HttpUtil();
        try {
            HttpResult httpResult = util.doGet(url, null, null);
            if (httpResult.getStatusCode() == 200) {
                JsonParser jsonParser = new JsonParser();
                JsonObject obj = (JsonObject) jsonParser.parse(httpResult.getBody());
                log.info(obj.toString());
                if (obj.get("errcode") != null) {
                    return openId;
                } else {
                    openId = obj.get("openid").toString().replace("\"", "");
//                    map.put("openId", obj.get("openid").toString().replace("\"", ""));
//                    map.put("session_key", obj.get("session_key").toString().replace("\"", ""));
                    return openId;
                }
                //return httpResult.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openId;
    }

    public static String getOpenIdService(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.APP_ID_SERVICE +
                "&secret=" + Constant.APP_SECT_SERVICE + "&js_code=" + code + "&grant_type=authorization_code";
        String openId = "";
        HttpUtil util = new HttpUtil();
        try {
            HttpResult httpResult = util.doGet(url, null, null);
            if (httpResult.getStatusCode() == 200) {
                JsonParser jsonParser = new JsonParser();
                JsonObject obj = (JsonObject) jsonParser.parse(httpResult.getBody());
                log.info(obj.toString());
                if (obj.get("errcode") != null) {
                    return openId;
                } else {
                    openId = obj.get("openid").toString().replace("\"", "");
//                    map.put("openId", obj.get("openid").toString().replace("\"", ""));
//                    map.put("session_key", obj.get("session_key").toString().replace("\"", ""));
                    return openId;
                }
                //return httpResult.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openId;
    }

    public static boolean messageSendService(String token,String openId, Object message) {
        String url = Constant.uniformSend + token;
        // 这里的参数要和下面的Map Key值对应
        JSONObject obj = new JSONObject();
        JSONObject mpTemplateMsg = new JSONObject();
        JSONObject mini = new JSONObject();
        mini.put("appid", Constant.APP_ID_SERVICE);
        mini.put("pagepath", Constant.pagepathService);//pagepath
        mpTemplateMsg.put("data", message);
        mpTemplateMsg.put("miniprogram", mini);
        mpTemplateMsg.put("url", Constant.pagepathService);
        ///微信公众号appid
        mpTemplateMsg.put("appid", Constant.appId);
        ///微信公众号模板id
        mpTemplateMsg.put("template_id", Constant.TEMP_ID);
        obj.put("touser", openId);
        obj.put("mp_template_msg", mpTemplateMsg);
        //obj.put("access_token",token);
        String res = HttpUtils.sendPost(url,obj.toJSONString());
        JSONObject object = JSON.parseObject(res);
        log.info(res);
        Integer errcode = (Integer) object.get("errcode");
        if (errcode == 0) {
            log.info("消息推送成功");
            return true;
        } else if (errcode == 40003) {
            log.error("推送消息的openid错误,openid:{},消息内容：{}", openId, message);
            return false;
        } else if (errcode == 43004) {
            log.error("该用户未关注公众号,openid:{},消息内容：{}", openId, message);
            return false;
        } else {
            return false;
        }
    }


    /**
     * 保存微信头像
     * @line https://blog.csdn.net/neu_yousei/article/details/22413855
     * @param wechatHeadimgurl 微信头像url
     */
    public void saveWechatHeadimg(String wechatHeadimgurl) {
        if (wechatHeadimgurl != null) {
            InputStream inputStream = null;
            HttpURLConnection httpURLConnection = null;
            FileOutputStream fileOutputStream = null;
            try {
                URL url = new URL(wechatHeadimgurl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // 设置网络连接超时时间
                httpURLConnection.setConnectTimeout(10000);
                // 设置应用程序要从网络连接读取数据
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器返回一个输入流
                    inputStream = httpURLConnection.getInputStream();
                }
                if (inputStream != null) {
                    // 创建文件夹
                    String filePath = HengYuanConfig.getAvatarPath() + url.getPath() + "/";
                    String[] paths = filePath.split("/");
                    String fileName = paths[paths.length - 1] + ".jpg";

                    byte[] data = new byte[1024];
                    int len;
                    StringBuilder fullPath = new StringBuilder();
                    for (int i = 0; i < paths.length; i++) {
                        fullPath.append(paths[i]).append("/");
                        File file = new File(fullPath.toString());
                        if (!file.exists()) {
                            file.mkdir();
                        }
                    }
                    // 文件写入
                    fileOutputStream = new FileOutputStream(filePath + fileName);
                    while ((len = inputStream.read(data)) != -1) {
                        fileOutputStream.write(data, 0, len);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
}
