package ru.netology.nmedia


data class Post(
    val id: Long = 0,
    val author: String,
    val content: String,
    val published: String,
    var like: Int = 0,
    var shares: Int = 0,
    var likedByMe: Boolean = false,
    val shared: Boolean = false,
    val video: String? = null
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


