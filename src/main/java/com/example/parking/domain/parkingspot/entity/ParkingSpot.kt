package com.example.parking.domain.parkingspot.entity
import com.example.parking.domain.parkingLot.entity.ParkingLot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    name = "parking_spot",
    indexes = [
        Index(name = "idx_parking_lot_status", columnList = "parking_lot_id, parking_spot_status"),
        Index(
            name = "idx_parking_spot_status_reserved_at",
            columnList = "parking_spot_status, reserved_at"
        )]
)
class ParkingSpot(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    val parkingLot: ParkingLot,

    @Column(name = "parking_spot_number", nullable = false, length = 50)
    val number: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "parking_spot_type", nullable = false)
    val type: SpotType,

    @Enumerated(EnumType.STRING)
    @Column(name = "parking_spot_status", nullable = false)
    var status: SpotStatus = SpotStatus.AVAILABLE,

    @Column(name = "reserved_at")
    var reservedAt: LocalDateTime? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_spot_id")
    val id: Long = 0

    fun reserve() {
        if (this.status != SpotStatus.AVAILABLE) {
            throw IllegalStateException("이미 점유된 자리입니다.")
        }
        this.status = SpotStatus.OCCUPIED
        this.reservedAt = LocalDateTime.now()
    }

    fun release() {
        this.status = SpotStatus.AVAILABLE
        this.reservedAt = null
    }

    fun updateStatus(status: SpotStatus) {
        this.status = status
    }
}