package com.jd2.repository.role;

import com.jd2.domain.Role;
import com.jd2.repository.CRUDRepository;

import java.util.List;

public interface RoleRepositoryInterface extends CRUDRepository<Integer, Role> {

    List<Role> findRolesByUserId(Long userId);

}
