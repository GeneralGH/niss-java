package com.nanyang.academy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.common.Convert;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.SysDepartment;
import com.nanyang.academy.entity.SysRole;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.param.DeptQueryParam;
import com.nanyang.academy.entity.pojo.TreeSelect;
import com.nanyang.academy.exception.ServiceException;
import com.nanyang.academy.mapper.SysDepartmentMapper;
import com.nanyang.academy.mapper.SysRoleMapper;
import com.nanyang.academy.service.SysDepartmentService;
import com.nanyang.academy.utils.SecurityUtils;
import com.nanyang.academy.utils.SpringUtils;
import com.nanyang.academy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {

    @Autowired
    private SysDepartmentMapper deptMapper;

    @Autowired
    private SysRoleMapper roleMapper;


    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysDepartment> selectDeptList(SysDepartment dept)
    {
        return deptMapper.selectDeptList(dept);
    }
    /**
     * 分页查询部门数据
     *
     * @param param 查询条件
     * @return 部门信息集合
     */
    @Override
    public IPage<SysDepartment> selectDeptListPage(DeptQueryParam param) {
        Page<SysDepartment> page = new Page<>(param.getPage(),param.getSize());
        QueryWrapper<DeptQueryParam> wrapper = new QueryWrapper<>();
        //AND dept_id = #{deptId}
        wrapper.eq("d.is_delete",0);
        if (!ObjectUtils.isEmpty(param.getPId()))
            wrapper.eq("d.p_id",param.getPId());
        if (!ObjectUtils.isEmpty(param.getDeptName()))
            wrapper.like("d.p_id",param.getDeptName());
        if (!ObjectUtils.isEmpty(param.getStatus()))
            wrapper.eq("d.p_id",param.getStatus());
        wrapper.orderByAsc("d.p_id");
        wrapper.orderByAsc("d.sort");
        IPage<SysDepartment> list = deptMapper.selectDeptListPage(page,wrapper);
        return list;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDepartment> buildDeptTree(List<SysDepartment> depts)
    {
        List<SysDepartment> returnList = new ArrayList<SysDepartment>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDepartment dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (SysDepartment dept : depts)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getPId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDepartment> depts)
    {
        List<SysDepartment> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Long> selectDeptListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDepartment selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId)
    {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDepartment dept)
    {
        Long deptId = StringUtil.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDepartment info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getPId());
        if (StringUtil.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(Long deptId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysDepartment dept = new SysDepartment();
            dept.setDeptId(deptId);
            List<SysDepartment> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
            if (StringUtil.isEmpty(depts))
            {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDepartment dept)
    {
        SysDepartment info = deptMapper.selectDeptById(dept.getPId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getPId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDepartment dept)
    {
        SysDepartment newParentDept = deptMapper.selectDeptById(dept.getPId());
        SysDepartment oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtil.isNotNull(newParentDept) && StringUtil.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtil.isNotEmpty(dept.getAncestors())
                && !StringUtil.equals("0", dept.getAncestors()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDepartment dept)
    {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDepartment> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDepartment child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDepartment> list, SysDepartment t)
    {
        // 得到子节点列表
        List<SysDepartment> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDepartment tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDepartment> getChildList(List<SysDepartment> list, SysDepartment t)
    {
        List<SysDepartment> tlist = new ArrayList<SysDepartment>();
        Iterator<SysDepartment> it = list.iterator();
        while (it.hasNext())
        {
            SysDepartment n = (SysDepartment) it.next();
            if (StringUtil.isNotNull(n.getPId()) && n.getPId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDepartment> list, SysDepartment t)
    {
        return getChildList(list, t).size() > 0;
    }
}
