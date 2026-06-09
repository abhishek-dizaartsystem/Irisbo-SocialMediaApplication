package com.example.sociamediaapplication.data.repository

import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.remote.MonetizationApi
import com.example.sociamediaapplication.data.remote.RetrofitClient
import com.example.sociamediaapplication.model.response.OrderwiseEarningsResponse
import com.example.sociamediaapplication.model.response.PayoutHistoryResponse
import com.example.sociamediaapplication.model.response.TransactionHistoryResponse
import com.example.sociamediaapplication.model.response.WalletSummaryResponse

class MonetizationRepository(
    private val tokenManager: TokenManager
) {

    private val api = RetrofitClient.monetizationApi

    suspend fun getWalletSummary(): WalletSummaryResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getWalletSummary(token)
    }

    suspend fun getTransactionHistory(): TransactionHistoryResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getTransactionHistory(token)
    }

    suspend fun getOrderWiseEarningsResponse(): OrderwiseEarningsResponse{
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getOrderwiseEarnings(token)
    }

    suspend fun getPayoutHistory(): PayoutHistoryResponse {
        val token = "Bearer ${tokenManager.getToken()}"

        return api.getPayoutHistory(token)
    }
}