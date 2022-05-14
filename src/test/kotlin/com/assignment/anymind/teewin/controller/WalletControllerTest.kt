package com.assignment.anymind.teewin.controller

import org.json.JSONObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class WalletControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
) {
    val baseUrl = "/api/wallet"

    @Nested
    @DisplayName("GET /api/wallet/history")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetHistory {

        @Test
        fun `should return all history`() {
            //given
            val resp = JSONObject()
            resp.put("startDatetime", "2022-05-12T13:00:00+00:00")
            resp.put("endDatetime", "2022-05-12T16:00:00+00:00")
            // when
            val performGet = mockMvc.get("$baseUrl/history") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performGet
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return BAD REQUEST if start date more than end date`() {
            // given
            val resp = JSONObject()
            resp.put("startDatetime", "2022-05-12T15:00:00+00:00")
            resp.put("endDatetime", "2022-05-12T14:00:00+00:00")

            // when
            val performGet = mockMvc.get("$baseUrl/history") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performGet
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

    }

    @Nested
    @DisplayName("POST /api/wallet/deposit")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostDeposit {
        
        @Test
        fun `should add new history`() {
            // given
            val resp = JSONObject()
            resp.put("datetime", "2022-05-12T15:00:00+07:00")
            resp.put("amount", 500)
            
            // when
            val performPost = mockMvc.post("$baseUrl/deposit") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }
            
            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }
        
        @Test
        fun `should return BAD REQUEST if amount is Empty (1)`() {
            // given
            val resp = JSONObject()
            resp.put("datetime", "2022-05-12T15:00:00+07:00")

            // when
            val performPost = mockMvc.post("$baseUrl/deposit") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return BAD REQUEST if amount is Empty (2)`() {
            // given
            val resp = JSONObject()
            resp.put("datetime", "2022-05-12T15:00:00+07:00")
            resp.put("amount", "")

            // when
            val performPost = mockMvc.post("$baseUrl/deposit") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return BAD REQUEST if amount is NULL`() {
            // given
            val resp = JSONObject()
            resp.put("datetime", "2022-05-12T15:00:00+07:00")
            resp.put("amount", JSONObject.NULL)

            // when
            val performPost = mockMvc.post("$baseUrl/deposit") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return BAD REQUEST if amount less than or equal to 0 (1)`() {
            // given
            val resp = JSONObject()
            resp.put("datetime", "2022-05-12T15:00:00+07:00")
            resp.put("amount", "0")

            // when
            val performPost = mockMvc.post("$baseUrl/deposit") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return BAD REQUEST if amount less than or equal to 0 (2)`() {
            // given
            val resp = JSONObject()
            resp.put("datetime", "2022-05-12T15:00:00+07:00")
            resp.put("amount", "-500")

            // when
            val performPost = mockMvc.post("$baseUrl/deposit") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return BAD REQUEST if wrong data format`() {
            // given
            val resp = JSONObject()
            resp.put("datetime", "2022-05-12T15:00:00+07:00")
            resp.put("amount", "TestString")

            // when
            val performPost = mockMvc.post("$baseUrl/deposit") {
                contentType = MediaType.APPLICATION_JSON
                content = resp.toString()
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }

        }

    }
}