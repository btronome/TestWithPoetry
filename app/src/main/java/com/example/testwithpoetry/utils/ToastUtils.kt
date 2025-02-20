package com.example.testwithpoetry.utils

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    val context = LocalContext.current
    Toast.makeText(context, message, duration).show()
}