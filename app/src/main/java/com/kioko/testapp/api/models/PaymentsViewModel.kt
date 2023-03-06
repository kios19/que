package com.kioko.testapp.api.models

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kioko.testapp.api.MyApi
import com.kioko.testapp.api.data.PaymentsResponse
import com.kioko.testapp.api.data.PaymentsResponseItem
import com.kioko.testapp.storage.PaymentDatabase
import com.kioko.testapp.storage.PaymentsRepository
import com.kioko.testapp.storage.PaymentsStorage
import com.kioko.testapp.storage.PayoutStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

var payments = mutableStateListOf<PaymentsResponseItem>()
var paymentsloading = mutableStateOf(false)
var payoutloading = mutableStateOf(false)
var submitloading = mutableStateOf(false)


var account = mutableStateOf("")
var amount = mutableStateOf("")

class PaymentsViewModel(app: Application): AndroidViewModel(app){

    val repo: PaymentsRepository

    init{
        val paymentsDao = PaymentDatabase.getInstance(app).paymentStorageDao()
        repo = PaymentsRepository(paymentsDao)
    }

    fun Notify(context: Context, account:String){
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(context, "payment made to " + account, Toast.LENGTH_SHORT).show()
        }
    }
    suspend fun sendPending(context: Context){
        var all = repo.getPayouts()
        if (all.size > 0){
            GlobalScope.launch(Dispatchers.IO) {
                for (item in all){
                    Notify(context, item.account)
                    repo.removePayout(item.account)
                }
            }
        }
    }

    fun checkConnection(context: Context): Boolean {
        var cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        var isConnected: Boolean = activeNetwork?.isConnected == true
        return isConnected
    }

    fun saveToPayouts(context: Context){
        GlobalScope.launch(Dispatchers.IO) {
            val pay = PayoutStorage(
                account = account.value,
                amount = amount.value
            )
            repo.insertPayout(pay)
        }
    }

    fun makePayments( context: Context){
        if(account.value.length <=0 || amount.value.length <=0){
            Toast.makeText(context, "please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            GlobalScope.launch(Dispatchers.Main) {
                var check = checkConnection(context)
                if (!check){
                    saveToPayouts(context)
                    Toast.makeText(context, "payment saved for later", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "payment successfully processed", Toast.LENGTH_SHORT).show()
                    account.value = ""
                    amount.value = ""
                }
            }
        }
    }
    fun getPayments(context: Context){
        GlobalScope.launch(Dispatchers.IO) {
            paymentsloading.value = true
            MyApi().getPayments()
                .enqueue(object : Callback<PaymentsResponse>{
                    override fun onFailure(call: Call<PaymentsResponse>, t: Throwable){
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        paymentsloading.value = false
                    }

                    override fun onResponse(
                        call: retrofit2.Call<PaymentsResponse>,
                        response: Response<PaymentsResponse>
                    ){
                        response.body()?.let {
                            payments.swapList(it)
                            paymentsloading.value = false
                        }

                    }
                })
        }
    }

    class PaymentsViewModelFactory(
        private val application: Application
    ):ViewModelProvider.Factory{
        override fun<T : ViewModel> create(modelClass: Class<T>):T{
            @Suppress("UNCHECKED_CAST")
            if(modelClass.isAssignableFrom(PaymentsViewModel::class.java)){
                return PaymentsViewModel(application) as T
            }
            throw java.lang.IllegalArgumentException("unknown viewmodel class")
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}