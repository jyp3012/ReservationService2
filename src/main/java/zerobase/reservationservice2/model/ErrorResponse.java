package zerobase.reservationservice2.model;

import lombok.*;
import zerobase.reservationservice2.exception.ErrorCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;
}
