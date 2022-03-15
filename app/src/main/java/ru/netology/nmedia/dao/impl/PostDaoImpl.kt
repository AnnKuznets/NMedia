package ru.netology.nmedia.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.dao.PostDao

class PostDaoImpl(private val db: SQLiteDatabase): PostDao {
    override fun getAll() = db.query(
        PostTable.NAME,
        PostTable.ALL_COLUMNS,
        null,
        null,
        null,
        null,
        "${PostTable.Column.ID.columnName} DESC"
    ).use { cursor ->
        List(cursor.count) {
            cursor.moveToNext()
            cursor.toModel()
        }
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostTable.Column.AUTHOR.columnName, (R.string.author_me))
            put(PostTable.Column.CONTENT.columnName, post.content)
            put(PostTable.Column.PUBLISHED.columnName, (R.string.published))
        }
        val id = if (post.id != 0L) {
            db.update(
                PostTable.NAME,
                values,
                "${PostTable.Column.ID.columnName} = ?",
                arrayOf(post.id.toString())
            )
            post.id
        } else {
            db.insert(PostTable.NAME,null,values)
        }
        db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(post.id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return it.toModel()
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostTable.NAME} SET
                ${PostTable.Column.LIKES.columnName} = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                ${PostTable.Column.LIKED_BY_ME.columnName} = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id = ?;
                """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostTable.NAME,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }

    override fun share(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostTable.NAME} SET
                ${PostTable.Column.SHARES.columnName} = shares + CASE WHEN shared 1 END,
                ${PostTable.Column.SHARED.columnName} = CASE WHEN shared 0 END
                WHERE id = ?;
                """.trimIndent(),
            arrayOf(id)
        )
    }

    private fun Cursor.toModel() = Post(
        id = getLong(getColumnIndexOrThrow(PostTable.Column.ID.columnName)),
        author = getString(getColumnIndexOrThrow(PostTable.Column.AUTHOR.columnName)),
        content = getString(getColumnIndexOrThrow(PostTable.Column.CONTENT.columnName)),
        published = getString(getColumnIndexOrThrow(PostTable.Column.PUBLISHED.columnName)),
        likedByMe = getInt(getColumnIndexOrThrow(PostTable.Column.LIKED_BY_ME.columnName)) !=0,
        like = getInt(getColumnIndexOrThrow(PostTable.Column.LIKES.columnName)),
        shares = getInt(getColumnIndexOrThrow(PostTable.Column.SHARES.columnName))
    )
}