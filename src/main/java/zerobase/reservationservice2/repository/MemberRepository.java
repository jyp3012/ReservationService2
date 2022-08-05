package zerobase.reservationservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservationservice2.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByUserId(String userId);

    boolean existsByUserId(String userid);
}


