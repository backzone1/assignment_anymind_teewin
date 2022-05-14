package com.assignment.anymind.teewin.datasource

import com.assignment.anymind.teewin.model.Wallet

interface WalletDataSource {

    fun retrieveHistory(): Collection<Wallet>

    fun depositBitcoinToWallet(wallet: Wallet): Wallet
}