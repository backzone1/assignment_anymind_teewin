package com.assignment.anymind.teewin.service

import com.assignment.anymind.teewin.datasource.WalletDataSource
import com.assignment.anymind.teewin.model.HistoryDatetimeRange
import com.assignment.anymind.teewin.model.Wallet
import org.springframework.stereotype.Service
import java.time.DateTimeException
import java.time.Duration

@Service
class WalletService(private val dataSource: WalletDataSource) {

    fun getHistory(datetimeRange: HistoryDatetimeRange): Collection<Wallet> {

        val result = mutableListOf<Wallet>()
        val data = dataSource.retrieveHistory()

        if (datetimeRange.startDatetime.isAfter(datetimeRange.endDatetime)){
            throw DateTimeException("Start date should less than end date ${datetimeRange.startDatetime} > ${datetimeRange.endDatetime}")
        }

        val timeHourRange = (Duration.between(datetimeRange.startDatetime,datetimeRange.endDatetime).toHours())

        for(i in 0..timeHourRange){
            val tempDate = datetimeRange.startDatetime.plusHours(i).withMinute(0).withSecond(0)
            val amount = data.filter { it.datetime.isBefore(tempDate.plusSeconds(1)) }.sumOf { it.amount }
            result.add(Wallet(tempDate, amount))
        }

        return result
    }

    fun addBalance(wallet: Wallet): Wallet = dataSource.depositBitcoinToWallet(wallet)



}