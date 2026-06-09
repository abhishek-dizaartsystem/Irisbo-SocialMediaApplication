package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.MonetizationRepository
import com.example.sociamediaapplication.model.response.OrderwiseEarningsResponse
import com.example.sociamediaapplication.model.response.PayoutHistoryResponse
import com.example.sociamediaapplication.model.response.TransactionHistoryResponse
import com.example.sociamediaapplication.model.response.WalletSummaryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MonetizationViewModel(
    private val repository: MonetizationRepository
): ViewModel() {

    private val _walletSummary = MutableStateFlow<WalletSummaryResponse?>(null)
    val walletSummary: StateFlow<WalletSummaryResponse?> = _walletSummary

    private val _transactionHistory = MutableStateFlow<TransactionHistoryResponse?>(null)
    val transactionHistory: StateFlow<TransactionHistoryResponse?> = _transactionHistory

    private val _orderwiseEarnings = MutableStateFlow<OrderwiseEarningsResponse?>(null)
    val orderwiseEarnings: StateFlow<OrderwiseEarningsResponse?> = _orderwiseEarnings

    private val _payoutHistory =
        MutableStateFlow<PayoutHistoryResponse?>(null)

    val payoutHistory:
            StateFlow<PayoutHistoryResponse?> =
        _payoutHistory

    fun fetchPayoutHistory(){
        viewModelScope.launch {
            try {
                _payoutHistory.value =
                    repository.getPayoutHistory()
            }catch (e: Exception){
                Log.e(
                    "PAYOUT_DEBUG",
                    e.message.toString()
                )
            }
        }
    }

    fun getWalletSummary(){
        viewModelScope.launch {
            try {
                _walletSummary.value = repository.getWalletSummary()
            }catch (e: Exception){
                Log.e("Monetization_DEBUG", e.message.toString())
            }
        }
    }

    fun getTransactionHistory(){
        viewModelScope.launch {
            try {
                _transactionHistory.value = repository.getTransactionHistory()
            }catch (e: Exception){
                Log.e("Monetization_DEBUG", e.message.toString())
            }
        }
    }

    fun getOrderwiseEarnings(){
        viewModelScope.launch {
            try {
                _orderwiseEarnings.value = repository.getOrderWiseEarningsResponse()
            }catch (e: Exception){
                Log.e("Monetization_DEBUG", e.message.toString())
            }
        }
    }

}