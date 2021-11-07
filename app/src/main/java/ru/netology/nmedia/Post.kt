package ru.netology.nmedia

import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong

data class Post (
    val id: Int = 0,
    val author: String,
    val content: String,
    val published: String,
    val like: Int = 0,
    var share: Int = 0,
    var view: Int = 0,
    val likedByMe: Boolean = false,
    val shared: Boolean = false
)

abstract class Elements() {
    operator fun compareTo(element: Int): Int {
        return element
    }

    operator fun rem(element: Int): Int {
        return element
    }

    operator fun div(element: Int): Int {
        return element
    }
}

abstract class Service <E: Elements> {
    private var elements = mutableListOf<E>()

    fun calculate (element: E) {
        when {
            element >= 1_000 || element < 10_000 || element % 100 in 0..99 -> print("%.0f".format(element / 1_000) +"K")
            element >= 1_000 || element < 10_000 || element % 1_000 in 1..999 -> print(
                "%.1f".format(
                    element / 1_000
                ) + "K"
            )
            element >= 10_000 || element <= 999_999 -> print("%.0f".format(element / 1_000) + "K")
            element >= 1_000_000 || element < 1_100_000 -> print("%.0f".format(element / 1_000_000) + "M")
            element >= 1_100_000 -> print("%.1f".format(element / 1_000_000) + "M")
        }
    }
}
class Like : Elements()
class LikeService : Service<Like>() {
    val like = mutableListOf<Like>()
    }
class Share : Elements()
class ShareService : Service<Share>() {
    val share = mutableListOf<Share>()
}
class View : Elements()
class ViewService : Service<View>() {
    val view = mutableListOf<View>()
}

