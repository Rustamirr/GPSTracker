package com.udmurtenergo.gpstracker.utils

import java.io.Serializable

data class Settings(
    val isBootService: Boolean,
    val minSnr: Int,
    val minAccuracy: Int,
    val minSatellitesCount: Int,
    val updateInterval: Int,
    val smallestDisplacement: Int,
    val serverIp: String,
    val networkUpdateInterval: Int) : Serializable