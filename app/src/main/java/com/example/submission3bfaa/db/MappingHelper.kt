package com.example.submission3bfaa.db

import android.database.Cursor
import com.example.submission3bfaa.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.example.submission3bfaa.db.DatabaseContract.UserColumns.Companion.ID
import com.example.submission3bfaa.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.example.submission3bfaa.utils.User

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        userCursor?.apply {
            while (userCursor.moveToNext()) {
                val id = userCursor.getString(getColumnIndexOrThrow(ID))
                val username = userCursor.getString(getColumnIndexOrThrow(USERNAME))
                val avatar = userCursor.getString(getColumnIndexOrThrow(AVATAR_URL))
                userList.add(
                    User(
                        id = id,
                        userName = username,
                        avatarUrl = avatar
                    )
                )
            }
        }
        return userList
    }
}