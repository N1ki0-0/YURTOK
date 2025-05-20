package com.example.yurtok.presentation.screens.signup

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.yurtok.R
import com.example.yurtok.presentation.navigation.Route
import com.example.yurtok.presentation.screens.login.LoginEvent
import com.example.yurtok.presentation.screens.profile.MyTextField
import com.example.yurtok.presentation.state.Resource


@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: SignupViewModel = hiltViewModel()
    ){

    val signupFlow by viewModel.registerState.collectAsState()

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onEvent(SignupEvent.OnAvatarChange(uri))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.register),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth(0.25f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Register",
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.2f),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { }
                    .padding(horizontal = 35.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    modifier  = Modifier.size(30.dp)
                )
            }
        }

        Text(
            text = "Or, login with...",
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(0.5f)
        )

        MyTextField(
            text = viewModel.name,
            hint = "Username",
            leadingIcon = Icons.Outlined.AccountCircle,
            keyboardType = KeyboardType.Text,
            onValueChange = { viewModel.onEvent(SignupEvent.OnNameChange(it)) }
        )

        MyTextField(
            text = viewModel.email,
            hint = "Email",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            onValueChange = { viewModel.onEvent(SignupEvent.OnEmailChange(it)) }
        )

        MyTextField(
            text = viewModel.password,
            hint = "Password",
            leadingIcon = Icons.Outlined.Lock,
            isPassword = true,
            onValueChange = {viewModel.onEvent(SignupEvent.OnPasswordChange(it))}
        )

        Button(onClick = { pickerLauncher.launch("image/*") }) {
            Text("Выбрать аватар")
        }
        viewModel.avatar?.let { uri ->
            Image(painter = rememberImagePainter(uri), contentDescription = null, Modifier.size(80.dp))
        }

        Button(
            onClick = {viewModel.signup() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Login",
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Do you have an account? ",
                fontSize = 16.sp,
            )
            Text(
                text = "Login",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { navController.navigate(Route.LOGIN) }
            )
        }

        Spacer(modifier = Modifier.height(1.dp))
    }

    when (val result = signupFlow) {
        is Resource.Loading -> { /* show loader */ }
        is Resource.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate(Route.PROFILE) {
                    popUpTo(Route.SIGNUP) { inclusive = true }
                }
            }
        }
        is Resource.Failure -> {
            val context = LocalContext.current
            Toast.makeText(context, result.e.message, Toast.LENGTH_LONG).show()
            Log.e("Map", "${result.e.message}")
        }

        null -> {}

    }

}