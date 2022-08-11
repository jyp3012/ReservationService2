package zerobase.reservationservice2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.entity.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    @Data
    public static class reserve{
        private String reservationPassword;
        private String enterpriseName;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate userReservedDate;

        public ReservationEntity toEntity(MemberEntity member, EnterpriseEntity enterprise){
            return ReservationEntity.builder()
                    .userId(member.getUserId())
                    .userName(member.getUserFullName())
                    .userPhoneNumber(member.getUserPhoneNumber())
                    .enterpriseName(enterprise.getEnterpriseName())
                    .enterpriseAddress(enterprise.getEnterpriseAddress())
                    .userReservedDate(this.userReservedDate)
                    .reservationPassword(this.reservationPassword)
                    .approvalDate(LocalDateTime.now())
                    .build();
        }

    }

    @Data
    public static class unReserve{
        private String reservationPassword;
        private String enterpriseName;
    }
}
