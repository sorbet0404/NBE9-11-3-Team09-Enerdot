package com.example.parking.domain.reservation.entity

enum class ReservationStatus {
    PENDING,  // 결제 전 예약 생성
    CONFIRMED,  // 예약 확정
    COMPLETED,  // 주차 완료
    CANCELED // 예약 취소 및 환불
}