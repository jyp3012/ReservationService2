package zerobase.reservationservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservationservice2.entity.EnterpriseEntity;

@Repository
public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    boolean existsByEnterpriseAddress(String enterpriseAddress);

    boolean existsByEnterpriseName(String enterpriseName);
}
