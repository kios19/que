package com.kioko.testapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "payouttable")
data class PayoutStorage(
    @PrimaryKey
    val account: String,
    @ColumnInfo(name="amount")
    val amount: String,

)