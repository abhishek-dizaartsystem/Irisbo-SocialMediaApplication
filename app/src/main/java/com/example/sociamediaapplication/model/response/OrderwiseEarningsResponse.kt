package com.example.sociamediaapplication.model.response

data class OrderwiseEarningsResponse(
    val success: Boolean,
    val data: OrderwiseEarningsData
)

data class OrderwiseEarningsData(
    val pagination: PaginationResponse,
    val earnings: List<EarningData>
)

data class EarningData(
    val id: Int,
    val order_id: Int,
    val order_item_id: Int,
    val product_id: Int,
    val seller_id: Int,
    val quantity: Int,
    val vendor_price: Int,
    val markup_percent: Int,
    val markup_amount: Int,
    val final_unit_price: Int,
    val final_line_amount: Int,
    val seller_gross_amount: Int,
    val platform_gross_amount: Int,
    val gateway_fee: Float,
    val seller_fee_share: Float,
    val platform_fee_share: Float,
    val seller_net_amount: Float,
    val platform_net_amount: Float,
    val earning_status: String,
    val payout_eligible_at: String,
    val payment_status: String,
    val order_status: String,
    val created_at: String
)