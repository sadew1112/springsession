package com.likelion.lionlib.controller;

import com.likelion.lionlib.dto.request.ReservationRequest;
import com.likelion.lionlib.dto.response.ReservationResponse;
import com.likelion.lionlib.dto.response.ReserveCountResponse;
import com.likelion.lionlib.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReserve(@RequestBody ReservationRequest reservationRequest){
        log.info("Request POST a Reservation: {}", reservationRequest);

        ReservationResponse savedReserve = reservationService.addReserve(reservationRequest);
        return ResponseEntity.ok(savedReserve);
    }

    @GetMapping("/reservations/{reservationsId}")
    public ResponseEntity<ReservationResponse> getReserve(
            @PathVariable Long reservationsId
    ){
        log.info("Request GET a Reservation with ID: {}", reservationsId);
        ReservationResponse reservation = reservationService.getReserve(reservationsId);

        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{reservationsId}")
    public ResponseEntity<?> deleteReserve(
            @PathVariable Long reservationsId
    ){
        log.info("Request DELETE reservation with ID: {}", reservationsId);
        reservationService.deleteReserve(reservationsId);
        return ResponseEntity.ok().body("예약이 취소되었습니다.");
    }

    @GetMapping("/members/{memberId}/reservations")
    public ResponseEntity<List<ReservationResponse>> getMemberReserve(
            @PathVariable Long memberId
    ){
        log.info("Request GET a reservations for memberID: {}", memberId);

        List<ReservationResponse> reservation = reservationService.getMemberReserve(memberId);

        if (reservation.isEmpty()) {
            log.info("No reservations found for memberId: {}", memberId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reservation);

    }

    @GetMapping("books/{bookId}/reservations")
    public ResponseEntity<?> countReserve(
            @PathVariable Long bookId
    ){
        log.info("Request GET a Counting Reservation for bookId : {}", bookId);
        ReserveCountResponse count = reservationService.countReserve(bookId);
        return ResponseEntity.ok(count);
    }


}
