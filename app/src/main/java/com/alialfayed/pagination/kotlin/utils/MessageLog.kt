package com.alialfayed.pagination.kotlin.utils

import android.util.Log

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 12/29/2020 - 4:36 PM
 */
class MessageLog {

    companion object{

        /**
         * Set Log
         *
         * @param tag TAG of logcat
         * @param msg Message of Logcat
         */
        fun setLogCat(tag: String, msg: String?) {
            Log.i("$tag :", msg!!)
        }
    }
}