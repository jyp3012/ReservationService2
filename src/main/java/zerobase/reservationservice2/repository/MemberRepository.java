package zerobase.reservationservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservationservice2.entity.MemberEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUserId(String userId);

    boolean existsByUserId(String userid);

    Optional<MemberEntity> findByEmailAuthKey(String uuid);

    Optional<MemberEntity> findByResetPasswordKey(String uuid);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE member m set m.roles = :role where m.userId = :userId")
    void updaterMemberRole(String role, String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE member m set m.roles = :role where m.emailAuthKey = :uuid")
    void emailUpdaterMemberRole(String role, String uuid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE member m set m.resetPasswordKey = :uuid where m.userId = :userId")
    void resetPasswordUuid(String userId, String uuid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE member m set m.restPasswordLimitDt = :date where m.resetPasswordKey = :uuid")
    void resetPasswordDt(LocalDateTime date, String uuid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE member m set m.userPassword = :password where m.resetPasswordKey = :uuid")
    void updatePassword(String password, String uuid);

}


