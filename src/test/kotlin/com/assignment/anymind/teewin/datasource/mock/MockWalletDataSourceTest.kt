package com.assignment.anymind.teewin.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockWalletDataSourceTest {
    private val mockDataSource = MockWalletDataSource()
    
    @Test
    fun `should provide a collection of wallet history`() {
        // when
        val wallets = mockDataSource.retrieveHistory()
        
        // then
        assertThat(wallets).isNotEmpty
    }
}