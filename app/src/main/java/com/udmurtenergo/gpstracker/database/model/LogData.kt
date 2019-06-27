package com.udmurtenergo.gpstracker.database.model

import java.util.Date

data class LogData (
    val id: Long,
    val title: String,
    val description: String,
    val date: Date) {

    constructor(title: String, description: String, date: Date): this(
        0,
        title,
        description,
        date)
}


