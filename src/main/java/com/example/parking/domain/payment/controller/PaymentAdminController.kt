package com.example.parking.domain.payment.controller

import com.example.parking.domain.payment.dto.PaymentAdminRespDto
import com.example.parking.domain.payment.dto.PaymentRespDto
import com.example.parking.domain.payment.entity.PaymentStatus
import com.example.parking.domain.payment.service.PaymentService
import com.example.parking.global.response.RsData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/payments")
@Tag(name = "관리자 - 결제", description = "관리자 결제 관련 API")
class PaymentAdminController(
    private val paymentService: PaymentService
) {
    @Operation(summary = "전체 결제 조회", description = "관리자 권한으로 시스템 내 전체 결제 내역을 페이지 단위로 조회합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 완료"),
        ApiResponse(responseCode = "500", description = "서버 내부 오류")
    )
    @GetMapping
    fun getAllPayments(
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<RsData<Page<PaymentAdminRespDto>>> {
        val data = paymentService.getAllPayments(pageable)
        return ResponseEntity.ok(RsData("전체 결제 내역 조회가 완료되었습니다.", "200-1", data))
    }

    @Operation(summary = "고객별 결제 조회", description = "특정 고객의 모든 결제 이력을 페이지 단위로 조회합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 완료"),
        ApiResponse(responseCode = "500", description = "서버 내부 오류")
    )
    @GetMapping("/{userId}")
    fun getPaymentsByUser(
        @PathVariable userId: Long,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<RsData<Page<PaymentAdminRespDto>>> {
        val data = paymentService.getPaymentsByUser(userId, pageable)
        return ResponseEntity.ok(RsData("고객별 결제 내역 조회가 완료되었습니다.", "200-2", data))
    }

    @Operation(summary = "결제 상태별 조회", description = "관리자 권한으로 결제 상태별 내역을 페이지 단위로 조회합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 완료"),
        ApiResponse(responseCode = "400", description = "잘못된 상태값"),
        ApiResponse(responseCode = "500", description = "서버 내부 오류")
    )
    @GetMapping("/status/{status}")
    fun getPaymentsByStatus(
        @PathVariable status: PaymentStatus,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<RsData<Page<PaymentAdminRespDto>>> {
        val data = paymentService.getPaymentsByStatus(status, pageable)
        return ResponseEntity.ok(RsData("결제 상태별 내역 조회가 완료되었습니다.", "200-3", data))
    }

    @Operation(summary = "환불 처리", description = "관리자 권한으로 특정 결제 건에 대한 강제 환불을 처리합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "환불 완료"),
        ApiResponse(responseCode = "400", description = "존재하지 않는 결제"),
        ApiResponse(responseCode = "409", description = "이미 환불됐거나 환불 불가 상태")
    )
    @PatchMapping("/{paymentId}/refund")
    fun refundPayment(@PathVariable paymentId: Long): ResponseEntity<RsData<PaymentRespDto>> {
        val data = paymentService.refundPayment(paymentId)
        return ResponseEntity.ok(RsData("환불 처리가 완료되었습니다.", "200-4", data))
    }
}