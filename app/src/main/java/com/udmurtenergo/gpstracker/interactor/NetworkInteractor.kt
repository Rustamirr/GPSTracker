/*package com.udmurtenergo.gpstracker.interactor

import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.database.model.LocationData
import com.udmurtenergo.gpstracker.database.model.Satellite
import com.udmurtenergo.gpstracker.utils.SSLConection
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NetworkInteractor {
    private var imei: String? = null
    private var url: String? = null
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ZZZZZ", Locale.US)

    fun initialize(imei: String, serverIp: String) {
        this.imei = imei
        url = "https://$serverIp/soap/Iobserv"
    }

    @Throws(Exception::class)
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
        if (response != null) {
            if (response is SoapObject) {
                val returnProperty = response.getProperty("return").toString()
                when (returnProperty) {
                    NetworkResponse.NOT_FOUND_IMEI -> {
                        throw Exception(App.getInstance().getString(R.string.error_not_found_imei))
                    }
                }
            } else {
                throw Exception(
                    App.getInstance().getString(R.string.error_soap_fault_response) +
                            ": " + response
                )
            }
        } else {
            throw Exception(App.getInstance().getString(R.string.error_soap_null_respose))
        }
    }

    private object NetworkResponse {
        private val SUCCESSFUL = "1"
        private val NOT_FOUND_IMEI = "0"
    }

    companion object {
        private val NAMESPACE = "http://tempuri.org/"
        private val ACTION = "urn:testIntf-Iobserv#PUTDB"
        private val METHOD_NAME = "PUTDB_ShortF"
        private val FIX_SATELLITES = 20

        private val IMEI = "IMEI"
        private val LATITUDE = "Latitude"
        private val LONGITUDE = "Longitude"
        private val ACCURACY = "Accuracy"
        private val SPEED = "Speed"
        private val ALTITUDE = "Altitude"
        private val BEARING = "Bearing"
        private val TIME = "TimeZ"
        private val TIME_SEND = "TimeSendZ"
        private val LEVEL_OF_CHARGE = "LevelOfCharge"
        private val GSM_CLASS = "BondType"
        private val GSM_ASU = "BondLevel"
        private val SATELLITES_COUNT = "Satelites"
        private val SATELLITE_SNR = "L"
    }
}*/