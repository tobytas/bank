package com.gmail.wondergab12.bank.repository.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.lang.IllegalStateException
import java.util.*

const val TABLE_NAME = "atms_table"
const val COLUMN_ID = "id"
const val COLUMN_DATE = "date"
const val COLUMN_CITY = "city"
const val COLUMN_CURRENCY = "currency"
const val COLUMN_RESPONSE = "response"

const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
        "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "$COLUMN_DATE, INTEGER, " +
        "$COLUMN_CITY, TEXT, " +
        "$COLUMN_CURRENCY, TEXT, " +
        "$COLUMN_RESPONSE, TEXT, " +
        "UNIQUE ($COLUMN_CITY, $COLUMN_CURRENCY)"

const val SQL_DROP_TABLE = "DROP TABLE $TABLE_NAME"

class AtmsDao(private val db: SQLiteDatabase) : DatabaseDao {

    override fun createRecord(city: String?, currency: String?, response: String?): Long {
        val contentValues = ContentValues()
        val date = Calendar.getInstance().timeInMillis
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_CITY, city)
        contentValues.put(COLUMN_CURRENCY, currency)
        contentValues.put(COLUMN_RESPONSE, response)

        return db.insert(TABLE_NAME, null, contentValues)
    }

    override fun readRecord(city: String?, currency: String?): Optional<DatabaseResponse> {
        var response = Optional.empty<DatabaseResponse>()

        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_DATE, COLUMN_RESPONSE),
                "$COLUMN_CITY = ? AND $COLUMN_CURRENCY = ?",
                arrayOf(city ?: throw WrongDatabaseParameter(),
                        currency ?: throw WrongDatabaseParameter()), null, null, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val indexDate = cursor.getColumnIndex(COLUMN_DATE)
                val indexResponse = cursor.getColumnIndex(COLUMN_RESPONSE)
                response = Optional.of(DatabaseResponse(cursor.getLong(indexDate),
                        cursor.getString(indexResponse)))
            }
            cursor.close()
        }

        return response
    }

}

class WrongDatabaseParameter :
        IllegalStateException("Null parameter in database request!")