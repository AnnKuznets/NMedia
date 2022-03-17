package ru.netology.nmedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dao.impl.PostDaoImpl
import ru.netology.nmedia.dao.impl.PostTable

class AppDb private constructor(context: Context, db: SQLiteDatabase) {
    val postDao: PostDao = PostDaoImpl(db,context)

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(context,
                    buildDatabase(context, arrayOf(PostTable.DDL))
                ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs
        ).writableDatabase
    }
}
