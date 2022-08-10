package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.entity.ReservationEntity;
import zerobase.reservationservice2.exception.EnterpriseException;
import zerobase.reservationservice2.exception.ErrorCode;
import zerobase.reservationservice2.exception.ReservationException;
import zerobase.reservationservice2.model.Reservation;
import zerobase.reservationservice2.repository.EnterpriseRepository;
import zerobase.reservationservice2.repository.MemberRepository;
import zerobase.reservationservice2.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final EnterpriseRepository enterpriseRepository;

    @Transactional
    public ReservationEntity reservation(Reservation.reserve request, String userId) {

        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 ID가 없습니다."));

        EnterpriseEntity enterprise = enterpriseRepository.findByEnterpriseName(request.getEnterpriseName())
                .orElseThrow(() -> new EnterpriseException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        validateReservation(member, enterprise);

        EnterpriseEntity entity = increaseReservedUser(enterprise);

        var result = reservationRepository.save(request.toEntity(member, entity));

        return result;
    }

    private EnterpriseEntity increaseReservedUser(EnterpriseEntity enterprise) {
        Long reservedUser = enterprise.getReservedUser() + 1L;

        return EnterpriseEntity.builder()
                .enterpriseName(enterprise.getEnterpriseName())
                .enterprisePassword(enterprise.getEnterprisePassword())
                .enterpriseAddress(enterprise.getEnterpriseAddress())
                .reservedUser(reservedUser)
                .userId(enterprise.getUserId())
                .regDt(enterprise.getRegDt())
                .adminApprovalYn(enterprise.isAdminApprovalYn())
                .build();
    }
    private void validateReservation(MemberEntity member, EnterpriseEntity enterprise) {
        if (enterprise.getReservedUser() >= 5) {
            throw new EnterpriseException(ErrorCode.FULL_RESERVE_USER);
        }
    }

    @Transactional
    public boolean unReservation(Reservation.unReserve request, String userId) {

        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 ID가 없습니다."));

        ReservationEntity reservation = reservationRepository.findByEnterpriseName(request.getEnterpriseName())
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        validateUnReservation(request,member, reservation);

        EnterpriseEntity enterprise = enterpriseRepository.findByEnterpriseName(request.getEnterpriseName())
                .orElseThrow(() -> new EnterpriseException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        EnterpriseEntity entity = decreaseReservedUser(enterprise);

        reservationRepository.deleteById(reservation.getId());

        return true;
    }

    private EnterpriseEntity decreaseReservedUser(EnterpriseEntity enterprise) {
        Long reservedUser = enterprise.getReservedUser() - 1L;

        return EnterpriseEntity.builder()
                .enterpriseName(enterprise.getEnterpriseName())
                .enterprisePassword(enterprise.getEnterprisePassword())
                .enterpriseAddress(enterprise.getEnterpriseAddress())
                .reservedUser(reservedUser)
                .userId(enterprise.getUserId())
                .regDt(enterprise.getRegDt())
                .adminApprovalYn(enterprise.isAdminApprovalYn())
                .build();
    }

    private void validateUnReservation(Reservation.unReserve request, MemberEntity member, ReservationEntity reservation) {
        if (!member.getUserId().equals(reservation.getUserId())) {
            throw new ReservationException(ErrorCode.UN_MATH_USERID_ADDRESS);
        }
        if (!request.getReservationPassword().equals(reservation.getReservationPassword())) {
            throw new ReservationException(ErrorCode.UN_MATH_PASSWORD);
        }

        if (ChronoUnit.DAYS.between(LocalDateTime.now(), reservation.getUserReservedDate()) <= 3) {
            throw new ReservationException(ErrorCode.TOO_CLOSE_RESERVATION_DATE);
        }

    }

    public List<ReservationEntity> listUp(String name) {

        return reservationRepository.findListByUserId(name);
    }
}
