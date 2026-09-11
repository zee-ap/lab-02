package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = {cityRepository.addCity( it )},
                        onDeleteCity = {cityRepository.deleteCity(it)},
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onDeleteCity: (String) -> Unit,
    modifier: Modifier = Modifier
){
    var newCityName by remember {mutableStateOf(value = "")}
    var selectedCity by remember { mutableStateOf("")}

    Column(modifier = modifier.fillMaxSize()) {
        Row() {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Button(
                    onClick = {
                        if (newCityName.isNotBlank()) {
                            onAddCity(newCityName)
                            newCityName = ""
                        }
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .height(35.dp),
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Add City") }

                Spacer(modifier = Modifier.height(2.dp))
                Button(
                    onClick = {
                        if (selectedCity.isNotBlank()) {
                            onDeleteCity(selectedCity)
                        }
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .height(35.dp),
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Delete City") }
            }

        }

        LazyColumn(modifier = modifier.fillMaxSize()){
            items(cities){
                city -> CityRow(
                city =city,
                isSelected = city == selectedCity,
                onCityClick = {
                    selectedCity = it
                }
            )
            }
        }
    }
}

@Composable
fun CityRow(city: String, onCityClick: (String) -> Unit, isSelected: Boolean){
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier.fillMaxWidth().padding(horizontal=18.dp, vertical = 0.dp).background(if(isSelected)Color.LightGray else Color.Transparent).clickable{
            onCityClick(city)
        }.padding(top = 10.dp, bottom = 10.dp, start = 0.dp, end = 0.dp)
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}
class CityRepository{
    private val _cities = mutableStateListOf("edmonton", "vancouver", "moscow", "sydney","berlin","vienna","tokyo","beijing","osaka","new delhi")

    val cities: List<String>
        get() = _cities

    fun addCity(city: String){
    _cities.add(city)
    }
    fun deleteCity(city:String){
        _cities.remove(city)
    }
}
