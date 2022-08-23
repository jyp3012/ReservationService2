package zerobase.reservationservice2.model;

import lombok.Data;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.security.Authority;

import java.time.LocalDateTime;
import java.util.UUID;

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
        private String uuid = UUID.randomUUID().toString();

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                    .userId(this.userId)
                    .userName(this.userName)
                    .userPassword(this.userPassword)
                    .userPhoneNumber(this.userPhoneNumber)
                    .emailAuthKey(uuid)
                    .roles(Authority.ROLE_GUEST.toString())
                    .emailAuthDt(LocalDateTime.now())
                    .regDt(LocalDateTime.now())
                    .build();
        }
    }
}
