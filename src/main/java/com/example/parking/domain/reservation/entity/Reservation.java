package com.example.parking.domain.reservation.entity

import com.example.parking.domain.parkingLot.entity.ParkingLot
import com.example.parking.domain.parkingspot.entity.ParkingSpot
import com.example.parking.domain.user.entity.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener.class)
    class Reservation(
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
val user: User,

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "parking_lot_id", nullable = false)
val parkingLot: ParkingLot,

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "parking_spot_id", nullable = false)
val parkingSpot: ParkingSpot,

@Column(name = "parking_start_time", nullable = false)
val startTime: LocalDateTime,

@Column(name = "parking_end_time", nullable = false)
val endTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    val id: Long = 0

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: ReservationStatus = ReservationStatus.PENDING

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "canceled_at")
    var canceledAt: LocalDateTime? = null

    @Column(name = "payment_requested_at")
    var paymentRequestedAt: LocalDateTime? = null

    fun startPayment() {
        this.paymentRequestedAt = LocalDateTime.now()
    }

    fun cancel() {
        this.status = ReservationStatus.CANCELED
        this.canceledAt = LocalDateTime.now()
    }

    fun confirm() {
        this.status = ReservationStatus.CONFIRMED
    }

    fun complete() {
        this.status = ReservationStatus.COMPLETED
    }

    fun pending() {
        this.status = ReservationStatus.PENDING
    }
}