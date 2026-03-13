package com.nanyang.academy.exception;

/**
 * @author pt
 * @date 2022-06-02
 */
public class UserException extends BaseException{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
