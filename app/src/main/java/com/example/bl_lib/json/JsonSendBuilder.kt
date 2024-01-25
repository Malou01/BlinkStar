package com.example.bl_lib.json

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonSendBuilder {
    companion object {
        private var lineObjParam1 = JSONObject()
        private var lineObjParam2 = JSONObject()
        private var lineObjParam3 = JSONObject()
        private var lineObjParam4 = JSONObject()

        fun messageParamSum(day1: Int, day2: Int, temp: Double, hum: Int, tempEgg: Double, box: Boolean, line: Int) {
            try {
                val jsonArray = JSONArray()
                jsonArray.put(day1)
                jsonArray.put(day2)
                jsonArray.put(temp)
                jsonArray.put(hum)
                jsonArray.put(tempEgg)
                jsonArray.put(box)
                lineObjParam1.put(line.toString(), jsonArray)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageParamSum(day1: Int, day2: Int, timer: String?, line: Int) {
            try {
                val jsonArray = JSONArray()
                //jsonArray.put(item_motor);
                jsonArray.put(day1)
                jsonArray.put(day2)
                jsonArray.put(timer)
                //jsonArray.put(duration);
                lineObjParam2.put(line.toString(), jsonArray)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageParamSum(day1: Int, day2: Int, timer1: String?, timer2: Int, line: Int) {
            try {
                val jsonArray = JSONArray()
                jsonArray.put(day1)
                jsonArray.put(day2)
                jsonArray.put(timer1)
                jsonArray.put(timer2)
                lineObjParam3.put(line.toString(), jsonArray)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageParamSum(day1: Int, day2: Int, timer1: String?, timer2: Int, temp: Double, line: Int) {
            try {
                val jsonArray = JSONArray()
                jsonArray.put(day1)
                jsonArray.put(day2)
                jsonArray.put(timer1)
                jsonArray.put(timer2)
                jsonArray.put(temp)
                lineObjParam4.put(line.toString(), jsonArray)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageSetMotorState(state: String?) {
            try {
                lineObjParam2.put("type", state)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageSetMotorDuration(parseInt: Int) {
            try {
                lineObjParam2.put("duration", parseInt)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageObj(key1: String?, key2: String?, key3: String?, key4: String?): String? {
            val paramObj = JSONObject()
            try {
                paramObj.put(key1, lineObjParam1)
                paramObj.put(key2, lineObjParam2)
                paramObj.put(key3, lineObjParam3)
                paramObj.put(key4, lineObjParam4)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
            return try {
                JSONObject().put("bird", paramObj).toString()
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageObj(key: String?, state: Boolean): String? {
            val obj = JSONObject()
            return try {
                obj.put(key, state)
                obj.toString()
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun messageObj(key1: String?, key2: String?): String? {
            val obj = JSONObject()
            return try {
                obj.put(key1, key2)
                obj.toString()
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }

        fun clean() {
            while (lineObjParam1.length() > 0) lineObjParam1.remove(lineObjParam1.keys().next())
            while (lineObjParam2.length() > 0) lineObjParam2.remove(lineObjParam2.keys().next())
            while (lineObjParam3.length() > 0) lineObjParam3.remove(lineObjParam3.keys().next())
            while (lineObjParam4.length() > 0) lineObjParam4.remove(lineObjParam4.keys().next())
        }
    }
}