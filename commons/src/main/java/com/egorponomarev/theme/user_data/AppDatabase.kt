package com.egorponomarev.theme.user_data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
@Database([UserData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UsersDao
}