package zerobase.reservationservice2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.model.Auth;
import zerobase.reservationservice2.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("회원 가입 되어 있지 않은 ID입니다."));
    }


    public MemberEntity register (Auth.SignUp member) {
        boolean exists = memberRepository.existsByUserId(member.getUserId());

        if (exists) {
            throw new RuntimeException("이미 사용 중인 ID입니다.");
        }
        member.setUserPassword(passwordEncoder.encode(member.getUserPassword()));
        var result = memberRepository.save(member.toEntity());

        return result;
    }

    public MemberEntity authenticate(Auth.SignIn member) {
        var user = memberRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 ID 입니다"));

        if(!passwordEncoder.matches(member.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
