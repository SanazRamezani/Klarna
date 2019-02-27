package com.klarna.challenge.model.data

data class Weather(
    var latitude: Double,
    var longitude: Double,
    var timezone: String?,
    var currently: Currently?,
    var offset: Int

)