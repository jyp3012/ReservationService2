package zerobase.reservationservice2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import zerobase.reservationservice2.model.InquireEnterprise;
import zerobase.reservationservice2.model.RegEnterprise;
import zerobase.reservationservice2.service.EnterpriseService;

@RestController
@RequestMapping("/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/regis")
    public ResponseEntity<?> register(@RequestBody RegEnterprise.regEnterprise request
            , Authentication authentication) {

        var result = enterpriseService.register(request, authentication.getName());

        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/unregis")
    public boolean unRegister(@RequestBody RegEnterprise.unRegEnterprise request
            , Authentication authentication) {

        return enterpriseService.unRegister(request, authentication.getName());

    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public InquireEnterprise getEnterprise(@RequestParam String enterprise) {
        return enterpriseService.inquire(enterprise);
    }

}
