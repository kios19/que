package com.kioko.testapp.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
public interface PaymentStorageDao{
    @Query("SELECT * FROM paymentstable")
    fun getPayments(): PaymentsStorage

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayment(vararg payment: PaymentsStorage)

    @Query("SELECT * FROM payouttable")
    fun getPayouts(): List<PayoutStorage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayout(vararg payout: PayoutStorage)

    @Query("DELETE FROM payouttable WHERE account = :acc")
    fun deletePayout(acc:String)

}