package zerobase.reservationservice2.model;

import lombok.Data;
import zerobase.reservationservice2.entity.MemberEntity;

import java.time.LocalDateTime;

public class Auth {

    @Data
    public static class SignIn{
        private String userId;
        private String userPassword;
    }

    @Data
    public static class SignUp {

        private String userId;
        private String userName;
        private String userPassword;
        private String userPhoneNumber;
        private String roles;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                    .userId(this.userId)
                    .userName(this.userName)
                    .userPassword(this.userPassword)
                    .userPhoneNumber(this.userPhoneNumber)
                    .roles(this.roles)
                    .regDt(LocalDateTime.now())
                    .build();
        }
    }
}
