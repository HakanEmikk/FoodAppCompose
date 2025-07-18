package com.hakanemik.foodappcompose.repo

import androidx.lifecycle.MutableLiveData
import com.hakanemik.foodappcompose.entity.FoodAnswer
import com.hakanemik.foodappcompose.entity.Foods
import com.hakanemik.foodappcompose.retrofit.ApiUtils
import com.hakanemik.foodappcompose.retrofit.FoodDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodDaoRepository {
    var foodList = MutableLiveData<List<Foods>>()
    var foodDaoInterface: FoodDaoInterface
    init {
       foodList= MutableLiveData()
       foodDaoInterface= ApiUtils.getFoodDaoInterface()

    }
    fun getFoods():MutableLiveData<List<Foods>>{
        return foodList
    }
   fun allFoods(){
    foodDaoInterface.allFoods().enqueue(object :Callback<FoodAnswer>{
        override fun onResponse(call: Call<FoodAnswer>, response: Response<FoodAnswer>) {
            val liste=response.body()!!.foods
            foodList.value=liste

        }

        override fun onFailure(call: Call<FoodAnswer>, t: Throwable) {

        }
    })
   }
}