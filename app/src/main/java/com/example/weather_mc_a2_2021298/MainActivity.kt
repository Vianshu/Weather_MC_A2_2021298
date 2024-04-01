package com.example.weather_mc_a2_2021298

import com.example.weather_mc_a2_2021298.ui.theme.Weather_MC_A2_2021298Theme
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.myapplication.Model.Weather
import com.example.myapplication.RestAPIInstance
import com.example.myapplication.Temp
import com.example.myapplication.Wt_DB
import com.example.myapplication.data
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WRepo.initialize(this)
        val repo = WRepo.get()
        var datasuccess = false
        val currentDate = LocalDate.now()
        GlobalScope.launch {
            try {
                Log.d("TRYING_IV", " ---> TRYING TO GET DATA 2021 2024-03-20 , ${currentDate.minusDays(2).toString()}")
//                Change the sd parameter , to add more history data till 1950 , due to large amount of data , i have added from 2000.
                val response = getAsyncData("2000-01-01", currentDate.minusDays(2).toString())

                val last2 = RestAPIInstance.apil2.getl2(
                    "52.52",
                    "13.41",
                    "2",
                    "temperature_2m"
                )
                val url = last2.raw().request.url.toString()
                Log.d("API_URL", url)

                Log.d("TRYING_", "SUCCESS ${last2.body()}")

                if (last2 != null) {
                    last2.body()?.let { SaveData(response, it,repo) }
                }
                datasuccess = true
                val inputValue=currentDate.toString()
//                var weatherS by remember { mutableStateOf<Temp?>(null) }
                if(datasuccess){
                        try {
                            Log.d("TRYING_IV", " ---> INPUT DATE : ${inputValue.toString()}")
//                            ShowData(w = repo.gethist(inputValue))
//                            Log.d("SQL_RET", "PAST : Response successful from SQL $weatherS")

                        } catch (e: Exception) {
                            Log.d("ERROR_", e.toString())
                    }
                }
                Log.d("TRYING_", "SUCCESS ")
            } catch (e: Exception) {
                Log.d("ERROR_GETTING DATA", e.toString())
            }
        }
        setContent {
            Weather_MC_A2_2021298Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("BERLIN, GERMANY")
                    bgscreen(repo = repo)
                }
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun bgscreen(repo: WRepo) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.chk2),
                contentDescription = "Weather Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            Text(
                "Berlin, Germany",  //to change country enter corresponding longitude and latitude
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 20.dp),
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(54.dp))

            TakeInput(repo = repo)

        }
    }



    @Composable
    private fun ShowData(w: Weather) {
        val wmax = w.hourly.temperature_2m.max()
        val wmin = w.hourly.temperature_2m.min()
        Log.d("DSS_","max:$wmax   min:$wmin ${w.hourly.temperature_2m.size}")
        Text("Max Temperature : $wmax °C\n Min Temperature : $wmin °C")
    }

    @Composable
    private fun show(t: Temp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .background(Color.Transparent)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("MIN \n  ${t.min}",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp),
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add space between the boxes

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .background(Color.Transparent)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("MAX  \n ${t.max}",

                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp),
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 1.sp,
                    color = Color.White)
            }
        }
        Log.d("DSS_","max:${t.max}  min:${t.min} ")
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun SaveData(w:Weather, l:Weather, repo:WRepo) {
        try{
            val tdata = mutableListOf<data>()
//        val repo = WRepo.get()
            repo.clear()
            val cdt = LocalDate.now()
            var ld="cdt"
            Log.d("Starting DATA ADDED ","Till  ${ld} ")
            for (i in 0 until w.hourly.time.size step 24) {
                val datel = w.hourly.time[i].substringBefore('T')
                val segment = w.hourly.temperature_2m.subList(i, minOf(i + 24, w.hourly.time.size))
                val max_24 = segment.max()
                val min_24 = segment.min()
                repo.insertData(datel, min_24, max_24)
                ld=datel
            }

            Log.d("DATA ADDED ","Till  ${ld} ")
            val dt=LocalDate.parse(ld)
            if (l != null) {
                for (i in 0 until l.hourly.time.size step 24) {
                    val cdate = LocalDate.parse(l.hourly.time[i].substringBefore('T'))
                    if (dt < cdate && cdate<= cdt) {
                        val segment = l.hourly.temperature_2m.subList(i, minOf(i + 24, l.hourly.time.size))
                        val max_24 = segment.max()
                        val min_24 = segment.min()
                        repo.insertData(cdate.toString(), min_24, max_24)
                    }
                }
            }
            Log.d("DATA ADDED ","Till  ${ld} ")
            Log.d("REPO_", "DATA INSERT SUCCESS")
        }
        catch (e: Exception){
            Log.d("DATA ADDING ERROR ","${e}")
        }
    }

    @Composable
    fun InputDate(
        value: String,
        onValueChange: (String) -> Unit,
        textColor: Color = Color.White // Default text color is white
    ) {
        var textValue by remember { mutableStateOf(value) }
        var isError by remember { mutableStateOf(false) }

        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                imeAction = ImeAction.Next
            ),
            value = textValue,
            onValueChange = { newValue ->

                if (newValue.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                    val parts = newValue.split("-")
                    val year = parts[0].toInt()
                    val month = parts[1].toInt()
                    val day = parts[2].toInt()

                    val validYear = year > 2000

                    val validMonth = month in 1..12

                    val validDay = when (month) {
                        1, 3, 5, 7, 8, 10, 12 -> day in 1..31
                        4, 6, 9, 11 -> day in 1..30
                        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                            day in 1..29
                        } else {
                            day in 1..28
                        }
                        else -> false
                    }

                    isError = !(validYear && validMonth && validDay)
                } else {
                    isError = true
                }
                textValue = newValue
                if (!isError) {
                    onValueChange(newValue)
                }
            },
            label = {
                Text(
                    text = "Date (YYYY-MM-DD)",
                    color = textColor
                )
            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = textColor),
            isError = isError
        )
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun TakeInput(repo: WRepo) {
        var inputValue by remember { mutableStateOf("2021-01-01") }
        var weatherData by remember { mutableStateOf<Weather?>(null) }
        var weatherS by remember { mutableStateOf<Temp?>(null) }
        val coroutineScope = rememberCoroutineScope()
        val currentDate = LocalDate.now()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            weatherS?.let { show(t = weatherS!!) }
            InputDate(
                value = inputValue,
                onValueChange = { inputValue = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            Log.d("TRYING_IV", " ---> INPUT DATE : ${inputValue.toString()}")
                            if (inputValue > currentDate.toString()) {
                                weatherS = repo.getfuturetemp(inputValue)
                                Log.d("SQL_RET", "FUTURE : Response successful from SQL $weatherS")
                            } else {
                                weatherS = repo.gethist(inputValue)
                                Log.d("SQL_RET", "PAST : Response successful from SQL $weatherS")
                            }
                        } catch (e: Exception) {
                            Log.d("ERROR_", e.toString())
                        }
                    }
                },
