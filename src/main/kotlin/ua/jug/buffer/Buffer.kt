package ua.jug.buffer

interface Buffer<T> {
    fun buffer() : List<T>
    fun put(elem: T)
}