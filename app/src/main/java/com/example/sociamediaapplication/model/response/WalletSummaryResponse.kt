package com.example.sociamediaapplication.model.response

data class WalletSummaryResponse(
    val success: Boolean,
    val data: WalletSummaryData
)

data class WalletSummaryData(
    val user_id: Int,
    val total_balance: Int,
    val available_balance: Int,
    val pending_balance: Int,
    val total_earned: Float,
    val total_withdrawn: Float,
    val total_refunded: Int,
    val created_at: String?,
    val updated_at: String?
)
