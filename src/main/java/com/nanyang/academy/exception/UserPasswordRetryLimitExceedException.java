package com.nanyang.academy.exception;

/**
 * @ClassName pt
 * @Description TODO
 * @Author pt
 * @Date 2023/7/8
 * @Version 1.0
 **/
public class UserPasswordRetryLimitExceedException extends UserException{
    private static final long serialVersionUID = 1L;
    public UserPasswordRetryLimitExceedException(Object[] objects)
    {
        super("user.password.retry.limit.count", objects);
    }
}
