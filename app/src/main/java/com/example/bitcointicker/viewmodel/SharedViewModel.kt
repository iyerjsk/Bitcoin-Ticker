package com.example.bitcointicker.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitcointicker.api.model.TickerModel
import com.example.bitcointicker.repository.BlockchainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class SharedViewModel @ViewModelInject constructor(
    private val repository: BlockchainRepository
) : ViewModel() {
    val data: MutableLiveData<Pair<Long, TickerModel>> by lazy {
        MutableLiveData<Pair<Long, TickerModel>>().also {
            loadData()
        }
    }

    private val disposable = CompositeDisposable()

    private fun loadData() {
        Observable.interval(1, TimeUnit.SECONDS)
            .flatMap {
                return@flatMap repository.getTicker()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                data.value = Pair(System.currentTimeMillis(), it)
            }
    }
}