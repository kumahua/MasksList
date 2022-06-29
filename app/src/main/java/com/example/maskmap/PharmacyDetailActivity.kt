package com.example.maskmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.maskmap.data.Feature
import com.example.maskmap.databinding.ActivityPharmacyDetailBinding

class PharmacyDetailActivity : AppCompatActivity() {
    private val data by lazy { intent.getSerializableExtra("data") as? Feature }

    private val name by lazy { data?.properties?.name }
    private val maskAdultAmount by lazy { data?.properties?.mask_adult }
    private val maskChildAmount by lazy { data?.properties?.mask_child }
    private val phone by lazy { data?.properties?.phone }
    private val address by lazy { data?.properties?.address }
    private val available by lazy { data?.properties?.available }

    private lateinit var binding: ActivityPharmacyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_pharmacy_detail)

        binding = ActivityPharmacyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        val date =available?.split("、")
        val dates = StringBuffer()
        if (date != null) {
            for(day in date) {
                if(date.indexOf(day)%2 == 0) {
                    dates.append("$day \t|")
                } else {dates.append("\t $day \n")}
            }
        }

        binding.tvName.text = name ?: "資料發生錯誤"
        binding.tvAdultAmount.text = maskAdultAmount.toString()
        binding.tvChildAmount.text = maskChildAmount.toString()
        binding.tvPhone.text = phone
        binding.tvAddress.text = address
        binding.tvDate.text = dates.toString()
    }
}