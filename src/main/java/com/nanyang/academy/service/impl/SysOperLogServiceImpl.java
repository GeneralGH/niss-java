package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.entity.SysOperLog;
import com.nanyang.academy.entity.param.OperLogQueryParam;
import com.nanyang.academy.mapper.SysOperLogMapper;
import com.nanyang.academy.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog)
    {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog)
    {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param param 查询条件
     * @return 操作日志集合
     */
    @Override
    public IPage<SysOperLog> selectOperLogListPage(OperLogQueryParam param)
    {
        Page<SysOperLog> page = new Page<>(param.getPage(),param.getSize());
        QueryWrapper<OperLogQueryParam> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isEmpty(param.getTitle()))
            wrapper.like("title",param.getTitle());
        if (ObjectUtils.isEmpty(param.getBusinessType()))
            wrapper.like("business_type",param.getBusinessType());
        if (ObjectUtils.isEmpty(param.getBusinessTypes()))
            wrapper.in("business_type",param.getBusinessTypes());
        if (ObjectUtils.isEmpty(param.getStatus()))
            wrapper.eq("status",param.getStatus());
        if (ObjectUtils.isEmpty(param.getOperName()))
            wrapper.like("oper_name",param.getOperName());
        wrapper.orderByDesc("oper_id");
        return operLogMapper.selectOperLogListPage(page,wrapper);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds)
    {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId)
    {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog()
    {
        operLogMapper.cleanOperLog();
    }
}
