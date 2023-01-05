package com.qxw.acl.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qxw.acl.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 */
public interface PermissionMapper extends BaseMapper<Permission> {


    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);
}
