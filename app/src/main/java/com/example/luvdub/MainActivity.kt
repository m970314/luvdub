package com.example.luvdub

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.luvdub.Login.LoginFragment
import com.example.luvdub.ui.theme.LuvdubTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.luvdub.UsetInforSet.UserInforSetFragment
import com.kakao.sdk.common.util.Utility

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveFrgment() // Loing 화면으로 이동
        }
    }
}

// frgment별 composable 선언
@Composable
fun MoveFrgment() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "navigation_login"
    ) {
        // loginfragment로 이동(초기화면)
        composable("navigation_login") {
            LoginFragment().LoginScreen(navController)
        }

        // Uesr 데이터 입력화면으로 이동
        composable("navigation_user_set") {
            UserInforSetFragment().UserInforSetScrren()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LuvdubTheme {
        Greeting("Android")
    }
}