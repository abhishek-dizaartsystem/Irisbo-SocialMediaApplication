package com.example.sociamediaapplication.model.response

data class PayoutHistoryResponse(
    val success: Boolean,
    val data: PayoutHistoryData
)

data class PayoutHistoryData(
    val payouts: List<Payout>,
    val pagination: PaginationResponse
)

data class Payout(
    val id: Int,
    val payout_batch_id: Int,
    val batch_reference: String,
    val seller_id: Int,

    val amount: Double,
    val currency_code: String,

    val status: String,
    val batch_status: String,

    val failure_reason: String?,

    val processed_at: String?,
    val paid_at: String?,

    val created_at: String
)
