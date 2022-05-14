package com.assignment.anymind.teewin.service

import com.assignment.anymind.teewin.datasource.WalletDataSource
import com.assignment.anymind.teewin.model.HistoryDatetimeRange
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class WalletServiceTest {

    private val dataSource: WalletDataSource = mockk(relaxed = true)

    private val walletService = WalletService(dataSource)

    private val datetimeRange: HistoryDatetimeRange = mockk(relaxed = true)

    @Test
    fun `should call it's data source to retrieve history`() {
        // when
        walletService.getHistory(datetimeRange)
        
        // then
        verify(exactly = 1) { dataSource.retrieveHistory() }
        
    }
}