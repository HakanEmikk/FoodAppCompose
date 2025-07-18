package com.hakanemik.foodappcompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hakanemik.foodappcompose.entity.Foods
import com.hakanemik.foodappcompose.repo.FoodDaoRepository

class HomePageViewModel : ViewModel() {
    var foodDaoRepository = FoodDaoRepository()
    var foodList= MutableLiveData<List<Foods>>()
    init {
        getfood()
        foodList=foodDaoRepository.getFoods()
    }
    fun getfood(){
        foodDaoRepository.allFoods()
    }
}