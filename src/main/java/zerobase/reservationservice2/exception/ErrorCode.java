package zerobase.reservationservice2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ALREADY_EXISTS_ENTERPRISE("이미 등록 된 업체 입니다.");

    private final String description;
}
