package com.nanyang.academy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.dto.UserLoginDTO;
import com.nanyang.academy.entity.dto.UserModelVo;
import com.nanyang.academy.entity.param.UserQueryParam;
import com.nanyang.academy.entity.pojo.LoginSmsInput;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {


    /**
     * 绑定手机号
     * @author pt
     * @date 13:58 2022/7/19
     * @param user
     * @return int
     **/
    int bindPhone(SysUser user);

    /**
     * 通过手机号获取用户
     * @author pt
     * @date 14:14 2022/7/18
     * @param input
     * @return com.hengyuan.gy.entity.SysUser
     **/
    SysUser getUserByPhone(LoginSmsInput input);

    /**
     * 通过昵称查询用户
     * @author pt
     * @date 9:57 2022/7/18
     * @param nickName
     * @return com.hengyuan.gy.entity.SysUser
     **/
    SysUser selectUserByNickName(String nickName);

    SysUser login(UserLoginDTO param);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    int resetUserPwd(String userName, String password);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    boolean updateUserAvatar(String userName, String avatar);


    SysUser selectUserByUserName(String username);

    SysUser selectUserById(Long userId);

    int insertUser(SysUser user);

    int updateUserProfile(SysUser sysUser);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    String checkUserNameUnique(String userName);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean registerUser(SysUser user);

    /**
     * 分页查询用户信息
     * @param param
     * @return
     */
    IPage<UserModelVo> selectUserListPage(UserQueryParam param);

    String checkPhoneUnique(SysUser user);

    String checkEmailUnique(SysUser user);

    void checkUserDataScope(Long userId);

    int deleteUserById(Long userId);


    int deleteUserByIds(Long[] userIds);

    void checkUserAllowed(SysUser user);

    int updateUser(SysUser user);

    int resetPwd(SysUser user);

    int updateUserStatus(SysUser user);

    void insertUserAuth(Long userId, Long[] roleIds);

    int updateUserReceiveMessages(SysUser user);


    String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);



    int editUserMini(SysUser sysUser);



}
