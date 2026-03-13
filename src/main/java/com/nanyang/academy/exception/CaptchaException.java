package com.nanyang.academy.exception;

/**
 * @author pt
 * @date 2022-06-02
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.jcaptcha.error", null);
    }
}