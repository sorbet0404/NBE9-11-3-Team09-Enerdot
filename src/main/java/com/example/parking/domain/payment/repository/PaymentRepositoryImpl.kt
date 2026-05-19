
package com.example.parking.domain.payment.repository

import com.example.parking.domain.payment.entity.Payment
import com.example.parking.domain.payment.entity.PaymentStatus
import com.example.parking.domain.payment.entity.QPayment
import com.example.parking.domain.reservation.entity.QReservation
import com.example.parking.domain.user.entity.QUser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : PaymentRepositoryCustom {

    private val payment = QPayment.payment
    private val reservation = QReservation.reservation
    private val user = QUser.user

    override fun findAllByStatus(status: PaymentStatus): List<Payment> =
        queryFactory
            .selectFrom(payment)
            .join(payment.reservation, reservation).fetchJoin()
            .join(reservation.user, user).fetchJoin()
            .where(payment.status.eq(status))
            .fetch()

    override fun findAllWithReservationAndUserPaged(pageable: Pageable): Page<Payment> {
        val content = queryFactory
            .selectFrom(payment)
            .join(payment.reservation, reservation).fetchJoin()
            .join(reservation.user, user).fetchJoin()
            .orderBy(payment.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(payment.count())
            .from(payment)
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }

    override fun findAllByUserIdWithReservationAndUserPaged(userId: Long, pageable: Pageable): Page<Payment> {
        val content = queryFactory
            .selectFrom(payment)
            .join(payment.reservation, reservation).fetchJoin()
            .join(reservation.user, user).fetchJoin()
            .where(reservation.user.id.eq(userId))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(payment.count())
            .from(payment)
            .join(payment.reservation, reservation)
            .where(reservation.user.id.eq(userId))
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }

    override fun findAllByStatusPaged(status: PaymentStatus, pageable: Pageable): Page<Payment> {
        val content = queryFactory
            .selectFrom(payment)
            .join(payment.reservation, reservation).fetchJoin()
            .join(reservation.user, user).fetchJoin()
            .where(payment.status.eq(status))
            .orderBy(payment.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(payment.count())
            .from(payment)
            .where(payment.status.eq(status))
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }
}