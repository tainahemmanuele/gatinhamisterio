package com.gm.user;

import com.gm.user.User;
import com.gm.util.UserRole;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@EnableScan
public interface UserRepository extends CrudRepository<User, Long> {
    //@Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(@Param("email") String email);

    //@Query("SELECT u FROM User u WHERE u.role = :role")
    public List<User> findByRole(@Param("role") UserRole role);

    //@Query("SELECT u FROM User u WHERE u.email = :email")
    public UserRole findUserRoleByEmail(@Param("email") String email);
}