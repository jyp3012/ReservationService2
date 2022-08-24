package zerobase.reservationservice2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_EXISTS_USER("이미 존재하는 회원 입니다."),
    UN_MATCH_USERID_USERNAME("등록된 이름과 ID가 일치하지 않습니다."),
    ALREADY_EXISTS_ENTERPRISE("이미 등록 된 업체 입니다."),
    UN_MATH_USERID_ADDRESS("등록 당시 로그인 했던 ID가 아닙니다."),
    UN_MATH_PASSWORD("등록 된 비밀번호와 일치하지 않습니다."),
    EXISTS_RESERVED_USER("현재 예약 되어 있는 회원이 있습니다."),
    NOT_EXISTS_ENTERPRISE("해당하는 업체가 없습니다."),
    FULL_RESERVE_USER("예약 가능한 자리가 없습니다."),
    TOO_CLOSE_RESERVATION_DATE("에약일 3일 전에는 취소가 불가능합니다."),
    OVER_DATE_TO_EMAIL("이메일 인증 가능 기한이 지났습니다."),
    SUSPENDED_ENTERPRISE("관리자에 의해 영업이 제한된 업체 입니다"),
    ENTERPRISE_TRANSACTION_LOCK("현재 예약이 진행 중인 업체 입니다.");

    private final String description;
}
