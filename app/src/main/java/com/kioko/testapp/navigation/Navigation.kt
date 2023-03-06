package com.kioko.testapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kioko.testapp.payments.Pay
import com.kioko.testapp.payments.Payments

@Composable
fun Boat(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home" ){
        composable("home"){ Payments(navController = navController)}
        composable("pay"){ Pay(navController = navController)}
    }
}