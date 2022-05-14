package com.assignment.anymind.teewin.model

import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.validation.constraints.DecimalMin

data class Wallet(
    val datetime: ZonedDateTime,
    @field:DecimalMin(value = "0.0", inclusive = false, message = "The decimal value can not be less than 0.0")
    val amount: BigDecimal
)