package com.example.sociamediaapplication.data.remote

import com.example.sociamediaapplication.model.response.OrderwiseEarningsResponse
import com.example.sociamediaapplication.model.response.PayoutHistoryResponse
import com.example.sociamediaapplication.model.response.TransactionHistoryResponse
import com.example.sociamediaapplication.model.response.WalletSummaryResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface MonetizationApi {

    @GET("api/monetization/seller/wallet-summary")
    suspend fun getWalletSummary(
        @Header("Authorization") token: String
    ): WalletSummaryResponse

    @GET("api/monetization/seller/transactions")
    suspend fun getTransactionHistory(
        @Header("Authorization") token: String
    ): TransactionHistoryResponse

    @GET("api/monetization/seller/order-wise-earnings")
    suspend fun getOrderwiseEarnings(
        @Header("Authorization") token: String
    ): OrderwiseEarningsResponse

    @GET("api/monetization/seller/payout-history")
    suspend fun getPayoutHistory(
        @Header("Authorization") token: String
    ): PayoutHistoryResponse
}