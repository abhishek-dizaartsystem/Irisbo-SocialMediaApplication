package com.example.sociamediaapplication.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.trace
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.preferences.TokenManager
import com.example.sociamediaapplication.data.repository.AuthRepository
import com.example.sociamediaapplication.model.AuthResponse
import com.example.sociamediaapplication.ui.theme.Black
import com.example.sociamediaapplication.ui.theme.Blue
import com.example.sociamediaapplication.ui.theme.GreyBtn
import com.example.sociamediaapplication.ui.theme.GreyTxt
import com.example.sociamediaapplication.ui.theme.LBlue
import com.example.sociamediaapplication.ui.theme.LGrey
import com.example.sociamediaapplication.ui.theme.Transparent
import com.example.sociamediaapplication.ui.theme.White
import com.example.sociamediaapplication.viewmodel.AuthViewModel
import kotlin.math.sign

@Composable
fun AuthScreen(
    authState: AuthResponse?,
    onLogin:(String, String) ->Unit = {email, password->},
    onSignup: (String, String, String, String)-> Unit = {name, email, password, confirmPassword->},
    onAuthSuccess: ()-> Unit = {}
){
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    var signinSelected by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        authState?.let { response ->

            // ❌ Error case (no token)
            if (response.token == null) {
                Toast.makeText(
                    context,
                    response.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            // ✅ Success case
            if (response.token != null) {
                Toast.makeText(
                    context,
                    if (signinSelected) "Sign in successful 🎉" else "Sign up successful 🎉",
                    Toast.LENGTH_SHORT
                ).show()
                onAuthSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LBlue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Column(
                    modifier = Modifier
                        .background(color = Blue, shape = RoundedCornerShape(50.dp))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.facebook_svgrepo_com),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .height(50.dp),
                        tint = White
                    )
                }

                Text(
                    text = "Social Media App",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Connect with friends easily.",
                    color = Color(0xFF9F9F9F),
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )

                Row(
                    modifier = Modifier
                        .background(
                            color = LGrey,
                            shape = RoundedCornerShape(16.dp)
                        ).padding(4.dp)
                ) {
                    Button(
                        onClick = {signinSelected = true},
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(signinSelected) White else LGrey
                        )
                    ) {
                        Text(
                            text = "Sign In",
                            color = if (signinSelected) Black else GreyTxt
                        )
                    }
                    Button(
                        onClick = { signinSelected = false },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(!signinSelected) White else LGrey
                        )
                    ) {
                        Text(
                            text = "Sign Up",
                            color = if(!signinSelected) Black else GreyTxt
                        )
                    }
                }

                if(!signinSelected){
                    TextField(
                        value = name,
                        onValueChange = {newValue->
                            name = newValue
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.profile_1341_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.height(20.dp)
                            )
                        },
                        modifier = Modifier
                            .padding(top = 30.dp).fillMaxWidth(),
                        label = {
                            Text(
                                text = "Name"
                            )
                        }
                    )
                    TextField(
                        value = userName,
                        onValueChange = {newValue->
                           userName = newValue
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.profile_1341_svgrepo_com),
                                contentDescription = "",
                                modifier = Modifier.height(20.dp)
                            )
                        },
                        modifier = Modifier
                            .padding(top = 30.dp).fillMaxWidth(),
                        label = {
                            Text(
                                text = "Userame"
                            )
                        }
                    )
                }



                TextField(
                    value = email,
                    onValueChange = {newValue->
                        email = newValue
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.keyboard_alt_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.height(20.dp)
                        )
                    },
                    modifier = Modifier
                        .padding(top = 30.dp).fillMaxWidth(),
                    label = {
                        Text(
                            text = "Email/Phone"
                        )
                    }
                )
                TextField(
                    value = password,
                    onValueChange = {newValue->
                        password = newValue
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.lock_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.height(20.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.chatreadhidden_svgrepo_com),
                            contentDescription = "",
                            modifier = Modifier.height(20.dp)
                        )
                    },
                    modifier = Modifier
                        .padding(top = 30.dp).fillMaxWidth(),
                    label = {
                        Text(
                            text = "Password"
                        )
                    }
                )

                Button(
                    onClick = {
                        if (signinSelected) {
                            onLogin(email, password)
                        }
                        else {
                            onSignup(name, userName, email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if(signinSelected) "Sign In" else "Sign Up",
                            fontSize = 20.sp
                        )
                    }
                }
                if (signinSelected){
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                            modifier = Modifier.height(17.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Text("Forgot Password?",
                                fontSize = 12.sp,
                                color = Blue
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier) {
                            Text(
                                text = "Don't have an account?",
                                fontSize = 12.sp
                            )
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = Transparent),
                                modifier = Modifier.height(17.dp).width(50.dp),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(0.dp)
                            ) {
                                Text("Register",
                                    fontSize = 12.sp,
                                    color = Blue
                                )
                            }
                        }

                    }
                }


            }

        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreenPreview(){

    AuthScreen(
        authState = null
    )
}