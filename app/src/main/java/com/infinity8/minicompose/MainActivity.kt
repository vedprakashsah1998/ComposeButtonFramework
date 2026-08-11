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
import com.infinity8.compose_button_framework.gradient.ButtonGradient
import com.infinity8.compose_button_framework.node.ButtonStyle
import com.infinity8.compose_button_framework.node.colorResource
import com.infinity8.compose_button_framework.runtime.MiniCompose
import com.infinity8.compose_button_framework.widget.Button
import com.infinity8.compose_button_framework.widget.ComposeButton

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

                    ComposeButton(
                        text = "Login",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth().height(56.dp).padding(horizontal = 64.dp),
                        backgroundColor = "#2962FF".toColorInt(),
                        cornerRadius = 16.dp,
                        textColor = com.infinity8.compose_button_framework.node.colorResource(R.color.black),
                        elevation = 8.dp,
                        fontFamily = FontFamily.Resource(R.font.tccc_unitytext_bold),
                        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
                        onClick = {}
                    )




                Spacer(modifier = Modifier.height(16.dp))


                    ComposeButton(
                        text = "Create Account",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth().height(56.dp).padding(horizontal = 32.dp),
                        style = ButtonStyle.Outlined,
                        borderWidth = 2.dp,
                        borderColor = "#2962FF".toColorInt(),
                        textColor = "#2962FF".toColorInt(),
                        cornerRadius = 16.dp,
                        onClick = {}
                    )



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
                        isUpperCase = true,
                        onClick = {}

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
                        contentAlignment = com.infinity8.compose_button_framework.node.Alignment.Center,
                        onClick = {}

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
                        contentAlignment = com.infinity8.compose_button_framework.node.Alignment.Start,
                        onClick = {}

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
                        overflow = TextOverflow.MiddleEllipsis,
                        onClick = {}

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
                        overflow = TextOverflow.Clip,
                        onClick = {}

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
                        overflow = TextOverflow.Visible,
                        onClick = {}

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
                        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
                        onClick = {}

                    )
                }

                // ============================================================
// GRADIENT BUTTON EXAMPLES
// ============================================================

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Gradient Buttons",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))


// ------------------------------------------------------------
// 1. HORIZONTAL GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Horizontal Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                "#FF512F".toColorInt(),
                                "#DD2476".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 2. HORIZONTAL GRADIENT - THREE COLORS
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Three Color Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                "#FF6A00".toColorInt(),
                                "#EE0979".toColorInt(),
                                "#8E2DE2".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 3. VERTICAL GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Vertical Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Vertical(
                            colors = listOf(
                                "#667EEA".toColorInt(),
                                "#764BA2".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 4. VERTICAL GRADIENT - THREE COLORS
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Vertical Three Color",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Vertical(
                            colors = listOf(
                                "#00C6FF".toColorInt(),
                                "#0072FF".toColorInt(),
                                "#001F7A".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 5. DIAGONAL - TOP LEFT TO BOTTOM RIGHT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Diagonal Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Diagonal(
                            colors = listOf(
                                "#00C6FF".toColorInt(),
                                "#0072FF".toColorInt()
                            ),

                            startX = 0f,
                            startY = 0f,
                            endX = 1f,
                            endY = 1f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 6. DIAGONAL - TOP RIGHT TO BOTTOM LEFT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Reverse Diagonal",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Diagonal(
                            colors = listOf(
                                "#F857A6".toColorInt(),
                                "#FF5858".toColorInt()
                            ),

                            startX = 1f,
                            startY = 0f,
                            endX = 0f,
                            endY = 1f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 7. DIAGONAL - THREE COLORS
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Diagonal Three Color",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Diagonal(
                            colors = listOf(
                                "#FC466B".toColorInt(),
                                "#3F5EFB".toColorInt(),
                                "#00F2FE".toColorInt()
                            ),

                            startX = 0f,
                            startY = 0f,
                            endX = 1f,
                            endY = 1f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 8. RADIAL GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Radial Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Radial(
                            colors = listOf(
                                "#FFD700".toColorInt(),
                                "#FF8C00".toColorInt(),
                                "#FF4500".toColorInt()
                            ),

                            centerX = 0.5f,
                            centerY = 0.5f,
                            radius = 1f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 9. RADIAL - CENTER LEFT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Radial Left",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Radial(
                            colors = listOf(
                                "#FFFFFF".toColorInt(),
                                "#00C6FF".toColorInt(),
                                "#0072FF".toColorInt()
                            ),

                            centerX = 0.2f,
                            centerY = 0.5f,
                            radius = 1f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 10. RADIAL - TOP LEFT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Radial Top Left",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Radial(
                            colors = listOf(
                                "#FFFFFF".toColorInt(),
                                "#8E2DE2".toColorInt(),
                                "#4A00E0".toColorInt()
                            ),

                            centerX = 0.15f,
                            centerY = 0.15f,
                            radius = 1f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 11. SWEEP GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Sweep Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Sweep(
                            colors = listOf(
                                "#FF0000".toColorInt(),
                                "#FFFF00".toColorInt(),
                                "#00FF00".toColorInt(),
                                "#00FFFF".toColorInt(),
                                "#0000FF".toColorInt(),
                                "#FF00FF".toColorInt(),
                                "#FF0000".toColorInt()
                            ),

                            centerX = 0.5f,
                            centerY = 0.5f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 12. SWEEP GRADIENT - TWO COLORS
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Sweep Two Color",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Sweep(
                            colors = listOf(
                                "#FF512F".toColorInt(),
                                "#DD2476".toColorInt(),
                                "#FF512F".toColorInt()
                            ),

                            centerX = 0.5f,
                            centerY = 0.5f
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 13. RESOURCE COLORS - HORIZONTAL
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Resource Color Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                colorResource(R.color.gradient_start),
                                colorResource(R.color.gradient_end)
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 14. RESOURCE COLORS - VERTICAL
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Resource Vertical Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Vertical(
                            colors = listOf(
                                colorResource(R.color.gradient_start),
                                colorResource(R.color.gradient_end)
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 15. MIXED COLORS
// Resource + Hex + Android Color
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Mixed Color Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                colorResource(R.color.gradient_start),
                                "#673AB7".toColorInt(),
                                Color.Red.toArgb()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 16.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 16. BLUE PREMIUM GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Premium Blue",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                "#396AFB".toColorInt(),
                                "#2948FF".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 14.dp,
                        elevation = 8.dp,
                        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 17. PURPLE GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Premium Purple",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                "#8E2DE2".toColorInt(),
                                "#4A00E0".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 14.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 18. GREEN GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Success",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                "#11998E".toColorInt(),
                                "#38EF7D".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 14.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 19. ORANGE / RED GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Warning",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                "#FF8008".toColorInt(),
                                "#FFC837".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 14.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


// ------------------------------------------------------------
// 20. PINK GRADIENT
// ------------------------------------------------------------

                MiniCompose {
                    Button(
                        text = "Pink Gradient",
                        modifier = com.infinity8.compose_button_framework.modifier.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 32.dp),

                        gradient = ButtonGradient.Horizontal(
                            colors = listOf(
                                "#FF416C".toColorInt(),
                                "#FF4B2B".toColorInt()
                            )
                        ),

                        textColor = Color.White.toArgb(),
                        cornerRadius = 14.dp,
                        elevation = 8.dp,
                        onClick = {}

                    )
                }


            }

        }
    }
}


