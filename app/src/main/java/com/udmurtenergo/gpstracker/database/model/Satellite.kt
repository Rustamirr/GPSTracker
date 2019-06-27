package com.udmurtenergo.gpstracker.database.model

data class Satellite(
    val id: Long,
    val snr: Float,
    val location: LocationData) {

    constructor(snr: Float, location: LocationData): this(
        0,
        snr,
        location)
}