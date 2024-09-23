package com.interview.assignment.model.mqtt

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interview.assignment.model.Message
import com.interview.assignment.model.MqttResponse
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import com.rabbitmq.client.QueueingConsumer
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLSocketFactory


class RabbitMQClient(private val data: MqttResponse, private var mCallback: RabbitMQCallBack) {

    var mChannel: Channel? = null
    var mConnection: Connection? = null
    var mQueue: QueueingConsumer? = null
    var userName: String = data.userName ?: ""
    var password: String = data.password ?: ""
    var virtualHost: String = "/" // data.clientId ?: ""
    var host: String = data.host ?: ""
    var mRoutingKey: String = data.topic ?: "" // Publish & Subscribe
    var mQueueName: String = ""
    var type: String = "direct"
    var port: Int = data.port ?: 8883
    var protocol: String = data.protocol ?: "SSL"

    protected var running: Boolean = true
    var publishTimeout: Int = 5 * 1000
    private var publishThread: Thread? = null

    private val mCallbackHandler = Handler(Looper.getMainLooper())
    protected var isListening: Boolean = true

    var managerRabbitMQ: RabbitMQClient = this

    companion object {
        external fun GetRabbitMQData(): String?

        const val EXCHANGE_NAME: String = "" // exchange
    }

    init {
        // mQueueName = createDefaultQueueName();
        connectToRabbitMQ()
    }

    fun getInstance(mCallback: RabbitMQCallBack): RabbitMQClient {
        this.mCallback = mCallback
        return managerRabbitMQ
    }

    private fun connectToRabbitMQ() {
        running = true
        publishThread = Thread {
            while (running) {
                try {
                    initConnection()
                    initChannel()
                    running = false
                } catch (e: IOException) {
                    mCallback.onMQConnectionFailure(e.message ?: "")
                    try {
                        Thread.sleep(5000) //sleep and then try again
                    } catch (e1: InterruptedException) {
                        running = false
                        break
                    }
                } catch (e: TimeoutException) {
                    mCallback.onMQConnectionFailure(e.message ?: "")
                    try {
                        Thread.sleep(5000)
                    } catch (e1: InterruptedException) {
                        running = false
                        break
                    }
                }
            }
        }
        publishThread!!.start()
    }

    @Throws(IOException::class, TimeoutException::class)
    private fun createConnection() {
        val connectionFactory = ConnectionFactory()
        connectionFactory.username = userName
        connectionFactory.password = password
        connectionFactory.virtualHost = virtualHost
        connectionFactory.host = host
        connectionFactory.port = port
        connectionFactory.connectionTimeout = 60
        connectionFactory.useSslProtocol(protocol)
        connectionFactory.socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        connectionFactory.isAutomaticRecoveryEnabled = true
        mConnection = connectionFactory.newConnection()
    }

    private fun createChannel() {
        try {
            mChannel = mConnection!!.createChannel()
            mChannel?.basicQos(data.qos ?: 0)
            mChannel?.exchangeDeclare(EXCHANGE_NAME, type, true)
            mQueueName = mChannel?.queueDeclare()?.queue ?: ""
            mChannel?.queueBind(mQueueName, EXCHANGE_NAME, mRoutingKey)

            /*mQueue = QueueingConsumer(mChannel)
            mChannel?.basicConsume(mQueueName, mQueue)

            while (isListening) {
                val delivery: QueueingConsumer.Delivery? = mQueue?.nextDelivery()
                if (delivery != null) {
                    mChannel?.basicAck(delivery.getEnvelope().getDeliveryTag(), false)
                    mCallbackHandler.run {
                        try {
                            val message = String(delivery.getBody(), charset("UTF-8"))
                            mCallback.onMessageReceived(message)
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }
                    }
                }
            }*/

            val consumer: Consumer = object : DefaultConsumer(mChannel) {
                @Throws(IOException::class)
                override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
                    val message = String(body, charset("UTF-8"))
                    mCallback.onMessageReceived(message)
                }
            }

            mChannel?.basicConsume(mQueueName, consumer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val isConnected: Boolean
        get() {
            if (mConnection != null && mConnection!!.isOpen) {
                return true
            }
            return false
        }

    @Throws(IOException::class, TimeoutException::class)
    fun initConnection() {
        if (!isConnected) {
            createConnection()
        }
    }

    private fun initChannel() {
        if (!isChannelAvailable) {
            createChannel()
        }
    }

    private val isChannelAvailable: Boolean
        get() {
            if (mChannel != null && mChannel!!.isOpen) {
                return true
            }
            return false
        }

    fun sendMessage(txtMessage: String) {
        running = true
        publishThread = Thread {
            while (running) {
                try {
                    initConnection()
                    initChannel()
                    mChannel!!.confirmSelect()
                    mChannel!!.basicPublish(EXCHANGE_NAME, mRoutingKey, null, txtMessage.trim().toByteArray())
                    mChannel!!.waitForConfirms(publishTimeout.toLong())
                    running = false
                } catch (e: InterruptedException) {
                    mCallback.onMQConnectionFailure(e.message)
                    try {
                        Thread.sleep(5000) //sleep and then try again
                    } catch (e1: InterruptedException) {
                        running = false
                        break
                    }
                } catch (e: IOException) {
                    mCallback.onMQConnectionFailure(e.message)
                    try {
                        Thread.sleep(5000)
                    } catch (e1: InterruptedException) {
                        running = false
                        break
                    }
                } catch (e: TimeoutException) {
                    mCallback.onMQConnectionFailure(e.message)
                    try {
                        Thread.sleep(5000)
                    } catch (e1: InterruptedException) {
                        running = false
                        break
                    }
                }
            }
        }
        publishThread!!.start()
    }

    private fun registerChanelListHost() {
        try {
            mChannel!!.exchangeDeclare(EXCHANGE_NAME, "direct", true)
            val queueName = mChannel!!.queueDeclare().queue
            mChannel!!.queueBind(queueName, EXCHANGE_NAME, "warning")

            val consumer: Consumer = object : DefaultConsumer(mChannel) {
                @Throws(IOException::class)
                override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
                    //Verify if device that send the info is different of the are receiver
//                    getHeader(properties);

                    val message = String(body, charset("UTF-8"))
                    val messageList = Gson().fromJson<List<Message>>(message, object : TypeToken<List<Message?>?>() {}.type)
                }
            }

            mChannel!!.basicConsume(queueName, true, consumer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dispose() {
        running = false
        try {
            if (mConnection != null) mConnection!!.close()
            if (mChannel != null) mChannel!!.abort()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
