package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.entity.ReservationEntity;
import zerobase.reservationservice2.exception.CustomTotalException;
import zerobase.reservationservice2.exception.ErrorCode;
import zerobase.reservationservice2.model.Reservation;
import zerobase.reservationservice2.repository.EnterpriseRepository;
import zerobase.reservationservice2.repository.MemberRepository;
import zerobase.reservationservice2.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final EnterpriseRepository enterpriseRepository;

    private final Long FULL_RESERVATION_USER = 5L;

    public Page<ReservationEntity> findAll(String userId, Pageable pageable) {
        return reservationRepository.findByUserIdOrderByApprovalDate(userId, pageable);
    }
    public List<ReservationEntity> listUp(String name) {

        return reservationRepository.findListByUserId(name);
    }

    @Transactional
    public ReservationEntity reservation(Reservation.reserve request, String userId) {

        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 ID가 없습니다."));

        EnterpriseEntity enterprise = enterpriseRepository.findByEnterpriseName(request.getEnterpriseName())
                .orElseThrow(() -> new CustomTotalException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        validateReservation(member, enterprise);

        Long changeReservedUser = enterprise.getReservedUser() + 1L;

        enterpriseRepository.updaterReservedUser(changeReservedUser, enterprise.getEnterpriseName());

        var result = reservationRepository.save(request.toEntity(member, enterprise));

        log.info("userId : " + member.getUserId() + ", EnterpriseName : " + enterprise.getEnterpriseName() + "예약 완료");

        return result;
    }

    private void validateReservation(MemberEntity member, EnterpriseEntity enterprise) {
        if (enterprise.getReservedUser() >= FULL_RESERVATION_USER.floatValue()) {
            throw new CustomTotalException(ErrorCode.FULL_RESERVE_USER);
        }
    }

    @Transactional
    public boolean unReservation(Reservation.unReserve request, String userId) {

        MemberEntity member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 ID가 없습니다."));

        ReservationEntity reservation = reservationRepository.findByEnterpriseName(request.getEnterpriseName())
                .orElseThrow(() -> new CustomTotalException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        validateUnReservation(request,member, reservation);

        EnterpriseEntity enterprise = enterpriseRepository.findByEnterpriseName(request.getEnterpriseName())
                .orElseThrow(() -> new CustomTotalException(ErrorCode.NOT_EXISTS_ENTERPRISE));

        Long changeReservedUser = enterprise.getReservedUser() - 1L;

        enterpriseRepository.updaterReservedUser(changeReservedUser, enterprise.getEnterpriseName());

        reservationRepository.deleteById(reservation.getId());

        log.info("userId : " + member.getUserId() + ", EnterpriseName : " + enterprise.getEnterpriseName() + "예약 취소");

        return true;
    }


    private void validateUnReservation(Reservation.unReserve request, MemberEntity member, ReservationEntity reservation) {
        if (!member.getUserId().equals(reservation.getUserId())) {
            throw new CustomTotalException(ErrorCode.UN_MATH_USERID_ADDRESS);
        }
        if (!request.getReservationPassword().equals(reservation.getReservationPassword())) {
            throw new CustomTotalException(ErrorCode.UN_MATH_PASSWORD);
        }

        if (ChronoUnit.DAYS.between(LocalDate.now(), reservation.getUserReservedDate()) <= 3) {
            throw new CustomTotalException(ErrorCode.TOO_CLOSE_RESERVATION_DATE);
        }

    }
}
