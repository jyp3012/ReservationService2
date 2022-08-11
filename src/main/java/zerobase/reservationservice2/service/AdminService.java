package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.entity.ReservationEntity;
import zerobase.reservationservice2.exception.EnterpriseException;
import zerobase.reservationservice2.exception.ErrorCode;
import zerobase.reservationservice2.repository.EnterpriseRepository;
import zerobase.reservationservice2.repository.MemberRepository;
import zerobase.reservationservice2.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EnterpriseRepository enterpriseRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;


    public List<ReservationEntity> suspensionEnterprise(String enterpriseName) {

        EnterpriseEntity enterprise = enterpriseRepository.findByEnterpriseName(enterpriseName)
                .orElseThrow(() -> new EnterpriseException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        enterpriseRepository.updaterApprovalAdmin(false, enterpriseName);

        List<ReservationEntity> reservationEntityList = reservationRepository.findListByEnterpriseName(enterpriseName);

        if (!reservationEntityList.isEmpty()) {
            reservationRepository.deleteAllByEnterpriseName(enterpriseName);
        }

        return reservationEntityList;
    }

    public EnterpriseEntity approvalEnterprise(String enterpriseName) {

        EnterpriseEntity enterprise = enterpriseRepository.findByEnterpriseName(enterpriseName)
                .orElseThrow(() -> new EnterpriseException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        enterpriseRepository.updaterApprovalAdmin(true, enterpriseName);

        return enterprise;
    }

}
