package com.example.bitcointicker.api

import com.example.bitcointicker.api.model.TickerModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface BlockChainApi {

    @GET("ticker")
    fun getTicker(): Observable<TickerModel>
}