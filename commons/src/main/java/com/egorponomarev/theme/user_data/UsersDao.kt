package com.egorponomarev.theme.user_data

import androidx.room.Dao
import androidx.room.Query

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
@Dao
interface UsersDao {

    @Query("SELECT * FROM ")
    fun userById(id: Int) : UserData
}