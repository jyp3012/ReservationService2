package zerobase.reservationservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservationservice2.entity.EnterpriseEntity;

import java.util.Optional;

@Repository
public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    boolean existsByEnterpriseAddress(String enterpriseAddress);

    boolean existsByEnterpriseName(String enterpriseName);

    Optional<EnterpriseEntity> findByUserIdAndEnterpriseName(String userId, String enterpriseName);

    Optional<EnterpriseEntity> findByEnterpriseName(String enterpriseName);

    void deleteByUserId(String userId);
}
