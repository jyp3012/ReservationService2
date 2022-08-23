package zerobase.reservationservice2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservationservice2.entity.EnterpriseEntity;
import zerobase.reservationservice2.entity.MemberEntity;
import zerobase.reservationservice2.entity.ReservationEntity;
import zerobase.reservationservice2.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/enterprise/approval")
    @PreAuthorize("hasRole('ADMIN')")
    public EnterpriseEntity approvalEnterprise(String enterpriseName) {

        return adminService.approvalEnterprise(enterpriseName);
    }


    @PostMapping("/enterprise/suspension")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReservationEntity> suspensionEnterprise(String enterpriseName) {

        return adminService.suspensionEnterprise(enterpriseName);
    }

    @PostMapping("/member/approval")
    @PreAuthorize("hasRole('ADMIN')")
    public String approvalMember(String userId) {

        return adminService.approvalMember(userId);
    }

    @PostMapping("/member/suspension")
    @PreAuthorize("hasRole('ADMIN')")
    public String suspensionMember(String userId) {

        return adminService.suspensionMember(userId);
    }

}
