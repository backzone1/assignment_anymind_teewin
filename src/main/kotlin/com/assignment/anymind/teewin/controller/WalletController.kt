package com.assignment.anymind.teewin.controller

import com.assignment.anymind.teewin.model.HistoryDatetimeRange
import com.assignment.anymind.teewin.model.Wallet
import com.assignment.anymind.teewin.service.WalletService
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.json.JSONObject
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.time.DateTimeException
import javax.validation.Valid

@RestController
@RequestMapping("api/wallet")
class WalletController (private val service: WalletService){

    @ExceptionHandler(DateTimeException::class)
    fun handleDateTime(e: DateTimeException): ResponseEntity<String> {
        val resp = JSONObject()
        resp.put("code", HttpStatus.BAD_REQUEST)
        resp.put("message", e.message)
        return ResponseEntity(resp.toString(),getJsonHeader(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<String> {
        val resp = JSONObject()
        resp.put("code", HttpStatus.BAD_REQUEST)
        resp.put("message", "Amount should be more than 0")
        return ResponseEntity(resp.toString(),getJsonHeader(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(JsonParseException::class)
    fun handleJsonParse(e: JsonParseException): ResponseEntity<String> {
        val resp = JSONObject()
        resp.put("code", HttpStatus.BAD_REQUEST)
        resp.put("message", "Wrong Json format.")
        return ResponseEntity(resp.toString(),getJsonHeader(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    fun handleMissingKotlinParameter(e: MissingKotlinParameterException): ResponseEntity<String> {
        val resp = JSONObject()
        resp.put("code", HttpStatus.BAD_REQUEST)
        resp.put("message", "'${e.parameter.name}' field required.")
        return ResponseEntity(resp.toString(),getJsonHeader(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormat(e: InvalidFormatException): ResponseEntity<String> {
        val resp = JSONObject()
        resp.put("code", HttpStatus.BAD_REQUEST)
        resp.put("message", "'${e.value}' is not correct should input correctly data format. (${e.targetType.typeName})")
        return ResponseEntity(resp.toString(),getJsonHeader(), HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/history")
    fun getHistory(@RequestBody datetimeRange: HistoryDatetimeRange): Collection<Wallet> = service.getHistory(datetimeRange)

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    fun addBalance(@Valid @RequestBody wallet: Wallet): ResponseEntity<String> {
        service.addBalance(wallet)

        val resp = JSONObject()
        resp.put("code", HttpStatus.CREATED)
        resp.put("message", "Deposit bitcoin successfully.")
        return ResponseEntity(resp.toString(),getJsonHeader(), HttpStatus.CREATED)
    }

    fun getJsonHeader(): HttpHeaders {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        return httpHeaders
    }

}