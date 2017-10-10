package ua.jug.kotlindemo

import twitter4j.Status
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.util.function.Consumer

class TwitterBot(val twitterStream: TwitterStream = TwitterStreamFactory.getSingleton(), val action: Consumer<Status>) {

    fun subscribe(vararg tags: String) {
        val filteredTags = tags.filter { it.isNotBlank() }
        if (filteredTags.isEmpty())
            throw IllegalArgumentException("Empty tags")

        twitterStream
                .onStatus(action)
                .filter(* filteredTags.toTypedArray())
    }
}