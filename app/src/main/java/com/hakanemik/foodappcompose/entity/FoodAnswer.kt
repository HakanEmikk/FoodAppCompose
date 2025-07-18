package com.hakanemik.foodappcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FoodAnswer(
    @SerializedName("yemekler")
    @Expose
    val foods: List<Foods>,
    @SerializedName("success")
    @Expose
    var success: Int,
){}
