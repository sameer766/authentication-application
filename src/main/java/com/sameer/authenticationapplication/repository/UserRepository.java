package com.sameer.authenticationapplication.repository;

import com.sameer.authenticationapplication.entity.UserEntity;
import com.sameer.authenticationapplication.shared.UserDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findUserByEmail(String email);

  UserEntity findUserByUserId(String userId);

  UserEntity findUserByEmailVerificationToken(String token);

  @Query(value = "Select * from users u where u.email_verification_status = 1",
         countQuery = "Select count(*) from users u where u.email_verification_status = 1",
         nativeQuery = true)
  Page<UserEntity> userWithEmailAddressVerified(Pageable pageable);

  @Query(value = "Select * from users u where u.first_name = ?1", nativeQuery = true)
  List<UserEntity> findUserByFirstName(String firstName);

  @Query(value = "Select * from users u where u.last_name = :lastName", nativeQuery = true)
  List<UserEntity> findUserByLastName(@Param("lastName") String lastName);


  @Query(value = "Select * from users u where u.last_name LIKE %:lastName% or u.first_name LIKE %:firstName%",
         nativeQuery = true)
  List<UserEntity> findUserContainingKeyword(@Param("lastName") String lastName,
                                             @Param("firstName") String firstName);

  @Query(value = "Select first_name , last_name from users u where u.last_name LIKE %:lastName% or u.first_name LIKE %:firstName%",
         nativeQuery = true)
  List<Object[]> findUserContainingKeywordOnlyName(@Param("lastName") String lastName,

                                                   @Param("firstName") String firstName);

  @Transactional
  @Modifying
  @Query(value = "update users u set u.email_verification_status = :emailVerificationStatus where u.user_id = :userId",
         nativeQuery = true)
  void updateUserEmailVerificationStatus(
      @Param("emailVerificationStatus") int emailVerificationStatus,
      @Param("userId") String userId);

  @Query("Select u from UserEntity u where u.firstName = :firstName")
  UserEntity findUserByFirstNameJpql(@Param("firstName") String firstName);

}
