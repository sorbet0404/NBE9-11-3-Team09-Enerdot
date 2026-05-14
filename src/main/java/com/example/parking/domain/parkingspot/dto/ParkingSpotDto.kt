package com.example.parking.domain.parkingspot.dto

import com.example.parking.domain.parkingspot.entity.ParkingSpot
import com.example.parking.domain.parkingspot.entity.SpotStatus
import com.example.parking.domain.parkingspot.entity.SpotType

data class ParkingSpotDto(
    val id: Long,
    val status: SpotStatus,
    val type: SpotType,
    val number: String
) {
    constructor(parkingSpot: ParkingSpot) : this(
        parkingSpot.id,
        parkingSpot.status,
        parkingSpot.type,
        parkingSpot.number
    )
}