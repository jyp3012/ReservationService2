package zerobase.reservationservice2.entity;

import lombok.*;

import javax.persistence.*;
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

    private String userId;
    private String enterpriseName;
    private String enterpriseAddress;
    private String enterprisePassword;
    private Long reservedUser;
    private LocalDateTime regDt;
    private boolean adminApprovalYn;
}
