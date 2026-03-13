package com.nanyang.academy.exception.file;


import com.nanyang.academy.exception.BaseException;

/**
 * 文件信息异常类
 * 
 * @author pt
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
