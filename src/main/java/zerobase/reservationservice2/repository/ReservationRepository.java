package zerobase.reservationservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservationservice2.entity.ReservationEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    Optional<ReservationEntity> findByEnterpriseName(String enterpriseName);

    void deleteById(Long id);

    List<ReservationEntity> findListByUserId(String userId);
}