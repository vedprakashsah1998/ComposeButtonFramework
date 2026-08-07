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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.infinity8.compose_button_framework.FontFamily
import com.infinity8.compose_button_framework.TextOverflow
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
            val scrollState = rememberScrollState()

            var passwordVisible by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
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
                        textColor = com.infinity8.compose_button_framework.node.colorResource(R.color.black),
                        elevation = 8.dp,
                        fontFamily = FontFamily.Resource(R.font.tccc_unitytext_bold),
                        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold
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


                Spacer(modifier = Modifier.height(24.dp))

// Uppercase + Bold
                MiniCompose {
                    Button(
                        text = "Proceed to Checkout",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        backgroundColor = "#4CAF50".toColorInt(),
                        textColor = Color.White.toArgb(),
                        cornerRadius = 18.dp,
                        elevation = 6.dp,
                        fontFamily = FontFamily.Resource(R.font.tccc_unitytext_bold),
                        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
                        isUpperCase = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

// Center Alignment + Ellipsis
                MiniCompose {
                    Button(
                        text = "This is a very very very long button title that demonstrates Ellipsis overflow",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        backgroundColor = "#673AB7".toColorInt(),
                        textColor = Color.White.toArgb(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        contentAlignment = com.infinity8.compose_button_framework.node.Alignment.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

// Start Ellipsis
                MiniCompose {
                    Button(
                        text = "This is a very very very long button title for Start Ellipsis",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        backgroundColor = "#009688".toColorInt(),
                        textColor = Color.White.toArgb(),
                        maxLines = 1,
                        overflow = TextOverflow.StartEllipsis,
                        contentAlignment = com.infinity8.compose_button_framework.node.Alignment.Start
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

// Middle Ellipsis
                MiniCompose {
                    Button(
                        text = "ComposeButtonFrameworkVersion_1_0_0_Release.apk",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        backgroundColor = "#FF9800".toColorInt(),
                        textColor = Color.White.toArgb(),
                        maxLines = 1,
                        overflow = TextOverflow.MiddleEllipsis
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

// Clip
                MiniCompose {
                    Button(
                        text = "This text will simply be clipped when it reaches the end of the available width",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        backgroundColor = "#E91E63".toColorInt(),
                        textColor = Color.White.toArgb(),
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

// Visible Overflow
                MiniCompose {
                    Button(
                        text = "Visible Overflow Example Visible Overflow Example Visible Overflow Example",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        backgroundColor = "#607D8B".toColorInt(),
                        textColor = Color.White.toArgb(),
                        maxLines = 1,
                        overflow = TextOverflow.Visible
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

// Outlined + End Alignment
                MiniCompose {
                    Button(
                        text = "Outlined Button",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        style = ButtonStyle.Outlined,
                        borderWidth = 2.dp,
                        borderColor = "#3F51B5".toColorInt(),
                        textColor = "#3F51B5".toColorInt(),
                        contentAlignment = com.infinity8.compose_button_framework.node.Alignment.End,
                        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold
                    )
                }


            }

        }
    }
}


