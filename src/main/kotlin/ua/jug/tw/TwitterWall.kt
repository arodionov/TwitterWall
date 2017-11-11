package ua.jug.tw

import twitter4j.Query
import twitter4j.Twitter
import twitter4j.TwitterStream
import twitter4j.util.function.Consumer
import ua.jug.kotlindemo.TwitterBot
import ua.jug.buffer.BoundedBuffer
import ua.jug.buffer.Buffer

class TwitterWall(twitterStream: TwitterStream, val hashtags: List<String>, val size: Int = 10) {

    private val buffer: Buffer<String> = BoundedBuffer(size)

    init {
        val filteredHashtags = hashtags.filter { !it.isBlank() }
        if(filteredHashtags.isEmpty()) throw IllegalArgumentException("Empty list")

        TwitterBot(twitterStream,
                Consumer { status ->  buffer.put(status.text) }
        ).subscribe(* hashtags.toTypedArray())
    }

    fun fillBuffer(fillFun: () -> List<String>) {
       fillFun().take(size).forEach { buffer.put(it) }
    }

    internal fun searchTweets(twitter: Twitter): List<String> {
        val query = Query(hashtags[0])
        val result = twitter.search(query)
        return result.tweets
                .map { status -> status.text }
    }

    fun tweets(): List<String> = buffer.buffer().reversed()

}