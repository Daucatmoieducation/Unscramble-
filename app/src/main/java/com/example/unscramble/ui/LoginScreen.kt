package com.example.unscramble.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unscramble.ui.theme.UnscrambleTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.example.unscramble.R

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // Lưu trữ thông báo lỗi
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email TextField
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 5.dp)
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

        // Password TextField
        GetTextField(
            value = password,
            onValueChanged = { password = it },
            label = "Nhập mật khẩu",
            keyboardOption = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardOptionDone = {
                Login(email, password, auth, navController) { errorText ->
                    errorMessage = errorText
                }
            },
            isPassword = true
        )

        // Hiển thị thông báo lỗi nếu có
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        // Login button
        OutlinedButton(onClick = {
            Login(email, password, auth, navController) { errorText ->
                errorMessage = errorText
            }
        }) {
            Text(stringResource(R.string.login), modifier = Modifier.padding(top = 5.dp))
        }
    }
}

private fun Login(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    onError: (String) -> Unit
) {
    if (email.isEmpty() || password.isEmpty()) {
        onError("Email hoặc mật khẩu không thể để trống")
        return
    }

    // Reset lỗi khi người dùng nhấn nút đăng nhập
    onError("")

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                navController.navigate("home_screen")
            } else {
                // Xử lý lỗi khi đăng nhập thất bại
                val exception = task.exception
                val errorText = when (exception) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        "Sai email hoặc mật khẩu. Vui lòng thử lại."
                    }
                    is FirebaseAuthUserCollisionException -> {
                        "Tài khoản này đã tồn tại."
                    }
                    is FirebaseAuthInvalidUserException -> {
                        "Không tìm thấy tài khoản này. Vui lòng kiểm tra lại email."
                    }
                    is FirebaseAuthEmailException -> {
                        "Email không hợp lệ. Vui lòng nhập lại."
                    }
                    is FirebaseNetworkException -> {
                        "Không thể kết nối tới máy chủ. Vui lòng kiểm tra lại kết nối mạng."
                    }
                    else -> {
                        "Đã xảy ra lỗi, vui lòng thử lại sau."
                    }
                }
                onError(errorText)
                Log.e("LoginError", "Error: $errorText")
            }
        }
}

@Composable
fun GetTextField(
    value: String,
    label: String,
    onValueChanged: (String) -> Unit,
    keyboardOptionDone: ()->Unit = {},
    keyboardOption: KeyboardOptions,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(label) },
        keyboardOptions = keyboardOption,
        keyboardActions = KeyboardActions(
          onDone = {keyboardOptionDone()}
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.padding(vertical = 5.dp)
            .width(300.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DemoPreview() {
    UnscrambleTheme {
        val navController = rememberNavController()
        LoginScreen(navController = navController)
    }
}

