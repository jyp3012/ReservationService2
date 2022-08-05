package zerobase.reservationservice2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservationservice2.model.Auth;
import zerobase.reservationservice2.security.TokenProvider;
import zerobase.reservationservice2.service.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> singUp(@RequestBody Auth.SignUp request) {

        var result = memberService.register(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody Auth.SignIn request) {

        var member = memberService.authenticate(request);
        var token = tokenProvider.generateToken(member.getUserId(), member.getRoles());


        return ResponseEntity.ok(token);
    }


}
