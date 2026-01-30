package com.nanyang.academy.exception;

/**
 * @author pt
 * @date 2022-06-02
 */
public class CaptchaExpireException  extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super("user.jcaptcha.expire", null);
    }
}

