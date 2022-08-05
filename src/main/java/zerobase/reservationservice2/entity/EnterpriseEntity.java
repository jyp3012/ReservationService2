package zerobase.reservationservice2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "enterprise")
public class EnterpriseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    private String userId;

    @NotNull
    private String enterpriseName;

    @NotNull
    private String enterpriseAddress;

    @NotNull
    private String enterprisePassword;

    private Long reservedUser;
    private LocalDateTime regDt;
    private boolean adminApprovalYn;
}
