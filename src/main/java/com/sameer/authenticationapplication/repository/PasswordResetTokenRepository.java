package com.sameer.authenticationapplication.repository;

import com.sameer.authenticationapplication.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity,Long> {
  PasswordResetTokenEntity findByToken(String token);
}
