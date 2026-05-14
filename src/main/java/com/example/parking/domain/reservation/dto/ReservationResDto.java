package com.example.parking.domain.reservation.dto

import com.example.parking.domain.reservation.entity.Reservation
import com.example.parking.domain.reservation.entity.ReservationStatus
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

data class ReservationResDto(
    val reservationId: Long,
    val parkingLotId: Long,
    val parkingSpotId: Long,
    val parkingLotName: String,
    val parkingSpotNumber: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val status: ReservationStatus,
    val totalPrice: Int
) {
    companion object {
        fun from(reservation: Reservation): ReservationResDto {
            val minutes = ChronoUnit.MINUTES.between(
                reservation.getStartTime(), reservation.getEndTime()
            )
            val price = ceil(minutes / 10.0).toInt() * reservation.getParkingLot().getPrice()

            return ReservationResDto(
                reservationId = reservation.getId(),
                parkingLotId = reservation.getParkingLot().getId(),
                parkingSpotId = reservation.getParkingSpot().getId(),
                parkingLotName = reservation.getParkingLot().getName(),
                parkingSpotNumber = reservation.getParkingSpot().getNumber(),
                startTime = reservation.getStartTime(),
                endTime = reservation.getEndTime(),
                status = reservation.getStatus(),
                totalPrice = price
            )
        }
    }
}