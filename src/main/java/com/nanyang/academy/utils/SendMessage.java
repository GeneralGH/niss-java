package com.nanyang.academy.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2022/7/5
 * @Version 1.0
 **/
public class SendMessage {
    /**
     * 需要修改
     */
    public final static String ACCESS_KEY_ID = "LTAI5t9385LTtX67xYnNy8PH";
    public final static String ACCESS_KEY_SECRET = "fyLTdgpHALNpxSBuVLqXXiq1HFo2go";
    /**
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    public static final String product = "Dysmsapi";
    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    public static final String domain = "dysmsapi.aliyuncs.com";
    /**
     * 短信验证公司名称
     */
    public final static String SIGN_NAME = "徐州国云信息科技有限公司";
    /**
     * 模版
     */
    public final static String TEMPLATE_CODE = "SMS_251010532";

    public final static String OK = "OK";

    /**
     * 阿里云
     *
     * @param tel
     * @param code
     */
    public static boolean sendMessage(String tel, String code) {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
            request.setPhoneNumbers(tel);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(SIGN_NAME);
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(TEMPLATE_CODE);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            StringBuilder text = new StringBuilder("{\"code\":\"")
                    .append(code)
                    .append("\"}");
            request.setTemplateParam(text.toString());
            // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)，request.setSmsUpExtendCode("90997");可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者，request.setOutId("yourOutId");,请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals(OK)) {
                return true;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendMeetingMessage(String tel, String name, String date, String address, String username, Integer type, String templateCode) {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
            request.setPhoneNumbers(tel);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(SIGN_NAME);
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            StringBuilder text = new StringBuilder();
            // 会议邀请通知
            if (type == 1) {
                text.append("{\"name\":\"")
                        .append(name)
                        .append("\"")
                        .append(",\"date\":\"")
                        .append(date)
                        .append("\"")
                        .append(",\"address\":\"")
                        .append(address)
                        .append("\"")
                        .append(",\"username\":\"")
                        .append(username)
                        .append("\"}");
            }
            // 会议取消通知
            if (type == 2) {
                text.append("{\"date\":\"")
                        .append(date)
                        .append("\"")
                        .append(",\"name\":\"")
                        .append(name)
                        .append("\"")
                        .append(",\"username\":\"")
                        .append(username)
                        .append("\"}");
            }
            request.setTemplateParam(text.toString());
            // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)，request.setSmsUpExtendCode("90997");可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者，request.setOutId("yourOutId");,请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals(OK)) {
                return true;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