//                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "GET DATA")
            }

//            if (datasuccess) {
//                Text("DATA SAVED")
//            }
        }
    }


    private suspend fun getAsyncData(sd: String,ed:String): Weather {
        val response = RestAPIInstance.apiold.getolddata(
            "52.52",
            "13.41",
            startdate = sd,
            enddate = ed,
            "temperature_2m"
        )

        val url = response.raw().request.url.toString()
        Log.d("API_URL", url)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw IllegalStateException("Failed to fetch weather data")
        }
    }



    class WRepo private constructor(context: Context) {
        private val database: Wt_DB = Room.databaseBuilder(
            context.applicationContext,
            Wt_DB::class.java,
            DB_NAME
        ).build()

        suspend fun insertData(date: String, min: Double?, max: Double?) {
            val newData =
                data(0, date, min, max) // Assuming uid is auto-generated and starts from 0
            database.WDao().insertAll(newData)
        }

        suspend fun gethist(date:String):Temp{
            return database.WDao().gethist(date)
        }
        suspend fun getALL(): List<data> = database.WDao().getAll()

        suspend fun getfuturetemp(date: String):Temp {
            return database.WDao().getfuturespec(date)
        }

        suspend fun clear(){
            database.WDao().deleteall()
        }
        suspend fun getd(date: String): List<data> {
            return database.WDao().getd(date)
        }

        companion object {
            private var INSTANCE: WRepo? = null
            fun initialize(context: Context) {
                if (INSTANCE == null) {
                    INSTANCE = WRepo(context)
                }
            }

            fun get(): WRepo {
                return INSTANCE
                    ?: throw IllegalStateException("Repository must be initialized")
            }
        }
    }
    companion object {
        private const val DB_NAME = "StoreTemp"
    }
}





// EXTRA
// Getting past day data directly from api
// in takeinput method
//Button(
//onClick = {
//    coroutineScope.launch {
//        try {
//            Log.d("TRYING_IV"," ---> INPUT DATE : ${inputValue.toString()}")
//            val response = getAsyncData(inputValue,inputValue)
//            weatherS=Temp(response.hourly.temperature_2m.min(),response.hourly.temperature_2m.max())
//            if (response != null) {
//                weatherData = response
//                Log.d("TRYING_","SUCCESS")
//            } else {
//                Log.d("ERROR_", "Response not successful")
//            }
//        } catch (e: Exception) {
//            Log.d("ERROR_", e.toString())
//        }
//    }
//},
//modifier = Modifier.fillMaxWidth()
//) {
//    Text(text = "GET TEMPERATURE")
//}