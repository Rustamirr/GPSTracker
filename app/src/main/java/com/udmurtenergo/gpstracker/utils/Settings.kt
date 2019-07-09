package com.udmurtenergo.gpstracker.utils

import java.io.Serializable

data class Settings(
    var isBootService: Boolean = false,
    var minSnr: Int = 0,
    var minAccuracy: Int = 0,
    var minSatellitesCount: Int = 0,
    var updateInterval: Int = 0,
    var smallestDisplacement: Int = 0,
    var serverIp: String? = null,
    var networkUpdateInterval: Int = 0) : Serializable