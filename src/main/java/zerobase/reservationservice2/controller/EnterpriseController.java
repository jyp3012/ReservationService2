package zerobase.reservationservice2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservationservice2.model.RegEnterprise;
import zerobase.reservationservice2.service.EnterpriseService;

import java.security.Principal;

@RestController
@RequestMapping("/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/regis")
    public ResponseEntity<?> register(@RequestBody RegEnterprise.regEnterprise request, Authentication authentication) {

        var result = enterpriseService.register(request, authentication.getName());

        return ResponseEntity.ok(result);
    }
}
