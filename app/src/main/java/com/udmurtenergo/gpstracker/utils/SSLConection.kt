package com.udmurtenergo.gpstracker.utils

import android.util.Log

import javax.net.ssl.TrustManager
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

object SSLConection {

    private var trustManagers: Array<TrustManager>? = null

    class _FakeX509TrustManager : javax.net.ssl.X509TrustManager {

        @Throws(CertificateException::class)
        override fun checkClientTrusted(arg0: Array<X509Certificate>, arg1: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(arg0: Array<X509Certificate>, arg1: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return _AcceptedIssuers
        }

        companion object {
            private val _AcceptedIssuers = arrayOf<X509Certificate>()
        }
    }

    fun allowAllSSL() {

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier { hostname, session ->
            true //hostname.equals(PreferenceModel.DEFAULT_SERVER_IP);
        }

        val context: javax.net.ssl.SSLContext

        if (trustManagers == null) {
            trustManagers = arrayOf(_FakeX509TrustManager())
        }

        try {
            context = javax.net.ssl.SSLContext.getInstance("TLS")
            context.init(null, trustManagers, SecureRandom())
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
        } catch (e: NoSuchAlgorithmException) {
            Log.e("allowAllSSL", e.toString())
        } catch (e: KeyManagementException) {
            Log.e("allowAllSSL", e.toString())
        }

    }
}
