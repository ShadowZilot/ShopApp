package com.egorponomarev.theme.user_data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
@Entity(tableName = "users")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    private val mId: Int,
    @ColumnInfo("firstName")
    private val mFirstName: String,
    @ColumnInfo("lastName")
    private val mLastName: String,
    @ColumnInfo("email")
    private val mEmail: String,
    @ColumnInfo("photo")
    private val mPhoto: Uri
) {

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mFirstName,
        mLastName,
        mEmail,
        mPhoto,
        mId
    )

    interface Mapper<T> {
        fun map(
            firstName: String,
            lastName: String,
            email: String,
            photo: Uri,
            id: Int
        ) : T
    }
}