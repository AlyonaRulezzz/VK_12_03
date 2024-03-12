package com.example.p

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.p.productlist.ProductListScreen
import com.example.p.ui.theme.PTheme
import dagger.hilt.android.AndroidEntryPoint

import com.example.p.productdetail.ProductDetailScreen
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "product_list_screen") {
                    composable("product_list_screen") {
                        ProductListScreen(navController = navController)
                    }
                    composable(
                        "product_detail_screen/{dominantColor}/{productName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("productName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val productName = remember {
                            it.arguments?.getString("productName")
                        }
                        ProductDetailScreen(
                            dominantColor = dominantColor,
                            productName = productName?.lowercase(Locale.ROOT) ?: "",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
