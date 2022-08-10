package zerobase.reservationservice2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservationservice2.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    Optional<ReservationEntity> findByEnterpriseName(String enterpriseName);

    @Transactional
    void deleteById(Long id);

    List<ReservationEntity> findListByUserId(String userId);

    List<ReservationEntity> findByUserIdOrderByApprovalDate(String userId, Pageable pageable);
}
