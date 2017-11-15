package ua.jug.tw

import twitter4j.Query
import twitter4j.Twitter
import twitter4j.TwitterStream
import twitter4j.util.function.Consumer
import ua.jug.kotlindemo.TwitterBot
import ua.jug.buffer.BoundedBuffer
import ua.jug.buffer.Buffer

class TwitterWall(bufUpdFun: (Buffer<String>, List<String>) -> Unit, val hashtags: List<String>, val size: Int = 10) {

    private val buffer: Buffer<String> = BoundedBuffer(size)

    init {
        val filteredHashtags = hashtags.filter { !it.isBlank() }
        if(filteredHashtags.isEmpty()) throw IllegalArgumentException("Empty list")

        bufUpdFun(buffer, hashtags)
    }


    fun fillBuffer(fillFun: (List<String>) -> List<String>) {
       fillFun(hashtags).take(size).forEach { buffer.put(it) }
    }

    fun tweets(): List<String> = buffer.buffer().reversed()

}