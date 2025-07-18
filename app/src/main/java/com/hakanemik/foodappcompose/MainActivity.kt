package com.hakanemik.foodappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.hakanemik.foodappcompose.entity.Foods
import com.hakanemik.foodappcompose.ui.theme.FoodAppComposeTheme
import com.hakanemik.foodappcompose.viewmodel.HomePageViewModel
import com.skydoves.landscapist.glide.GlideImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodAppComposeTheme {
                PageSelect()
            }
        }
    }
}

@Composable
fun PageSelect() {
    val navController= rememberNavController()
    NavHost(navController=navController,startDestination = "home_page"){
        composable("home_page"){
            HomePage(navController)
        }
        composable("details_page/{food}", arguments = listOf(
            navArgument("food"){
                type= NavType.StringType
            }
        )){
            val json=it.arguments?.getString("food")
            val food=Gson().fromJson(json, Foods::class.java)
            DetailsPage(food)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage( navController: NavController) {
    val viewModel:HomePageViewModel= viewModel()
    val foodList= viewModel.foodList.observeAsState(listOf())

    Scaffold(
        modifier =  Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Yemekler")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.primary),
                    titleContentColor = colorResource(id = R.color.white)
                )

            )
        },
        content = {
            paddingValues->
            LazyColumn(contentPadding = paddingValues){
                items(
                    count = foodList.value!!.count(),
                    itemContent = {
                        val food=foodList.value!![it]
                        Card(modifier = Modifier.padding(all = 5.dp).fillMaxWidth()) {
                            Row(modifier=Modifier.clickable {
                                val foodJson=Gson().toJson(food)
                                navController.navigate("details_page/$foodJson")
                            }) {
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(all = 10.dp).fillMaxWidth()) {

                                    GlideImage(
                                        imageModel = {"http://kasimadalan.pe.hu/yemekler/resimler/${food.food_image}"},
                                        modifier = Modifier.size(100.dp)

                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()) {
                                        Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight()) {
                                            Text(text = food.food_name, fontSize = 20.sp)
                                            Spacer(modifier = Modifier.size(10.dp))
                                            Text(text = "${food.food_price}", fontSize = 20.sp, color = Color.Blue)
                                        }
                                        Icon(
                                            painter = painterResource(id = R.drawable.arrow_right),
                                            contentDescription = ""
                                        )
                                    }

                                }
                            }
                        }
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodAppComposeTheme {

    }
}