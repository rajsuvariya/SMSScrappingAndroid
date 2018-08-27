package com.rajsuvariya.smsscrapping

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Long
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkPermissions()){
            scrapSMS()
        } else {
            requestPermission()
        }

    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), 10092)
        }
    }

    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            10092 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    scrapSMS()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    requestPermission()
                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun scrapSMS() {
        val cr = this.contentResolver
        val c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null)
        var totalSMS = 0
        var smsList = arrayListOf<String>()
        if (c != null) {
            totalSMS = c!!.getCount()
            if (c!!.moveToFirst()) {
                for (j in 0 until totalSMS) {
                    val smsDate = c!!.getString(c!!.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    val number = c!!.getString(c!!.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    val body = c!!.getString(c!!.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    val dateFormat = Date(Long.valueOf(smsDate))
                    val type: String
                    when (Integer.parseInt(c!!.getString(c!!.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        Telephony.Sms.MESSAGE_TYPE_INBOX -> type = "inbox"
                        Telephony.Sms.MESSAGE_TYPE_SENT -> type = "sent"
                        Telephony.Sms.MESSAGE_TYPE_OUTBOX -> type = "outbox"
                        else -> {
                        }
                    }
                    smsList.add(body)
                    c!!.moveToNext()
                }
            }

            c!!.close()

        } else {
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show()
        }

        rv_recyclerview.adapter = SMSAdapter(smsList)
        rv_recyclerview.layoutManager = LinearLayoutManager(this)
        rv_recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
