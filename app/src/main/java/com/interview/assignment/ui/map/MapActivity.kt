package com.interview.assignment.ui.map

import android.util.Log
import com.application.base.AppBaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.interview.assignment.BuildConfig
import com.interview.assignment.R
import com.interview.assignment.databinding.ActivityMapBinding
import com.interview.assignment.model.mqtt.MQTTManager
import com.interview.assignment.model.mqtt.RabbitMQCallBack
import com.interview.assignment.model.mqtt.RabbitMQClient
import com.interview.assignment.util.toast

class MapActivity :
    AppBaseActivity<ActivityMapBinding, MapActivityViewModel>(), OnMapReadyCallback,
    RabbitMQCallBack {

    override fun setViewBinding() = ActivityMapBinding.inflate(layoutInflater)

    override fun setViewModel() = MapActivityViewModel.newInstance(this)


    private lateinit var mMap: GoogleMap

    private lateinit var mqttManager: MQTTManager
    var managerRabbitMQ: RabbitMQClient? = null

    override fun initView() {

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getMqttDetails {
            /**
             * I noticed one thing here is that somehow MQTT connection is revoked once connected.
             * So it was frequency connected and disconnected.
             * */

//            managerRabbitMQ = RabbitMQClient(it, this)

            val serverUri = "${it.protocol}://${it.host}:${it.port}"

            mqttManager = MQTTManager(this, serverUri, it.clientId!!)

            mqttManager.connect(it.userName!!, it.password!!) {
                mqttManager.subscribe(it.topic!!, it.qos!!) { message ->
                    Log.d("MainActivity", "Received message: $message")
                }
            }

//            Handler(Looper.getMainLooper()).postDelayed({
//                mqttManager.publish(it.topic!!, "Hello MQTT!")
//            }, 3000)


//            mqttManager = MQTTManager(this, serverUri, it.clientId!!)
//            mqttManager.connect(username = it.userName, password = it.password) {
//                // Once connected, subscribe to a topic
//                mqttManager.subscribe(it.topic!!, onMessageReceived = { message ->
//                    Log.d("OrderDetailsActivity", "Received message: $message")
//                })
//            }
//            // Publish a message after 3 seconds
//            Handler(Looper.getMainLooper()).postDelayed({
//                mqttManager.publish(it.topic!!, "Hello MQTT!")
//            }, 3000)


//            val serverUri = "ssl://drivers-broker.haatapis.delivery:8883"
//            val serverUri = "${it.protocol}://${it.host}:${it.port}"
//            val clientId = it.clientId!!
//            mqttClient = MQTTClient(this, serverUri, clientId)
//
//            // Connecting to MQTT broker using coroutines
//            CoroutineScope(Dispatchers.Main).launch {
//                val isConnected = mqttClient.connect(it.userName, it.password)
//                if (isConnected) {
//                    Log.d("MQTT", "Connected successfully")
//                    mqttClient.subscribe(it.topic!!)
//                } else {
//                    Log.d("MQTT", "Connection failed")
//                }
//            }


        }


    }

    override fun initOnClick() {

    }


    override fun appOnBackPressed() {
        finish()
    }

    override fun onDestroy() {
        mqttManager.disconnect()
        super.onDestroy()

//        val stopServiceIntent = Intent(this, MqttService::class.java)
//        stopService(stopServiceIntent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // When the map is ready, assign it to the GoogleMap variable
        mMap = googleMap

        // Add a marker to your desired location and move the camera
        val location = LatLng(37.7749, -122.4194) // San Francisco, for example
        mMap.addMarker(MarkerOptions().position(location).title("Marker in San Francisco"))

        // Move and zoom the camera to the marker location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }


    override fun onMQConnectionFailure(message: String?) {
        runOnUiThread {
            //if (BuildConfig.DEBUG) mContext.toast("failure : $message")
        }
    }

    override fun onMQDisconnected() {
        runOnUiThread {
            //if (BuildConfig.DEBUG) mContext.toast("disconnected")
        }
    }

    override fun onMQConnectionClosed(message: String?) {
        runOnUiThread {
            if (BuildConfig.DEBUG) toast("closed : $message")
        }
    }

    override fun onMessageReceived(message: String?) {
        runOnUiThread {
            // lblAnswer.setText(lblAnswer.getText().toString() + "\n" + R.id.message)
            // scrollView.post(Runnable { scrollView.fullScroll(View.FOCUS_DOWN) })
        }
    }
}