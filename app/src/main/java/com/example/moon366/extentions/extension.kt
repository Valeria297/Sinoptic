package com.example.moon366

import android.content.Context
import android.widget.Toast

//Toast extension
fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()