package com.ericchee.jsonfileexample0

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences


    companion object {
        val TAG: String = MainActivity::class.java.simpleName

        const val JSON_FILE_NAME = "myCoolJson.json"
        const val EMPLOYEES_KEY = "employees"
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"

        private const val USER_PREF_KEY = "USER_PREFERENCES_KEY"
        private const val TIMESTAMP_KEY = "timestamp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readWriteJson()
        readJson()
    }

    fun readWriteJson() {
        sharedPreferences = getSharedPreferences(USER_PREF_KEY, Context.MODE_PRIVATE)

        // Get current time stamp from SharedPreferences & print it
        val defaultErrorValue = -1L
        val timestamp = sharedPreferences.getLong("timestamp", defaultErrorValue)
        Log.i(TAG, "Current Shared preferences of time stamp = $timestamp")

        // Updated timestamp in SharedPreferences & print it
        sharedPreferences
            .edit()
            .putLong(TIMESTAMP_KEY, System.currentTimeMillis() + 1000)
            .apply()
        Log.i(TAG, "New shared preferences of time stamp = ${sharedPreferences.getLong("timestamp", defaultErrorValue)}")
    }

    fun readJson() {
        // Note: to create assets folder to put files in it: https://stackoverflow.com/a/27673773/1991683

        val jsonString: String? = try {
            // grab file from assets folder & read it to a String
            val inputStream = assets.open(JSON_FILE_NAME)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            null
        }

        jsonString?.let {

            Log.i(TAG, jsonString)

            // Create json from string
            val jsonObject = JSONObject(jsonString)

            // Get JSON array
            val employeesJSONArray = jsonObject.getJSONArray(EMPLOYEES_KEY)

            // Read JSON array
            for (i in 0 until employeesJSONArray.length()) {
                // get data of array value at index
                val employeeJSONObject = employeesJSONArray.get(i) as JSONObject

                // Get data value of key
                val firstName = employeeJSONObject.get(FIRST_NAME)
                val lastName = employeeJSONObject.get(LAST_NAME)

                Log.i(TAG, "Hello my name is $firstName $lastName")
            }
        }
    }
}
