package com.nanyang.academy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanyang.academy.entity.SysUser;
import com.nanyang.academy.entity.dto.UserModelVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author HengYuanKeJi
 */
@Component
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 绑定手机号
     *
     * @param user
     * @return int
     * @author pt
     * @date 13:59 2022/7/19
     **/
    int bindPhone(SysUser user);

    /**
     * 修改接单状态
     *
     * @param user
     * @return int
     * @author pt
     * @date 14:12 2022/7/18
     **/
    int updateUserOrderStatus(SysUser user);

    /**
     * 登录验证
     *
     * @param wrapper
     * @return
     */
    SysUser login(@Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper);


    /**
     * 通过用户名查询用户
     *
     * @param userName
     * @return
     */
    SysUser selectUserByUserName(@Param("userName") String userName);

    /**
     * 通过昵称查询用户
     *
     * @param nickName
     * @return com.hengyuan.gy.entity.SysUser
     * @author pt
     * @date 9:56 2022/7/18
     **/
    SysUser selectUserByNickName(String nickName);

    /**
     * 新增用户信息
     *
     * @param user
     * @return
     */
    int insertUser(SysUser user);

    /**
     * 检查用户名是否唯一
     *
     * @param userName
     * @return
     */

    int checkUserNameUnique(String userName);

    /**
     * 根据条件分页查询用户列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<UserModelVo> selectUserListPage(Page<UserModelVo> page, @Param(Constants.WRAPPER) QueryWrapper<UserModelVo> wrapper);


    List<SysUser> getUserList(SysUser sysUser);

    /**
     * 根据条件分页查询已配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);


    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);


    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);


    /**
     * 校验手机号码是否唯一
     *
     * @param phone 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phone);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);

    SysUser getUserByPhone(String phone);

    int updateUserStatus(SysUser user);

    int updateUserReceiveMessages(SysUser user);

    SysUser getUserByOpenID(String openid);

    int updateOnline(SysUser user);

    int editUserMini(SysUser sysUser);

    int bindSmart(SysUser user);

    int updateUserCode(SysUser user);

    @Update("update sys_user set online = 0")
    void setAllOutLine();

    @Update("update sys_user set order_status = 1")
    void setAllOrderStatus();

    List<SysUser> getOnlineService(@Param("area") String area,@Param("userName") String userName);

    @Update("update sys_user set openid = #{openid} where user_id = #{userId}")
    int updateUserOpenid(SysUser user);

    @Select("select password from password")
    Map<String,Object> getDefaultPassword();

    @Update("update password set password = #{pwd}")
    int editDefaultPassword(@Param("pwd") String pwd);
}
