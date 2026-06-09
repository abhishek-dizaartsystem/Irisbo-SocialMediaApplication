package com.example.sociamediaapplication.model.response

data class TransactionHistoryResponse(
    val success: Boolean,
    val data: TransactionHistoryData
)

data class TransactionHistoryData(
    val pagination: PaginationResponse,
    val transactions: List<TransactionData>
)

data class TransactionData(
    val id: Int,
    val user_id: Int,
    val entry_type: String,
    val balance_bucket: String,
    val direction: String,
    val source_type: String,
    val source_id: Int,
    val gross_amount: Float,
    val fee_amount: Float,
    val net_amount: Float,
    val currency_code: String,
    val status: String,
    val notes: String,
    val metadata: TransactionMetadata,
    val created_at: String
)

data class TransactionMetadata(
    val account_type: String,
    val withdrawal_id: Int,
    val payout_account_id: Int
)
