
package com.example.parking.domain.payment.repository

import com.example.parking.domain.payment.entity.Payment
import com.example.parking.domain.payment.entity.PaymentStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PaymentRepositoryCustom {
    fun findAllByStatus(status: PaymentStatus): List<Payment>
    fun findAllWithReservationAndUserPaged(pageable: Pageable): Page<Payment>
    fun findAllByUserIdWithReservationAndUserPaged(userId: Long, pageable: Pageable): Page<Payment>
    fun findAllByStatusPaged(status: PaymentStatus, pageable: Pageable): Page<Payment>
}