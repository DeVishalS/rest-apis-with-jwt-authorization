package com.example.hellosecurity.repository.user;

import com.example.hellosecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameIgnoreCase(String userName);

    @Modifying
    @Query(value = """
        UPDATE users
        SET invalid_attempts = 0, locked_until = null
        WHERE upper(user_name) = upper(:userName)
        """,nativeQuery = true)
    public void unlockAndResetAttemptsCount(@Param("userName") String userName);

    @Modifying
    @Query(value = """
            UPDATE users
            SET invalid_attempts = invalid_attempts + 1, locked_until = :unlockTime
            WHERE upper(user_name) = upper(:userName)
            """,nativeQuery = true)
    public void recordInvalidAttemptsForUser(@Param("userName") String userName, @Param("unlockTime") LocalDateTime unlockTime);

}