package com.hakanemik.foodappcompose.retrofit

import com.hakanemik.foodappcompose.entity.FoodAnswer
import retrofit2.Call
import retrofit2.http.GET

interface FoodDaoInterface {
    @GET("yemekler/tumYemekleriGetir.php")
    fun allFoods(): Call<FoodAnswer>
}