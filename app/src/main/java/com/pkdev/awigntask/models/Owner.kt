package com.pkdev.awigntask.models


import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val id: Int,
    val subscriptionsUrl: String,
    val type: String,
    val url: String
)