package com.kioko.testapp.storage

class PaymentsRepository(private val paymentStorageDao: PaymentStorageDao){

    suspend fun getPayments(): PaymentsStorage{
        return paymentStorageDao.getPayments()
    }

    suspend fun insertPayment(payment: PaymentsStorage){
        paymentStorageDao.insertPayment(payment)
    }

    suspend fun insertPayout(payment: PayoutStorage){
        paymentStorageDao.insertPayout(payment)
    }

    suspend fun getPayouts(): List<PayoutStorage>{
        return paymentStorageDao.getPayouts()
    }

    suspend fun removePayout(pay:String){
        paymentStorageDao.deletePayout(pay)
    }

}