package com.kioko.testapp.api.data

data class PaymentsResponseItem(
    val balance: Int,
    val company: String,
    val paid: Int,
    val paymentid: String,
    val transaction_date: String
)