package com.example.parking.domain.parkingLot.service

import com.example.parking.domain.parkingLot.entity.ParkingLot
import com.example.parking.domain.parkingLot.repository.ParkingLotRepository
import com.example.parking.domain.parkingspot.service.ParkingSpotService
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import org.locationtech.jts.geom.Point



/*
* 외부 API 호출에 트랜잭션을 걸어서 성능 저하 및 커넥션 풀 고갈이 되는거를 막기위해
* 개별 저장시에만 트랜잭션 적용되도록 트랜잭션 범위를 좁혀 수정
* */
@Service
class ParkingLotWriter(
    private val parkingLotRepository: ParkingLotRepository,
    private val parkingSpotService: ParkingSpotService
) {

    @Transactional
    fun saveOrUpdate(
        externalId: String,
        name: String,
        address: String,
        totalSpot: Int,
        location: Point?
    ) {
        parkingLotRepository.findByExternalId(externalId)
            .ifPresentOrElse(
                { parkingLot ->
                    parkingLot.updateInfo(
                        name = name,
                        address = address,
                        totalSpot = totalSpot,
                        location = location
                    )
                },
                {
                    val saved = parkingLotRepository.save(
                        ParkingLot.of(
                            externalId = externalId,
                            name = name,
                            address = address,
                            totalSpot = totalSpot,
                            location = location
                        )
                    )

                    if (totalSpot > 0) {
                        parkingSpotService.createSpots(saved, totalSpot)
                    }
                }
            )
    }
}