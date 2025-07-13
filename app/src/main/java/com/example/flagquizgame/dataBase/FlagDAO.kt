package com.example.flagquizgame.dataBase

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper

class FlagDAO {
    fun generate_ten_randmon(helper : DatabaseCopyHelper) : ArrayList<FlagModel> {

        val recordList = ArrayList<FlagModel>()


            val dataBase : SQLiteDatabase = helper.readableDatabase
            val cursor = dataBase.rawQuery("SELECT * FROM flags ORDER BY RANDOM() LIMIT 10 ",null)




            val idIndex = cursor.getColumnIndex("flags_id")
            val countryNameIndex = cursor.getColumnIndex("country_name")
            val flagNameIndex = cursor.getColumnIndex("flag_name")


            var record : FlagModel

            while (cursor.moveToNext() ){
                val id = cursor.getInt(idIndex)
                val countryName = cursor.getString(countryNameIndex)
                val flagName = cursor.getString(flagNameIndex)


                // Fixed parameter order: flag_id, flag_name, country_name
                record = FlagModel(id, flagName, countryName)
                recordList.add(record)
            }
            cursor.close()


        return recordList
    }
    fun generate_three_randmon(helper : DatabaseCopyHelper , id : Int) : ArrayList<FlagModel> {

        val recordList = ArrayList<FlagModel>()


        val dataBase : SQLiteDatabase = helper.readableDatabase
        val cursor = dataBase.rawQuery("SELECT * FROM flags WHERE flags_id != $id ORDER BY RANDOM() LIMIT 3 ",null)




        val idIndex = cursor.getColumnIndex("flags_id")
        val countryNameIndex = cursor.getColumnIndex("country_name")
        val flagNameIndex = cursor.getColumnIndex("flag_name")


        var record : FlagModel

        while (cursor.moveToNext() ){
            val id = cursor.getInt(idIndex)
            val countryName = cursor.getString(countryNameIndex)
            val flagName = cursor.getString(flagNameIndex)


            // Fixed parameter order: flag_id, flag_name, country_name
            record = FlagModel(id, flagName, countryName)
            recordList.add(record)
        }
        cursor.close()


        return recordList
    }
}