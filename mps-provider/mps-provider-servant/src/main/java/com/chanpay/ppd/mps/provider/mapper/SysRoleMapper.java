package com.chanpay.ppd.mps.provider.mapper;


import com.chanpay.ppd.mps.api.entity.SysRole;
import com.chanpay.ppd.mps.common.service.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色DAO接口
 *
 * @author zhangxd
 */
@Mapper
public interface SysRoleMapper extends CrudDao<SysRole> {

    /**
     * 查询用户角色列表
     *
     * @param userId the user id
     * @return the list
     */
    List<SysRole> findListByUserId(String userId);

    /**
     * 删除角色菜单
     *
     * @param role the role
     * @return the int
     */
    int deleteRoleMenu(SysRole role);

    /**
     * 插入角色菜单
     *
     * @param role the role
     * @return the int
     */
    int insertRoleMenu(SysRole role);
}
