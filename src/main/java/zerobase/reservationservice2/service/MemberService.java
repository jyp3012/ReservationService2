package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.reservationservice2.config.MailConfig;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.exception.CustomTotalException;
import zerobase.reservationservice2.exception.ErrorCode;
import zerobase.reservationservice2.model.Auth;
import zerobase.reservationservice2.model.ResetPassword;
import zerobase.reservationservice2.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static zerobase.reservationservice2.security.Authority.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MailConfig mailConfig;

    private final int EMAIL_VALID_DAY = 1;
    private final LocalDateTime LIMIT_PASSWORD_DATE = LocalDateTime.now().plusDays(1);


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID 입니다."));
    }


    public MemberEntity register (Auth.SignUp member) {
        boolean exists = memberRepository.existsByUserId(member.getUserId());

        if (exists) {
            throw new CustomTotalException(ErrorCode.ALREADY_EXISTS_USER);
        }
        member.setUserPassword(passwordEncoder.encode(member.getUserPassword()));
        var result = memberRepository.save(member.toEntity());

        String email = member.getUserId();
        String subject = "사이트 가입을 축하드립니다.";
        String text = "<p>사이트 가입을 축하드립니다.</p><p> 아래 링크를 클릭해서 가입을 완료 하세요.</p>"
                + "<div><a target ='_black' href = 'http://localhost:8080/member/email-auth?id=" + member.getUuid() + "'> 가입 완료</a></div>";

        mailConfig.sendMail(email, subject, text);
        log.info("userId : " + member.getUserId() + "회원가입 완료");

        return result;
    }

    public MemberEntity authenticate(Auth.SignIn member) {
        var user = memberRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("존재 하지 않는 ID 입니다"));

        if(!passwordEncoder.matches(member.getUserPassword(), user.getUserPassword())) {
            throw new CustomTotalException(ErrorCode.UN_MATH_PASSWORD);
        }

        return user;
    }

    public boolean emailAuth(String uuid) {

        MemberEntity member = memberRepository.findByEmailAuthKey(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));

        validateEmailAuth(member);

        memberRepository.emailUpdaterMemberRole(ROLE_USER.toString(), uuid);

        return true;
    }

    private void validateEmailAuth(MemberEntity member) {
        if (ChronoUnit.DAYS.between(member.getEmailAuthDt(), LocalDateTime.now()) > EMAIL_VALID_DAY) {
            throw new CustomTotalException(ErrorCode.OVER_DATE_TO_EMAIL);
        }
    }

    public boolean sendRestPasswordEmail(ResetPassword resetPassword) {

        MemberEntity member = memberRepository.findByUserId(resetPassword.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다"));

        validateSendRestPasswordEmail(resetPassword, member);

        String uuid = changePassword(resetPassword, member);

        resetPasswordMailSend(resetPassword, uuid);

        return true;
    }

    private void validateSendRestPasswordEmail(ResetPassword resetPassword, MemberEntity member) {

        if (!member.getUserFullName().equals(resetPassword.getUserName())) {
            throw new CustomTotalException(ErrorCode.UN_MATCH_USERID_USERNAME);
        }
    }
    
    private String changePassword(ResetPassword resetPassword, MemberEntity member) {
        
        String uuid = UUID.randomUUID().toString();

        memberRepository.resetPasswordUuid(member.getUserId(), uuid);

        memberRepository.resetPasswordDt(LIMIT_PASSWORD_DATE, uuid);

        return uuid;
    }

    private void resetPasswordMailSend(ResetPassword resetPassword, String uuid) {

        String email = resetPassword.getUserId();
        String subject = "비밀번호 초기화 메일 입니다..";
        String text = "<p>비밀번호 초기화 메일 입니다.</p><p> 아래 링크를 클릭해서 비밀번호 초기화를 진행 해주세요..</p>"
                + "<div><a target ='_black' href = 'http://localhost:8080/member/reset/password?id=" + uuid + "'> 초기화</a></div>";

        mailConfig.sendMail(email, subject, text);
    }

    public boolean resetPassword(String uuid, String password) {

        MemberEntity member = memberRepository.findByResetPasswordKey(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));

        memberRepository.updatePassword(passwordEncoder.encode(password), uuid);
        log.info("userId : " + member.getUserId() + "비밀번호 초기화 완료");

        return true;
    }
}
