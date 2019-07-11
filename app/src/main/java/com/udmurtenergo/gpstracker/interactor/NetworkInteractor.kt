package com.udmurtenergo.gpstracker.interactor

import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.utils.SSLConection
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.text.SimpleDateFormat
import java.util.*

class NetworkInteractor {
    private lateinit var imei: String
    private lateinit var url: String
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ZZZZZ", Locale.US)

    companion object {
        private const val NAMESPACE = "http://tempuri.org/"
        private const val ACTION = "urn:testIntf-Iobserv#PUTDB"
        private const val METHOD_NAME = "PUTDB_ShortF"
        private const val FIX_SATELLITES = 20

        private const val IMEI = "IMEI"
        private const val LATITUDE = "Latitude"
        private const val LONGITUDE = "Longitude"
        private const val ACCURACY = "Accuracy"
        private const val SPEED = "Speed"
        private const val ALTITUDE = "Altitude"
        private const val BEARING = "Bearing"
        private const val TIME = "TimeZ"
        private const val TIME_SEND = "TimeSendZ"
        private const val LEVEL_OF_CHARGE = "LevelOfCharge"
        private const val GSM_CLASS = "BondType"
        private const val GSM_ASU = "BondLevel"
        private const val SATELLITES_COUNT = "Satelites"
        private const val SATELLITE_SNR = "L"

        private const val SUCCESSFUL = "1"
        private const val NOT_FOUND_IMEI = "0"
    }

    fun initialize(imei: String, serverIp: String) {
        this.imei = imei
        url = "https://$serverIp/soap/Iobserv"
    }

    fun sendData(fullLocation: FullLocation, sendTime: Date) {
        val request = SoapObject(NAMESPACE, METHOD_NAME)

        // Данные по спутникам
        val locationData = fullLocation.locationData
        val satellites = fullLocation.satellites
        val satellitesCount = satellites.size
        request.addProperty(SATELLITES_COUNT, satellitesCount.toString())

        if (satellitesCount < FIX_SATELLITES) {
            for (i in 1..satellitesCount) {
                request.addProperty(SATELLITE_SNR + i, satellites[i - 1].snr.toString())
            }
            // Дополняем до fix размера
            for (i in satellitesCount + 1..FIX_SATELLITES) {
                request.addProperty(SATELLITE_SNR + i, "NULL")
            }
        } else {
            for (i in 1..satellitesCount) {
                request.addProperty(SATELLITE_SNR + i, satellites[i - 1].snr.toString())
            }
        }
        request.addProperty(IMEI, imei)
        request.addProperty(LATITUDE, locationData.latitude.toString())
        request.addProperty(LONGITUDE, locationData.longitude.toString())
        request.addProperty(ACCURACY, locationData.accuracy.toString())
        request.addProperty(SPEED, locationData.speed.toString())
        request.addProperty(ALTITUDE, locationData.altitude.toString())
        request.addProperty(BEARING, locationData.bearing.toString())
        request.addProperty(TIME, dateFormat.format(locationData.date))
        request.addProperty(TIME_SEND, dateFormat.format(sendTime))
        request.addProperty(LEVEL_OF_CHARGE, "")
        request.addProperty(GSM_CLASS, "")
        request.addProperty(GSM_ASU, "")

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = true
        envelope.setOutputSoapObject(request)

        val httpTransport = HttpTransportSE(url)
        SSLConection.allowAllSSL() // Отключает проверку сертификата
        httpTransport.call(ACTION, envelope)

        val response = envelope.response
        when (response) {
            is SoapObject -> {
                when (response.getProperty("return").toString()) {
                    NOT_FOUND_IMEI -> throw Exception(App.instance.getString(R.string.error_not_found_imei))
                }
            }
            else -> throw Exception("{App.instance.getString(R.string.error_soap_fault_response)}: $response")
        }
    }
}