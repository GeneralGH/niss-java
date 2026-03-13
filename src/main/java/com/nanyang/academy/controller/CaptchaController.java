package com.nanyang.academy.controller;

import com.google.code.kaptcha.Producer;
import com.nanyang.academy.common.Constant;
import com.nanyang.academy.common.HttpStatus;
import com.nanyang.academy.common.ResultEntity;
import com.nanyang.academy.config.HengYuanConfig;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.mapper.SysUserMapper;
import com.nanyang.academy.service.SysConfigService;
import com.nanyang.academy.utils.base.Base64;
import com.nanyang.academy.utils.msgVerify.PhoneFormatCheckUtils;
import com.nanyang.academy.utils.msgVerify.RandomUtil;
import com.nanyang.academy.utils.uuid.IdUtil;
import com.nanyang.academy.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author pt
 * @date 2022-06-02
 */
@RestController
@RequestMapping("/api/code")
@Api(tags = "验证码")
public class CaptchaController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysConfigService configService;

    @Autowired
    private SysUserMapper userMapper;
    /**
     * 生成验证码
     */
    @ApiOperation("获取验证码图片")
    @GetMapping("/captchaImage")
    public ResultEntity getCode(HttpServletResponse response) throws IOException
    {
        ResultEntity ajax = ResultEntity.getOkResult();
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        Map data = new HashMap();

        data.put("captchaOnOff", captchaOnOff);
        if (!captchaOnOff)
        {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = Constant.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = HengYuanConfig.getCaptchaType();
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
            logger.info(capStr+"----------"+code);
        }

        redisCache.setCacheObject(verifyKey, code, Constant.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        //logger.
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return ResultEntity.getErrorResult(HttpStatus.ERROR,e.getMessage());
        }

        data.put("uuid", uuid);
        data.put("img", Base64.encode(os.toByteArray()));
        return new ResultEntity<>(HttpStatus.SUCCESS,"获取成功",data);
    }


    @ApiOperation(value = "获取短信验证码")
    @GetMapping("/getSmsSend")
    public ResultEntity getSmsSend(@RequestParam String phone) {
        /**获取短信验证码*/
        boolean isPhone = PhoneFormatCheckUtils.isPhoneLegal(phone);
        if (!isPhone) {
            throw new CustomException("手机号格式不正确");
        }
        String code = RandomUtil.getNumber();
        throw new CustomException("短信发送失败");
        /*boolean flg = SendMessage.sendMessage(phone, code);*/
        //SendMessage.sendMessage(userTel, code);
        /*if (!flg) {
            throw new CustomException("短信发送失败");
        }
        redisCache.setCacheObject(Constant.PREFIX_VERIFY_CODE + phone, code, Constant.PREFIX_CODE_TIME,TimeUnit.SECONDS);
        return new ResultEntity(org.springframework.http.HttpStatus.OK.value(), "获取成功");*/
    }
}
