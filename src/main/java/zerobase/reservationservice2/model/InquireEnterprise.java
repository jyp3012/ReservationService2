package zerobase.reservationservice2.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquireEnterprise {

    private String enterpriseName;

    private String enterpriseAddress;

    private Long reservedUser;
}
