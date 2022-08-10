package zerobase.reservationservice2.security;

import lombok.ToString;

@ToString
public enum Authority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_GUEST
}
