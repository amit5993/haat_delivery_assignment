package com.interview.assignment.model.mqtt

interface RabbitMQCallBack {
    fun onMQConnectionFailure(message: String?)

    fun onMQDisconnected()

    fun onMQConnectionClosed(message: String?)

    fun onMessageReceived(message: String?)
}
