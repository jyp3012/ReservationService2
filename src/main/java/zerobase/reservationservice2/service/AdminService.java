package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.entity.ReservationEntity;
import zerobase.reservationservice2.exception.EnterpriseException;
import zerobase.reservationservice2.exception.ErrorCode;
import zerobase.reservationservice2.repository.EnterpriseRepository;
import zerobase.reservationservice2.repository.MemberRepository;
import zerobase.reservationservice2.repository.ReservationRepository;
import zerobase.reservationservice2.security.Authority;

import java.util.List;

import static zerobase.reservationservice2.security.Authority.ROLE_GUEST;
import static zerobase.reservationservice2.security.Authority.ROLE_USER;

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

    public MemberEntity approvalMember(String userId) {

        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        memberRepository.updaterMemberRole(ROLE_USER.toString(), userId);

        return member;
    }

    public MemberEntity suspensionMember(String userId) {

        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        memberRepository.updaterMemberRole(ROLE_GUEST.toString(), userId);

        return member;
    }
}
