package com.gmail.wondergab12.bank.model

import org.json.JSONArray
import org.json.JSONException

class JsonParse {

    companion object {
        fun getAtms(response: String): List<Atm> {
            val atms = mutableListOf<Atm>()
            val builder = Atm.Builder()

            try {
                val jsonArray = JSONArray(response)
                var i = 0
                while (i < jsonArray.length()) {
                    builder.setId(jsonArray
                            .getJSONObject(i)
                            .getString("id"))

                    builder.setArea(jsonArray
                            .getJSONObject(i)
                            .getString("area"))

                    builder.setCityType(jsonArray
                            .getJSONObject(i)
                            .getString("city_type"))

                    builder.setCity(jsonArray
                            .getJSONObject(i)
                            .getString("city"))

                    builder.setAddressType(jsonArray
                            .getJSONObject(i)
                            .getString("address_type"))

                    builder.setAddress(jsonArray
                            .getJSONObject(i)
                            .getString("address"))

                    builder.setHouse(jsonArray
                            .getJSONObject(i)
                            .getString("house"))

                    builder.setInstallPlace(jsonArray
                            .getJSONObject(i)
                            .getString("install_place"))

                    builder.setWorkTime(jsonArray
                            .getJSONObject(i)
                            .getString("work_time"))

                    builder.setGpsX(jsonArray
                            .getJSONObject(i)
                            .getString("gps_x"))

                    builder.setGpsY(jsonArray
                            .getJSONObject(i)
                            .getString("gps_y"))

                    builder.setInstallPlaceFull(jsonArray
                            .getJSONObject(i)
                            .getString("install_place_full"))

                    builder.setWorkTimeFull(jsonArray
                            .getJSONObject(i)
                            .getString("work_time_full"))

                    builder.setAtmType(jsonArray
                            .getJSONObject(i)
                            .getString("ATM_type"))

                    builder.setAtmError(jsonArray
                            .getJSONObject(i)
                            .getString("ATM_error"))

                    builder.setCurrency(jsonArray
                            .getJSONObject(i)
                            .getString("currency"))

                    builder.setCashIn(jsonArray
                            .getJSONObject(i)
                            .getString("cash_in"))

                    builder.setAtmPrinter(jsonArray
                            .getJSONObject(i)
                            .getString("ATM_printer"))

                    atms.add(builder.build())
                    i++
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return atms
        }
    }

}
