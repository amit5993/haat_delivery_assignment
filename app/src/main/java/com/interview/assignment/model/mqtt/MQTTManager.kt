package com.interview.assignment.model.mqtt

import android.content.Context
import android.util.Log
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.android.service.MqttAndroidClient
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

class MQTTManager(context: Context, serverUri: String, clientId: String) {

    private var mqttAndroidClient: MqttAndroidClient
    private val TAG = "MQTTManager"

    init {
        mqttAndroidClient = MqttAndroidClient(context, serverUri, clientId)
    }

    fun connect(username: String, password: String, onConnected: (() -> Unit)? = null) {
        val mqttConnectOptions = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
            socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
//            socketFactory = getSSLSocketFactory()
            connectionTimeout = 60 // seconds
            keepAliveInterval = 120 // seconds
            this.userName = username
            this.password = password.toCharArray()
        }
//        mqttAndroidClient.setTraceEnabled(true)


        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Connected to MQTT broker")
                    onConnected?.invoke()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Failed to connect to MQTT broker", exception)
                }
            })
        } catch (e: MqttException) {
            Log.e(TAG, "Error connecting to MQTT broker", e)
        }
    }

    fun subscribe(topic: String, qos: Int = 0, onMessageReceived: (String) -> Unit) {
        try {
            mqttAndroidClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Subscribed to topic: $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Failed to subscribe to topic: $topic", exception)
                }
            })

            mqttAndroidClient.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    Log.e(TAG, "MQTT connection lost", cause)
//                    reconnect()
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    message?.let {
                        Log.d(TAG, "Message received from topic $topic: ${it.payload.decodeToString()}")
                        onMessageReceived(it.payload.decodeToString())
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d(TAG, "Message delivered")
                }
            })
        } catch (e: MqttException) {
            Log.e(TAG, "Error subscribing to topic $topic", e)
        }
    }

    fun getSSLSocketFactory(): SSLSocketFactory? {
        try {
            val context = SSLContext.getInstance("TLS")
            context.init(null, arrayOf(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            }), java.security.SecureRandom())
            return context.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun publish(topic: String, payload: String, qos: Int = 1, retained: Boolean = false) {
        try {
            val message = MqttMessage(payload.toByteArray()).apply {
                this.qos = qos
                isRetained = retained
            }
            mqttAndroidClient.publish(topic, message)
            Log.d(TAG, "Message published to topic $topic")
        } catch (e: MqttException) {
            Log.e(TAG, "Error publishing message to topic $topic", e)
        }
    }

    fun disconnect() {
        try {
            mqttAndroidClient.disconnect()
            Log.d(TAG, "Disconnected from MQTT broker")
        } catch (e: MqttException) {
            Log.e(TAG, "Error disconnecting from MQTT broker", e)
        }
    }
}
