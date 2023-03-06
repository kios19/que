package com.kioko.testapp.payments

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kioko.testapp.api.models.*
import com.kioko.testapp.ui.theme.TestappTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pay(navController: NavController){



    val context = LocalContext.current

    val paymentsViewModel: PaymentsViewModel = viewModel(
        factory = PaymentsViewModel.PaymentsViewModelFactory(context.applicationContext as Application)
    )

    LaunchedEffect(key1 = payoutloading){
        GlobalScope.launch {
            var check = paymentsViewModel.checkConnection(context)
            if(check){
                val pending = paymentsViewModel.sendPending(context)
            }
        }
    }
    TestappTheme() {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .absolutePadding(
                            left = 20.dp,
                            right = 20.dp,
                            top = 40.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "Make payment",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        OutlinedTextField(
                            label= { Text(text = "Account")},
                            value = account.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .absolutePadding(top = 40.dp),
                            onValueChange = {
                                account.value = it.toString()
                            }
                        )
                        OutlinedTextField(
                            label= { Text(text = "Amount")},
                            value = amount.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .absolutePadding(top = 10.dp),
                            onValueChange = {
                                 amount.value= it.toString()
                            }
                        )
                        
                        ElevatedButton(
                            enabled = !paymentsloading.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .absolutePadding(top = 20.dp),
                            onClick = {
                                paymentsViewModel.makePayments(context)
                            }
                        ) {
                            if (payoutloading.value){
                                Text("Processing....")
                            }else{
                                Text("Submit")
                            }
                        }
                    }
                }
            }
        }
    }
}