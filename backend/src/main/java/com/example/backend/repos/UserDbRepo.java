package com.example.backend.repos;

import com.example.backend.models.database.UserDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDbRepo extends JpaRepository<UserDbEntity, Long> {
    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    List<UserDbEntity> findUserByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM users WHERE userid = :id", nativeQuery = true)
    UserDbEntity findUserById(@Param("id") Long id);
}
