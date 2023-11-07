package com.example.luvdub.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "쉽고 빠르고 새로운\n우리만의 연락방법")
        Text(text = "쉽고 빠르고 123")
        // 여기에 로그인 UI 구성 요소를 추가합니다.
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}