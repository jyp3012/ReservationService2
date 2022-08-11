package zerobase.reservationservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import zerobase.reservationservice2.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUserId(String userId);

    boolean existsByUserId(String userid);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE member m set m.roles = :role where m.userId = :userId")
    void updaterMemberRole(String role, String userId);

}


