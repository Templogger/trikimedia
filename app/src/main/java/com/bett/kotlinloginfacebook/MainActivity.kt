package com.matt.trikimedia

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.matt.trikimedia.AuthenticatedActivity
import com.matt.trikimedia.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        var globaltok = "n"
    }
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnLoginFacebook = findViewById<Button>(R.id.btnLoginFacebook)

        btnLoginFacebook.setOnClickListener(View.OnClickListener {

            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                            startActivity(Intent(applicationContext, AuthenticatedActivity::class.java))
                            globaltok = loginResult.accessToken.token
                        }

                        override fun onCancel() {
                            Log.d("MainActivity", "Facebook onCancel.")

                        }

                        override fun onError(error: FacebookException) {
                            Log.d("MainActivity", "Facebook onError.")

                        }
                    })
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)

    }
}
