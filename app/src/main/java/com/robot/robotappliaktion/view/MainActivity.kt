package com.robot.robotappliaktion.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.robot.robotappliaktion.R
import com.robot.robotappliaktion.viewmodel.QiContextViewModel

class MainActivity : AppCompatActivity(), RobotLifecycleCallbacks {

    private lateinit var navController: NavController
    private var qiContext: QiContext? = null
    private var qiContextViewModel: QiContextViewModel? = null

    private var sayFuture: Future<*>? = null
    private var animateFuture: Future<*>? = null
    private var chatFuture: Future<*>? = null


    // TODO: Use this function in your fragments.
    fun getQiContext(): QiContext? {
        return qiContext
    }

    fun setChatFuture(future: Future<*>) {
        chatFuture = future
    }

    fun getChatFuture(): Future<*>? {
        return chatFuture
    }

    fun setSayFuture(future: Future<*>) {
        sayFuture = future
    }

    fun getSayFuture(): Future<*>? {
        return sayFuture
    }

    fun setAnimateFuture(future: Future<*>) {
        animateFuture = future
    }

    fun getAnimateFuture(): Future<*>? {
        return animateFuture
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        qiContextViewModel = ViewModelProviders.of(this).get(QiContextViewModel::class.java)

        navController = Navigation.findNavController(this, R.id.fragmentMain)
        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?) {
        Log.i(TAG, "onRobotFocusGained")

        this.qiContext = qiContext

        qiContextViewModel?.setQiContextObserver(true)
    }

    override fun onRobotFocusLost() {
        Log.i(TAG, "onRobotFocusLost")

        this.qiContext = null
    }

    override fun onRobotFocusRefused(reason: String?) {
        Log.e(TAG, "onRobotFocusRefused: $reason")
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)

        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}