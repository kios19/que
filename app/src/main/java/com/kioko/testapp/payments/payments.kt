package com.kioko.testapp.payments

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kioko.testapp.api.models.PaymentsViewModel
import com.kioko.testapp.api.models.payments
import com.kioko.testapp.api.models.paymentsloading
import com.kioko.testapp.ui.theme.TestappTheme
import com.kioko.testapp.ui.theme.moneyFont
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payments(navController: NavController){

    val context = LocalContext.current
    
    val paymentsViewModel: PaymentsViewModel = viewModel(
        factory = PaymentsViewModel.PaymentsViewModelFactory(context.applicationContext as Application)
    )
    
    LaunchedEffect(key1 = paymentsloading ){
        GlobalScope.launch(Dispatchers.IO) { 
            paymentsViewModel.getPayments(context)
        }
    }

    TestappTheme() {
        Surface() {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = {
                    if(paymentsloading.value){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(20.dp)
                            )
                        }

                    }else{
                        LazyColumn(
                            modifier = Modifier.absolutePadding(
                                left = 20.dp,
                                right = 20.dp,
                                top = 40.dp
                            ),
                            content = {
                                item{
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Card(
                                            onClick = { navController.navigate("pay")},
                                            modifier = Modifier.size(width = 70.dp, height = 60.dp),
                                            shape = RoundedCornerShape(20.dp),
                                            colors = CardDefaults.elevatedCardColors(
                                               MaterialTheme.colorScheme.tertiary
                                            )
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                            ) {
                                                Icon(imageVector = LineAwesomeIcons.PaperPlane, contentDescription = "send")
                                                Text("Send",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp)
                                            }
                                        }

                                        Card(
                                            onClick = { Toast.makeText(context, "route disabled", Toast.LENGTH_SHORT).show() },
                                            modifier = Modifier.size(width = 70.dp, height = 60.dp),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                            ) {
                                                Icon(imageVector = LineAwesomeIcons.FileInvoiceSolid, contentDescription = "send")
                                                Text("Bill",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp)
                                            }
                                        }

                                        Card(
                                            onClick = { Toast.makeText(context, "route disabled", Toast.LENGTH_SHORT).show()},
                                            modifier = Modifier.size(width = 70.dp, height = 60.dp),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                            ) {
                                                Icon(imageVector = LineAwesomeIcons.MobileSolid, contentDescription = "send")
                                                Text("Mobile",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp)
                                            }
                                        }
                                        Card(
                                            onClick = { Toast.makeText(context, "route disabled", Toast.LENGTH_SHORT).show() },
                                            modifier = Modifier.size(width = 70.dp, height = 60.dp),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                            ) {
                                                Icon(imageVector = LineAwesomeIcons.BoxesSolid, contentDescription = "send")
                                                Text("More",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 10.sp)
                                            }
                                        }
                                    }
                                }
                                item { 
                                    Box(
                                        modifier = Modifier.absolutePadding(
                                            top = 20.dp,
                                            bottom = 10.dp
                                        )
                                    ){
                                        Text(
                                            text = "Recent Activities",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                itemsIndexed(payments){
                                    index, item ->
                                    key(index) {
                                        ElevatedCard(
                                            colors = CardDefaults.elevatedCardColors(

                                            ),
                                            elevation = CardDefaults.elevatedCardElevation(),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .absolutePadding(
                                                    left = 0.dp,
                                                    right = 0.dp,
                                                    top = 0.dp,
                                                    bottom = 10.dp
                                                )
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .absolutePadding(
                                                        left = 20.dp,
                                                        right = 20.dp
                                                    )
                                                    .fillMaxWidth()
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            //.background(Color.Black)
                                                            .height(40.dp)
                                                    ) {
                                                        Icon(
                                                            imageVector = LineAwesomeIcons.ReceiptSolid,
                                                            contentDescription = "receipt")
                                                    }

                                                    Column(
                                                        modifier = Modifier
                                                            .padding(
                                                                20.dp
                                                            )
                                                    ) {
                                                        Text(
                                                            text = item.company,
                                                            fontWeight = FontWeight.Bold,
                                                            fontSize = 13.sp
                                                        )
                                                        Text(
                                                            text = item.transaction_date,
                                                            fontWeight = FontWeight.Normal,
                                                            color = Color.Gray,
                                                            fontSize = 13.sp
                                                        )
                                                    }
                                                }

                                                Text(
                                                    text =  "ksh " + String.format("%,d",item.paid.toInt()),
                                                    //fontFamily = moneyFont,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 18.sp,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}