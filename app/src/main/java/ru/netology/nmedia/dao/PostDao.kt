package ru.netology.nmedia.dao

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.impl.PostTable
import ru.netology.nmedia.entity.PostEntity

    @Dao
    interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun editContent(id: Long, content: String)

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else editContent(post.id, post.content)

    @Query("""
            UPDATE PostEntity SET
                likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id = :id;
                """)
    fun likedById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)
}