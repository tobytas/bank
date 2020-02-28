package com.gmail.wondergab12.bank.repository.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "db"
const val DATABASE_VERSION = 2

class BankOpenHelpe private constructor(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var instance: BankOpenHelpe? = null

    fun getInstance(context: Context): BankOpenHelpe {
        if (instance == null) {
            instance = BankOpenHelpe(context)
        }

        return instance as BankOpenHelpe
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun databaseDao(): DatabaseDao {
        return AtmsDao(writableDatabase)
    }

}