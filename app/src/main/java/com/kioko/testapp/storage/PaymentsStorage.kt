package com.kioko.testapp.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paymentstable")
data class PaymentsStorage(
    @PrimaryKey
    val paymentid: String,
    @ColumnInfo(name = "balance")
    val balance: Int,
    @ColumnInfo(name = "company")
    val company: String,
    @ColumnInfo(name = "paid")
    val paid: Int,
    @ColumnInfo(name = "transaction_date")
    val transaction_date: String
)