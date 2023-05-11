package com.pkdev.awigntask.models


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("created_at")
    val createdAt: String,
    val description: String,
    val id: Int,
    val language: String,
    @SerializedName("languages_url")
    val languagesUrl: String,
    val name: String,
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("watchers_count")
    val watcherCount:Int,
    val score:Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)