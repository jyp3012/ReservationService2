package zerobase.reservationservice2.model;


import lombok.Data;
import zerobase.reservationservice2.entity.EnterpriseEntity;

import java.security.Principal;
import java.time.LocalDateTime;

public class RegEnterprise {

    @Data
    public static class regEnterprise{

        private String enterpriseName;
        private String enterpriseAddress;
        private String enterprisePassword;

        private Long reservedUser;

        private LocalDateTime regDt;


        public EnterpriseEntity toEntity(String userId) {
            return EnterpriseEntity.builder()
                    .userId(userId)
                    .enterpriseName(this.enterpriseName)
                    .enterpriseAddress(this.enterpriseAddress)
                    .enterprisePassword(this.enterprisePassword)
                    .regDt(LocalDateTime.now())
                    .reservedUser(0L)
                    .adminApprovalYn(true)
                    .build();
        }
    }

    @Data
    public static class unRegEnterprise{

        private String userId;
        private String enterpriseName;
        private String enterprisePassword;
        private Long reservedUser;
    }
}
