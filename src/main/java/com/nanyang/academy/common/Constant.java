package com.nanyang.academy.common;

/**
 * 常量
 *
 * @author
 * @date
 */
public class Constant {

    public static final String DEFAULT_WEBSOCKET_USERNAME = "ws_username:";

    public static final String DEFAULT_SESSION_USERNAME = "username:";

    public static final String ENOJI = "system:emoji";

    /**
     * 公众号消息模板
     */
    public static String TEMP_ID = "mtJPSWJ59pTE6wzyc7dzN2M_CfVF6QRamLA3g7PhAhg";
    /**
     * 小程序APP_ID
     */
    public static final String APP_ID = "wx1cf69896d5a9c178";
    /**
     * 小程序APP_SECT
     */
    public static final String APP_SECT = "5970d131c84807915415843daf175972";

    /**
     * 客服小程序APP_ID
     */
    public static final String APP_ID_SERVICE = "wx9e2c5f170ebb32b4";
    /**
     * 客服小程序APP_SECT
     */
    public static final String APP_SECT_SERVICE = "fa793100260b6436b40ab1154e1b1558";
    /**
     * 公众号消息推送URL
     */
    public static final String uniformSend = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=";
    /**
     * 客服小程序跳转的url
     */
    public static final String pagepathService = "pages/chat/chat";
    /**
     * 小程序跳转的url
     */
    public static final String pagepath = "pages/index/chat";
    /**
     * 公众号appId
     */
    public static final String appId = "wxee1f2cd119f3da21";
    /**
     * 公众号secret
     */
    public static final String appSecret = "wxee1f2cd119f3da21";
    /**
     * 小程序ACCESS_TOKEN获取URL
     */
    public static final String accessUrl = "https://api.weixin.qq.com/cgi-bin/token";



    /*public static final String APP_ID = "wx519f4c269fd7ab81";
    public static final String APP_SECT = "924f919f922cd7bb74f23b5d8d0d4ff0";*/

    /**
     * 用户咨询无结果次数
     */
    public static final String CONSULT_NO_ANSWER_NUM = "CONSULT_NO_ANSWER_NUM:";

    /**
     * 当天在线咨询人数
     */
    public static final String CONSULT_NUMBER_TOTAL = "CONSULT_NUMBER_TOTAL:";

    /**
     * 当前在线咨询人数
     */
    public static final String CONSULT_NUMBER = "consult:";

    /**
     * 在线客服当前接待人数
     */
    public static final String SERVICE_RECEPTION = "service:online:reception:";

    /**
     * 在线客服缓存
     */
    public static final String SERVICE_NAME = "service:online";

    /**
     * customer中的用户名 Key
     */
    public static final String CUSTOMER_NAME = "customer:";

    /**
     * customer创建的会话talking
     */
    public static final String CUSTOMER_NAME_TALKING = "customer:talking:";

    private Constant() {
    }

    /**
     * 黑名单词汇 cache key
     */
    public static final String SYS_BLACK_WORD = "sys_black_word:";

    /**
     * 敏感词汇 cache key
     */
    public static final String SYS_CERTAIN_WORD = "sys_certain_word:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";


    /**
     * 密码输入错误次数
     */
    public static final String PASSWORD_WRONG_COUNT = "password_wrong_count:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "ForEver ";

    public static final String TOKEN = "token";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";


    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-core:cache:
     */
    public static final String PREFIX_SHIRO_CACHE = "core:cache:";

    /**
     * 手机短信code
     */
    public static final String PREFIX_VERIFY_CODE = "verify:code:";

    /**
     * 短信code过期时间
     */
    public static final int PREFIX_CODE_TIME = 2 * 60;
    /**
     * redis-key-前缀-core:access_token:
     */
    public static final String PREFIX_ACCESS_TOKEN = "core:access_token:";

    /**
     * redis-key-前缀-core:refresh_token:
     */
    public static final String PREFIX_REFRESH_TOKEN = "core:refresh_token:";


    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 6;

    public static final String HTTP = "";

    public static final String HTTPS = "";

}
