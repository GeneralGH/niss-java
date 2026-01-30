package com.nanyang.academy.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanyang.academy.common.UserConstants;
import com.nanyang.academy.entity.*;
import com.nanyang.academy.entity.dto.UserLoginDTO;
import com.nanyang.academy.entity.dto.UserModelVo;
import com.nanyang.academy.entity.param.UserQueryParam;
import com.nanyang.academy.entity.pojo.LoginSmsInput;
import com.nanyang.academy.exception.CustomException;
import com.nanyang.academy.exception.ServiceException;
import com.nanyang.academy.mapper.*;
import com.nanyang.academy.service.SysConfigService;
import com.nanyang.academy.service.SysUserService;
import com.nanyang.academy.utils.SecurityUtils;
import com.nanyang.academy.utils.SpringUtils;
import com.nanyang.academy.utils.StringUtil;
import com.nanyang.academy.utils.bean.BeanValidators;
import com.nanyang.academy.utils.http.HttpUtils;
import com.nanyang.academy.utils.wx.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleUserMapper roleUserMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysDepartmentMapper deptMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysConfigService configService;

    @Autowired
    protected Validator validator;


    @Override
    public int bindPhone(SysUser user) {
        return sysUserMapper.bindPhone(user);
    }

    @Override
    public int updateUserOrderStatus(SysUser user) {
        return sysUserMapper.updateUserOrderStatus(user);
    }

    @Override
    public SysUser getUserByPhone(LoginSmsInput input) {
        return sysUserMapper.getUserByPhone(input.getPhone());
    }

    @Override
    public SysUser selectUserByNickName(String nickName) {
        return sysUserMapper.selectUserByNickName(nickName);
    }

    /**
     * 用户登录
     * @param param
     * @return
     */
    @Override
    public SysUser login(UserLoginDTO param) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",param.getUserName());
        queryWrapper.eq("password",param.getPassword());
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(user))
            throw new CustomException("用户不存在！");
        return user;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtil.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtil.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 修改用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return sysUserMapper.resetUserPwd(userName, password);
    }
    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return sysUserMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return sysUserMapper.selectUserById(userId);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUserProfile(SysUser user)
    {
        return sysUserMapper.updateUser(user);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = sysUserMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser sysUser) {
        return insertUser(sysUser)>0;
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param param 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public IPage<UserModelVo> selectUserListPage(UserQueryParam param) {
        Page page = new Page(param.getPage(),param.getSize());
        QueryWrapper<UserModelVo> qw = new QueryWrapper();
        qw.eq("u.is_delete",0);
        if (com.baomidou.mybatisplus.core.toolkit.ObjectUtils.isNotEmpty(param.getUserName()))
            qw.like("u.user_name",param.getUserName());
		if (ObjectUtils.isNotEmpty(param.getNickName()))
            qw.like("u.nick_name",param.getNickName());
		if (ObjectUtils.isNotEmpty(param.getArea()))
            qw.eq("u.area",param.getArea());
        if (ObjectUtils.isNotEmpty(param.getCompany())){
            qw.like("u.company",param.getCompany());
        }
        if (ObjectUtils.isNotEmpty(param.getUserType())){
            qw.eq("u.user_type",param.getUserType());
        }
        if (ObjectUtils.isNotEmpty(param.getProject())){
            qw.like("u.project",param.getProject());
        }
        if (ObjectUtils.isNotEmpty(param.getProperty())){
            qw.eq("u.property",param.getProperty());
        }
        if (ObjectUtils.isNotEmpty(param.getPhone())){
            qw.like("u.phone",param.getPhone());
        }
        if (ObjectUtils.isNotEmpty(param.getRemarkName())){
            qw.like("u.remark_name",param.getRemarkName());
        }
        if (ObjectUtils.isNotEmpty(param.getRemarkInfo())){
            qw.like("u.remark_info",param.getRemarkInfo());
        }
		if (ObjectUtils.isNotEmpty(param.getBeginTime()) && ObjectUtils.isNotEmpty(param.getEndTime()))
            qw.between("date(u.create_time)",param.getBeginTime(),param.getEndTime());

        return sysUserMapper.selectUserListPage(page,qw);
    }

    public List<SysUser> getUserList(SysUser user) {
        return sysUserMapper.getUserList(user);
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtil.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserMapper.checkPhoneUnique(user.getPhone());
        if (StringUtil.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        Long userId = StringUtil.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserMapper.checkEmailUnique(user.getEmail());
        if (StringUtil.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).getUserList(user);
            if (StringUtil.isEmpty(users))
            {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        roleUserMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return sysUserMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds)
    {
        /*for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }*/
        // 删除用户与角色关联
        roleUserMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return sysUserMapper.deleteUserByIds(userIds);
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtil.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }


    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        roleUserMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return sysUserMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int resetPwd(SysUser user) {
        return sysUserMapper.resetUserPwd(user.getUserName(),user.getPassword());
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUserStatus(SysUser user) {
        return sysUserMapper.updateUserStatus(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    @Override
    public void insertUserAuth(Long userId, Long[] roleIds) {
        roleUserMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    @Override
    public int updateUserReceiveMessages(SysUser user) {
        return sysUserMapper.updateUserReceiveMessages(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectAllocatedList(SysUser user) {
        return sysUserMapper.selectAllocatedList(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtil.isNotNull(roleIds))
        {
            // 新增用户与角色管理
            List<SysRoleUser> list = new ArrayList<SysRoleUser>();
            for (Long roleId : roleIds)
            {
                SysRoleUser ur = new SysRoleUser();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                roleUserMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user)
    {
        // 新增用户信息
        int rows = sysUserMapper.insertUser(user);
        // 新增用户岗位关联
        //insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }
    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtil.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<SysRoleUser> list = new ArrayList<SysRoleUser>();
            for (Long roleId : roles)
            {
                SysRoleUser ur = new SysRoleUser();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                roleUserMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtil.isNotNull(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return sysUserMapper.selectUnallocatedList(user);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtil.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = sysUserMapper.selectUserByUserName(user.getUserName());
                if (StringUtil.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateUser(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setUpdateUser(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public SysUser getUserByOpenID(String openId) {
        return sysUserMapper.getUserByOpenID(openId);
    }

    @Override
    public Map<String, Object> loginWechat(String code) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>(3);
        //授权登录
        String openId = WxUtil.getOpenId(code);
        if (ObjectUtils.isEmpty(openId)) {
            throw new CustomException("授权失败!");
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openId);
        SysUser user = sysUserMapper.selectOne(wrapper);
        if (user == null) {
            map.put("openid", openId);
        } if(user.getStatus()==1){
            throw new CustomException("该账号已被锁定");
        }else {

        }
        return map;
    }

    @Override
    public int updateOnline(SysUser user) {
        return sysUserMapper.updateOnline(user);
    }

    @Override
    public int editUserMini(SysUser sysUser) {
        return sysUserMapper.editUserMini(sysUser);
    }

    @Override
    public int bindSmart(SysUser user) {
        String url = "https://wxxz.xzbdc.com/znlMobileApps/znlMobile/mortgage/znUserLogin";
        String param = "userCode="+user.getSmartName()+"&password="+user.getSmartPassword();
        String res = HttpUtils.sendPost(url,param);
        JSONObject s = JSON.parseObject(res);
        System.out.println(s);
        if (s.getString("flag").equals("success")){
            user.setSmartRealName(s.getString("data"));
            return sysUserMapper.bindSmart(user);
        }else
            throw new CustomException("绑定智能链失败");

    }

    @Override
    public List<SysUser> getOnlineService(String area,String userName) {
        return sysUserMapper.getOnlineService(area,userName);
    }

    @Override
    public void setAllOutLine() {
        sysUserMapper.setAllOutLine();
    }

    @Override
    public void setAllOrderStatus() {
        sysUserMapper.setAllOrderStatus();
    }

    @Override
    public int updateUserOpenid(SysUser user) {
        return sysUserMapper.updateUserOpenid(user);
    }

    @Override
    public String getDefaultPassword() {
        return sysUserMapper.getDefaultPassword().get("password").toString();
    }

    @Override
    public int editDefaultPassword(String pwd) {
        return sysUserMapper.editDefaultPassword(pwd);
    }
}
