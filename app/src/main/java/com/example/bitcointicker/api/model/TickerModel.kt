package com.example.bitcointicker.api.model

import com.google.gson.annotations.SerializedName

data class TickerModel(
    @SerializedName("USD") val usPriceModel: PriceModel,
    @SerializedName("CAD") val canadaPriceModel: PriceModel,
    @SerializedName("EUR") val europePriceModel: PriceModel
)