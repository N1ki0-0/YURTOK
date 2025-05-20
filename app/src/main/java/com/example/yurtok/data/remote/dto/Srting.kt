package com.example.yurtok.data.remote.dto

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody

fun String.toRequestBody(toMediaType: MediaType): RequestBody =
    this.toRequestBody("text/plain".toMediaType())