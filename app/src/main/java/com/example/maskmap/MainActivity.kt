package com.example.maskmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maskmap.Util.OkHttpUtil
import com.example.maskmap.Util.OkHttpUtil.Companion.mOkHttpUtil
import com.example.maskmap.data.PharmacyInfo
import com.example.maskmap.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        getPharmacyData()
    }

    private fun initView() {
        // 定義 LayoutManager 為 LinearLayoutManager
        viewManager = LinearLayoutManager(this)

        // 自定義 Adapte 為 MainAdapter，稍後再定義 MainAdapter 這個類別
        viewAdapter = MainAdapter()

        //傳統寫法
//        binding.recyclerView.layoutManager = viewManager
//        binding.recyclerView.adapter = viewAdapter

        //簡化寫法
        binding.recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter

            //設定每個項目寬度、高度固定，可以提高效能，不用每條項目，都要讓系統重新去計算 size。
            setHasFixedSize(true)

            //每個項目，加入分隔線(divider)，讓每筆資料看起來更清楚
//            addItemDecoration(
//                DividerItemDecoration(
//                    this@MainActivity,
//                    DividerItemDecoration.VERTICAL
//                )
//            )
        }
    }

    //    封裝OkHtttp後
    private fun getPharmacyData() {
        //顯示忙碌圈圈
        binding.progressBar.visibility = View.VISIBLE

        mOkHttpUtil.getAsync(PHARMACIES_DATA_URL, object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                //藥局名稱變數宣告
                //var propertiesName = StringBuilder()

                val pharmaciesData = response.body?.string()

                val pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

//                for (i in pharmacyInfo.features) {
//                    propertiesName.append(i.properties.name + "\n")
//                }

                runOnUiThread {
                    //binding.tvPharmaciesData.text = propertiesName

                    //將下載的口罩資料，指定給 MainAdapter
                    viewAdapter.pharmacyList = pharmacyInfo.features

                    //關閉忙碌圈圈
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(e: okio.IOException) {
                Log.d("HKT", "onFailure: $e")

                runOnUiThread {
                    //關閉忙碌圈圈
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }
}