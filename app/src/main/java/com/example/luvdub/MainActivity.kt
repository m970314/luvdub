package com.example.luvdub

import android.os.Bundle
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoveLogin() // Loing 화면으로 이동
        }
    }
}

@Composable
fun MoveLogin() {
    val navController = rememberNavController()
    // loginfragment로 이동(초기화면)
    NavHost(
        navController = navController,
        startDestination = "navigation_login"
    ) {
        composable("navigation_login") {
            LoginFragment().LoginScreen()
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