package com.infinity8.minicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.infinity8.compose_button_framework.extension.height
import com.infinity8.compose_button_framework.extension.padding
import com.infinity8.compose_button_framework.node.ButtonStyle
import com.infinity8.compose_button_framework.runtime.MiniCompose
import com.infinity8.compose_button_framework.widget.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F7FA))
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(72.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    alignment = Alignment.Center,
                    contentDescription = "fowkjer",
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Welcome Back",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Sign in to continue",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = {
                        Text("Email")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    }
                )


                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = {
                        Text("Password")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    },
                    visualTransformation =
                        if (passwordVisible)
                            androidx.compose.ui.text.input.VisualTransformation.None
                        else
                            androidx.compose.ui.text.input.PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Forgot Password?",
                    color = Color(0xFF2962FF)
                )

                Spacer(modifier = Modifier.height(32.dp))
                MiniCompose {
                    Button(
                        text = "Login",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth().height(56.dp).padding(horizontal = 64.dp),
                        backgroundColor = "#2962FF".toColorInt(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))
                
                MiniCompose {
                    Button(
                        text = "Create Account",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth().height(56.dp).padding(horizontal = 32.dp),
                        style = ButtonStyle.Outlined,
                        borderWidth = 2.dp,
                        borderColor = "#2962FF".toColorInt(),
                        textColor = "#2962FF".toColorInt(),
                        cornerRadius = 16.dp,
                    )
                }

            }

        }
    }
}


