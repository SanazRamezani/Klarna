package com.klarna.challenge.model.data

data class Currently(
    var time: Long,
    var summary: String?,
    var icon: String?,
    var precipIntensity: Double,
    var precipProbability: Double,
    var temperature: Double,
    var apparentTemperature: Double,
    var dewPoint: Double,
    var humidity: Double,
    var pressure: Double,
    var windSpeed: Double,
    var windGust: Double,
    var windBearing: Int,
    var cloudCover: Double,
    var uvIndex: Int,
    var visibility: Double,
    var ozone: Double
)