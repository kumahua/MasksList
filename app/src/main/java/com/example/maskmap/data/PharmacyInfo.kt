package com.example.maskmap.data

import com.google.gson.annotations.SerializedName

class PharmacyInfo(
    @SerializedName("type")
    val type: String,

    @SerializedName("features")
    val features: List<Feature>
)

class Feature(
    @SerializedName("properties")
    val property: Property
)

class Property(
    @SerializedName("name")
    val name: String
)
