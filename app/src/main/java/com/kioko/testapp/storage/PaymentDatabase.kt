package com.kioko.testapp.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [PaymentsStorage::class, PayoutStorage::class], version = 1, exportSchema = false)
abstract class PaymentDatabase:RoomDatabase(){

    abstract fun paymentStorageDao(): PaymentStorageDao

    companion object{
        @Volatile
        private var INSTANCE: PaymentDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context):PaymentDatabase{
            kotlinx.coroutines.internal.synchronized(this){
                return INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    PaymentDatabase::class.java,
                    "payment_database"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}