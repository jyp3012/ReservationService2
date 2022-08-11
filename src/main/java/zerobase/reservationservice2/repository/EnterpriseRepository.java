package zerobase.reservationservice2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservationservice2.entity.EnterpriseEntity;

import java.util.Optional;

@Repository
public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    boolean existsByEnterpriseAddress(String enterpriseAddress);

    boolean existsByEnterpriseName(String enterpriseName);

    Optional<EnterpriseEntity> findByUserIdAndEnterpriseName(String userId, String enterpriseName);

    Optional<EnterpriseEntity> findByEnterpriseName(String enterpriseName);

    @Transactional
    void deleteByUserId(String userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE enterprise e set e.reservedUser = :reservedUser where e.enterpriseName = :enterpriseName")
    void updaterReservedUser(Long reservedUser, String enterpriseName);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE enterprise e set e.adminApprovalYn = :approvalAdmin where e.enterpriseName = :enterpriseName")
    void updaterApprovalAdmin(boolean approvalAdmin, String enterpriseName);
}
