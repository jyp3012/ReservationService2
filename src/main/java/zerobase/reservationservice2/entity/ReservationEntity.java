package zerobase.reservationservice2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userId;
    @NotNull
    private String userName;
    @NotNull
    private String userPhoneNumber;

    @NotNull
    private String enterpriseName;
    @NotNull
    private String enterpriseAddress;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate userReservedDate;

    @NotNull
    @Size(min = 4, max = 4)
    private String reservationPassword;

    private LocalDateTime approvalDate;

}
