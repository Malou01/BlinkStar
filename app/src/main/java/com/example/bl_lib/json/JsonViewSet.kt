package com.example.bl_lib.json

import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonViewSet {
    companion object {

        fun jsonLineIsChecker(message: String?, key: String?, count: Int): Boolean {
            return try {
                if (JSONObject(message!!).has("bird")) { JSONObject(message).getJSONObject("bird").getJSONObject(key!!).has(count.toString())
                } else false
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun jsonLineSet(message: String?, key: String?, count: Int, editTextName1: EditText, editTextName2: EditText, editTextName3: EditText, editTextName4: EditText, editTextName5: EditText, checkBox: CheckBox) {
            try {
                val jsonArray = JSONObject(message!!).getJSONObject("bird").getJSONObject(key!!)[(count + 1).toString()] as JSONArray
                editTextName1.setText(jsonArray.getInt(0).toString())
                editTextName2.setText(jsonArray.getInt(1).toString())
                editTextName3.setText(jsonArray.getDouble(2).toString())
                editTextName4.setText(jsonArray.getInt(3).toString())
                editTextName5.setText(jsonArray.getDouble(4).toString())
                checkBox.isChecked = jsonArray.getBoolean(5)
            } catch (e: JSONException) {
                throw java.lang.RuntimeException(e)
            }
        }

        fun jsonLineSet(message: String?, key: String?, count: Int, editTextName1: EditText, editTextName2: EditText, editTextName3: EditText, editTextDuration: EditText, spinner: Spinner) {
            try {
                val jsonArray = JSONObject(message!!).getJSONObject("bird").getJSONObject(key!!)[(count + 1).toString()] as JSONArray
                editTextName1.setText(jsonArray.getInt(0).toString())
                editTextName2.setText(jsonArray.getInt(1).toString())
                editTextName3.setText(jsonArray.getString(2))
                editTextDuration.setText(JSONObject(message).getJSONObject("bird").getJSONObject(key).getInt("duration").toString())
                if (JSONObject(message).getJSONObject("bird").getJSONObject(key).getString("type") == "Reverse") {
                    spinner.setSelection(0)
                } else spinner.setSelection(1)
            } catch (e: JSONException) {
                throw java.lang.RuntimeException(e)
            }
        }

        fun jsonLineSet(message: String?, key: String?, count: Int, editTextName1: EditText, editTextName2: EditText, editTextName3: EditText, editTextName4: EditText) {
            try {
                val jsonArray = JSONObject(message!!).getJSONObject("bird").getJSONObject(key!!)[(count + 1).toString()] as JSONArray
                editTextName1.setText(jsonArray.getInt(0).toString())
                editTextName2.setText(jsonArray.getInt(1).toString())
                editTextName3.setText(jsonArray.getString(2))
                editTextName4.setText(jsonArray.getString(3))
            } catch (e: JSONException) {
                throw java.lang.RuntimeException(e)
            }
        }

        fun jsonLineSet(message: String?, key: String?, count: Int, editTextName1: EditText, editTextName2: EditText, editTextName3: EditText, editTextName4: EditText, editTextName5: EditText) {
            try {
                val jsonArray = JSONObject(message!!).getJSONObject("bird").getJSONObject(key!!)[(count + 1).toString()] as JSONArray
                editTextName1.setText(jsonArray.getInt(0).toString())
                editTextName2.setText(jsonArray.getInt(1).toString())
                editTextName3.setText(jsonArray.getString(2))
                editTextName4.setText(jsonArray.getString(3))
                editTextName5.setText(jsonArray.getDouble(4).toString())
            } catch (e: JSONException) {
                throw java.lang.RuntimeException(e)
            }
        }
    }
}