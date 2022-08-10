package zerobase.reservationservice2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import zerobase.reservationservice2.entity.ReservationEntity;
import zerobase.reservationservice2.model.Reservation;
import zerobase.reservationservice2.service.ReservationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> reservation(@RequestBody Reservation.reserve request, Authentication auth){

        var result = reservationService.reservation(request, auth.getName());

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/cancel")
    @PreAuthorize("hasRole('USER')")
    public boolean unReservation(@RequestBody Reservation.unReserve request, Authentication auth) {

        return reservationService.unReservation(request, auth.getName());
    }
    
    @GetMapping("")
    public List<ReservationEntity> findAll(Authentication auth
            ,@PageableDefault(size = 10) Pageable pageable) {

        return reservationService.findAll(auth.getName(), pageable);
    }
}
