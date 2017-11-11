package ua.jug.buffer

class BoundedBuffer<T>(val size: Int, elems: List<T> = listOf()) : Buffer<T> {

    private val buffer: MutableList<T>

    init {
        if(size <= 0) throw IllegalArgumentException("Negative size")
        buffer = elems.take(size).toMutableList()
    }

    override fun buffer() = buffer.toList()

    override fun put(elem: T) {
        if(buffer.size == size) {
            buffer.removeAt(0)
        }
        buffer.add(elem)
    }
}