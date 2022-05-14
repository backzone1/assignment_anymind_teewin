package com.assignment.anymind.teewin.datasource.mock

import com.assignment.anymind.teewin.datasource.WalletDataSource
import com.assignment.anymind.teewin.model.Wallet
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.ZonedDateTime

@Repository
class MockWalletDataSource : WalletDataSource {

    val wallets = mutableListOf(
        Wallet(ZonedDateTime.parse("2022-05-12T13:00:00+00:00"), BigDecimal.valueOf(1000))
//        Wallet(ZonedDateTime.parse("2022-05-12T13:50:05+00:00"), BigDecimal.valueOf(1000)),
//        Wallet(ZonedDateTime.parse("2022-05-12T14:05:25+00:00"), BigDecimal.valueOf(5)),
//        Wallet(ZonedDateTime.parse("2022-05-12T15:10:00+00:00"), BigDecimal.valueOf(10)),
//        Wallet(ZonedDateTime.parse("2022-05-12T16:15:00+00:00"), BigDecimal.valueOf(15))
    )

    override fun retrieveHistory(): Collection<Wallet> = wallets

    override fun depositBitcoinToWallet(wallet: Wallet): Wallet {
        wallets.add(wallet)
        return wallet
    }
}