package zerobase.reservationservice2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 4, max = 4)
    private String enterprisePassword;

    private Long reservedUser;
    private LocalDateTime regDt;
    private boolean adminApprovalYn;
}
