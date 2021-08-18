package com.example.maskmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.maskmap.data.PharmacyInfo
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPharmacyData()
    }
    private fun getPharmacyData() {
        //口罩資料網址
        val pharmaciesDataUrl = "https://raw.githubusercontent.com/thishkt/pharmacies/master/data/info.json"

        //Part 1: 宣告 OkHttpClient
        val okHttpClient = OkHttpClient().newBuilder().build()

        //Part 2: 宣告 Request，要求要連到指定網址
        val request: Request = Request.Builder().url(pharmaciesDataUrl).get().build()

        //Part 3: 宣告 Call
        val call = okHttpClient.newCall(request)

        //執行 Call 連線後，採用 enqueue 非同步方式，獲取到回應的結果資料
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                Log.d("HKT", "onFailure: $e")
            }
            //注意要設定 UI ，需要執行在 UiThread 裡面，否則會噴錯誤

            override fun onResponse(call: Call, response: Response) {

                //從 Okhttp 收到的回應資料 response，取出 body 的部分。
                //注意這裏，response 不能二次使用，不然會噴錯誤。
                //所以我們將他轉存到 pharmaciesData 。
                val pharmaciesData = response.body?.string()

                val pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

                for(i in pharmacyInfo.features) {
                    Log.d("pharmacyData","藥局資料： ${i.properties.name}")
                    Log.d("pharmacyData","藥局電話： ${i.properties.phone}")
                }

                //將 pharmaciesData 整包字串資料，轉成 JSONObject 格式
//                val obj = JSONObject(pharmaciesData)

                //這個時候，我們就可以透過 getString 的方式，裡面放 key (name) 值，
                //即可以獲取到最外層的 type 欄位資料值。
                //Log.d("HKT",obj.getString("type"))

                //features 是一個陣列 [] ，需要將他轉換成 JSONArray
//                val featuresArray = JSONArray(obj.getString("features"))

//                val propertiesName = StringBuilder()
                //透過 for 迴圈，即可以取出所有的藥局名稱
//                for (i in 0 until featuresArray.length()) {
//                    val properties = featuresArray.getJSONObject(i).getString("properties")
//                    val property = JSONObject(properties)
//                    Log.d("HKT", "name: ${property.getString("name")}")
//                    propertiesName.append(property.getString("name") + "\n")
//                }

//                runOnUiThread{
                    //將 Okhttp 獲取到的回應值，指定到畫面的 TextView 元件中
//                    tv_pharmacies_data.text = propertiesName
//                }
            }
        })
    }
}