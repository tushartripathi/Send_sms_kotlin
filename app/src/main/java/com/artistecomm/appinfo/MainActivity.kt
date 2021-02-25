package com.artistecomm.appinfo

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.TrafficStats
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    var MY_PERMISSION_READ_CONTACTS= 1
    lateinit var numberBox: EditText
    lateinit var MessageBody: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          numberBox = findViewById(R.id.editPhoneNumberBox)
        MessageBody = findViewById(R.id.editMessageBodyBox)
        getPermission()


        }

        fun sendMessage(view: View?) {
            var number:String = numberBox.getText().toString()
            var message :String= MessageBody.getText().toString()
            if(number != null && message !=null )
                run {
                    var smsManager: SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(number,null,message,null,null)
                    Toast.makeText(this,"Sent",Toast.LENGTH_SHORT).show()

                }

        }

        fun getPermission(){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
            {
                //If NOt
                //Tell user why it is important
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS))
                {
                    var alert: AlertDialog.Builder = AlertDialog.Builder(this)
                    alert.setTitle("Request Permission")
                    alert.setMessage("You should enable this perimissin for")
                    alert.setNegativeButton("No", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.SEND_SMS), MY_PERMISSION_READ_CONTACTS)
                        }})
                    alert.setPositiveButton("Yes", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.SEND_SMS), MY_PERMISSION_READ_CONTACTS)
                        }})
                    alert.show()
                }
                else
                    ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.SEND_SMS), MY_PERMISSION_READ_CONTACTS)
            }
            else
            {
                //Permission is already granted

            }
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSION_READ_CONTACTS->
                if(grantResults.size > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission is given", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this,"Permission is not granted", Toast.LENGTH_SHORT).show()
        }
    }
}