package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanyang.academy.entity.SysRoleDept;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author HengYuanKeJi
 */
@Component
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {

     int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量删除角色部门关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     int deleteRoleDept(Long[] ids);

    /**
     * 查询部门使用数量
     *
     * @param deptId 部门ID
     * @return 结果
     */
     int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * 批量新增角色部门信息
     *
     * @param roleDeptList 角色部门列表
     * @return 结果
     */
     int batchRoleDept(List<SysRoleDept> roleDeptList);
}
