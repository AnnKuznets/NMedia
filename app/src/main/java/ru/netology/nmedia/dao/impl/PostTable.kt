package ru.netology.nmedia.dao.impl

object PostTable {

    const val NAME = "posts"

    val DDL = """
        
        CREATE TABLE $NAME (
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
        ${Column.CONTENT.columnName} TEXT NOT NULL,
        ${Column.PUBLISHED.columnName} TEXT NOT NULL,
        ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
        ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.SHARES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.SHARED.columnName} BOOLEAN NOT NULL DEFAULT 0
        );
        """.trimIndent()

    val ALL_COLUMNS = Column.values().map(Column::columnName).toTypedArray()

    enum class Column(val columnName:String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKED_BY_ME("likedByMe"),
        LIKES("likes"),
        SHARES("shares"),
        SHARED("shared")
    }
}