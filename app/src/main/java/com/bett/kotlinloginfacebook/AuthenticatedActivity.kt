package com.matt.trikimedia
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import bolts.Task.delay
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.matt.trikimedia.AuthenticatedActivity.Companion.globalVar
import com.matt.trikimedia.MainActivity.Companion.globaltok
import kotlinx.android.synthetic.main.authenticated_activity.view.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
class AuthenticatedActivity : AppCompatActivity() {
    companion object {
        var globalVar = "not got yet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        GlobalScope.launch(Dispatchers.IO) {
            sendGet()

        }
        Thread.sleep(3_000)
        var randomInt = globalVar



        super.onCreate(savedInstanceState)
        setContentView(R.layout.authenticated_activity)
        val resultText: TextView = findViewById(R.id.result_text)
        resultText.text = randomInt

        var btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener(View.OnClickListener {
            // LogMainActivityout
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()

                    finish()
                }).executeAsync()
            }
        })

    }
    suspend fun sendGet() {
        var buff = " "

        val url = URL("https://graph.facebook.com/2776682369322266?fields=name,id&access_token="+globaltok+"}")
        println(url)
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    println(line)
                    buff = buff.plus(line)
                    var rando = buff
                    globalVar = rando
                }
            }
        }
    }

}
