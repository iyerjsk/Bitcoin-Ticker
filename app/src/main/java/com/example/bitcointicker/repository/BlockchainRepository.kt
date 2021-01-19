package com.example.bitcointicker.repository

import com.example.bitcointicker.api.BlockChainApi
import javax.inject.Inject

class BlockchainRepository @Inject constructor(private val api: BlockChainApi) {
    fun getTicker()  = api.getTicker();
}