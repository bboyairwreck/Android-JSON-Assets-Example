package com.ericchee.jsonfileexample0

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName

        const val JSON_FILE_NAME = "myCoolJson.json"
        const val EMPLOYEES_KEY = "employees"
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val jsonString: String? = try {
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

            val jsonObject = JSONObject(jsonString)
            val employeesJSONArray = jsonObject.getJSONArray(EMPLOYEES_KEY)

            for (i in 0 until employeesJSONArray.length()) {
                val employeeJSONObject = employeesJSONArray.get(i) as JSONObject
                val firstName = employeeJSONObject.get(FIRST_NAME)
                val lastName = employeeJSONObject.get(LAST_NAME)

                Log.i(TAG, "Hello my name is $firstName $lastName")
            }
        }

    }
}
