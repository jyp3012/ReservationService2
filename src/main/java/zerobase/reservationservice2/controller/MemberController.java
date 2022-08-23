package zerobase.reservationservice2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.reservationservice2.model.Auth;
import zerobase.reservationservice2.model.ResetPassword;
import zerobase.reservationservice2.security.TokenProvider;
import zerobase.reservationservice2.service.MemberService;

@Slf4j
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

    @GetMapping("/email-auth")
    public boolean emailAuth(@RequestParam(value = "id", required = false)String uuid) {

        return memberService.emailAuth(uuid);
    }

    @PostMapping("/forgot/password")
    public boolean forgotPassword(@RequestBody ResetPassword resetPassword) {

        return memberService.sendRestPasswordEmail(resetPassword);
    }

    @PostMapping("/reset/password")
    public boolean ResetPassword(@RequestParam(value = "id", required = false)String uuid,
                                     @RequestBody String password) {

        return memberService.resetPassword(uuid, password);

    }


}
