package com.example.parking.domain.admin.parkingspot.controller

import com.example.parking.domain.parkingspot.entity.SpotStatus
import com.example.parking.domain.parkingspot.service.ParkingSpotService
import com.example.parking.global.response.RsData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/parking-spots")
class AdminParkingSpotController(
    private val parkingSpotService: ParkingSpotService,
) {

    // 관리자 수동 주차 상태 변경
    @PatchMapping("/{spotId}/status")
    fun updateSpotStatus(
        @PathVariable spotId: Long,
        @RequestParam status: SpotStatus,
    ): ResponseEntity<RsData<Unit>> {
        parkingSpotService.updateSpotStatusByAdmin(spotId, status)
        return ResponseEntity.ok(RsData("자리 상태가 ${status}로 변경되었습니다.", "200-1"))
    }
}