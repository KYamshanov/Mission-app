package ru.kyamshanov.mission.project_view.impl.utils

internal fun <T> List<T>.findIndexed(predicate: (T) -> Boolean): (Pair<Int, T>)? {
    forEachIndexed { index, t -> if (predicate(t)) return index to t }
    return null
}

internal fun <T> MutableList<T>.getAndRemoveFirst(predicate: (T) -> Boolean): T? {
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        val v = iterator.next()
        if (predicate(v)) {
            iterator.remove()
            return v
        }
    }
    return null
}