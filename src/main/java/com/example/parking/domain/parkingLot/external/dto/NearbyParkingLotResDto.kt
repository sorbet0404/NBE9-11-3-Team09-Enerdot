package com.example.parking.domain.parkingLot.dto

import com.example.parking.domain.parkingLot.repository.NearbyParkingLotProjection
import java.time.LocalTime

data class NearbyParkingLotResDto(
    val id: Long,
    val name: String,
    val address: String,
    val totalSpot: Int,
    val price: Int,
    val operationStartTime: LocalTime,
    val operationEndTime: LocalTime,
    val latitude: Double,
    val longitude: Double,
    val distance: Int  // 미터, 정수로 반올림
) {
    companion object {
        fun from(p: NearbyParkingLotProjection) = NearbyParkingLotResDto(
            id = p.getId(),
            name = p.getName(),
            address = p.getAddress(),
            totalSpot = p.getTotalSpot(),
            price = p.getPrice(),
            operationStartTime = p.getOperationStartTime(),
            operationEndTime = p.getOperationEndTime(),
            latitude = p.getLatitude(),
            longitude = p.getLongitude(),
            distance = p.getDistance().toInt()
        )
    }
}