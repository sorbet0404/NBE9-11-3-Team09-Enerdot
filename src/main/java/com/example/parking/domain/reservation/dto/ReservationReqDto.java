package com.example.parking.domain.reservation.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ReservationReqDto(
    @field:NotNull(message = "주차장 ID는 필수입니다.")
    val parkingLotId: Long,

    @field:NotNull(message = "주차 자리 ID는 필수입니다.")
    val parkingSpotId: Long,

    @field:NotNull(message = "시작 시간은 필수입니다.")
    val startTime: String,

    @field:NotNull(message = "종료 시간은 필수입니다.")
    val endTime: String
) {
    val parsedStartTime: LocalDateTime
        get() = parseTime(this.startTime)

    val parsedEndTime: LocalDateTime
        get() = parseTime(this.endTime)

    private fun parseTime(timeStr: String): LocalDateTime {
        var time = timeStr
        if (time.contains(" 24:00")) {
            time = time.replace(" 24:00", " 00:00")
            return LocalDateTime.parse(time, CUSTOM_FORMATTER).plusDays(1)
        }
        return LocalDateTime.parse(time, CUSTOM_FORMATTER)
    }

    companion object {
        private val CUSTOM_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}