package com.example.unscramble.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.ui.theme.UnscrambleTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navController: NavController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope() // Coroutine để delay chuyển màn hình
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Đăng ký",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        GetTextField(
            value = email,
            onValueChanged = { email = it },
            label = "Nhập email",
            keyboardOption = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        GetTextField(
            value = password,
            onValueChanged = { password = it },
            label = "Nhập mật khẩu",
            keyboardOption = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            isPassword = true
        )

        GetTextField(
            value = confirmPassword,
            onValueChanged = { confirmPassword = it },
            label = "Nhập lại mật khẩu",
            keyboardOption = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isPassword = true
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 5.dp))
        }

        OutlinedButton(
            onClick = {
                if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    errorMessage = "Vui lòng nhập đầy đủ thông tin!"
                } else if (password.length < 6) {
                    errorMessage = "Mật khẩu phải có ít nhất 6 ký tự!"
                } else if (password != confirmPassword) {
                    errorMessage = "Mật khẩu không khớp!"
                } else {
                    // Nếu hợp lệ, gọi Firebase để đăng ký
                    Signup(email, password, auth) { resultText, success ->
                        if (success) {
                            successMessage = resultText
                            errorMessage = ""
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(successMessage)
                                delay(1000)
                                navController.navigate("login_screen")
                            }
                        } else {
                            errorMessage = resultText
                        }
                    }
                }
            },
            modifier = Modifier.padding(top = 10.dp).width(300.dp)
        ) {
            Text(text = "Đăng ký", fontSize = 16.sp, color = Color.Black)
        }

        Text(
            text = "Đã có tài khoản? Đăng nhập",
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable { navController.navigate("login_screen") }
        )

        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(top = 10.dp))
    }
}

private fun Signup(
    email: String,
    password: String,
    auth: FirebaseAuth,
    onResult: (String, Boolean) -> Unit
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult("Đăng ký thành công!", true)
            } else {
                onResult(task.exception?.message ?: "Đăng ký thất bại!", false)
            }
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewSign(){
    UnscrambleTheme {
        val navController = rememberNavController()
        SignupScreen(navController)
    }
}