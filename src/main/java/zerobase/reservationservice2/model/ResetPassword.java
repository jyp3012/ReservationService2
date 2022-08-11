package zerobase.reservationservice2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResetPassword {

    private String userId;
    private String userName;
}
