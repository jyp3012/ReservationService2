package zerobase.reservationservice2.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnterpriseException extends RuntimeException{

    private ErrorCode errorCode;

    private String errorMessage;

    public EnterpriseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
