package zerobase.reservationservice2.model;

import lombok.*;

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
