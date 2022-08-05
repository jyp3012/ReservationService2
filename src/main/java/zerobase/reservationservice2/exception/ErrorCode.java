package zerobase.reservationservice2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ALREADY_EXISTS_ENTERPRISE("이미 등록 된 업체 입니다."),
    UN_MATH_USERID_ADDRESS("등록 당시 로그인 했던 ID가 아닙니다."),
    UN_MATH_ENTERPRISE_PASSWORD("등록 된 비밀번호와 일치하지 않습니다."),
    EXISTS_RESERVED_USER("현재 예약 되어 있는 회원이 있습니다.");

    private final String description;
}
