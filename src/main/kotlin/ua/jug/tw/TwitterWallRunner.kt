package ua.jug.tw

import io.javalin.Javalin
import twitter4j.*
import twitter4j.util.function.Consumer
import ua.jug.buffer.Buffer
import ua.jug.kotlindemo.TwitterBot


fun main(args: Array<String>) {
    val app = Javalin.start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }

    val tags = listOf("#JavaDayUA", "#JUGUA", "#JavaDayUA2017")

    val twitterWall = TwitterWall(
            twitterMessagesBufferUpdater(TwitterStreamFactory().instance),
            tags
    )
    twitterWall.fillBuffer( searchTweets(TwitterFactory().instance) )

    app.get("/tw") { ctx -> ctx.json(twitterWall.tweets()) }
}

fun twitterMessagesBufferUpdater(twitterStream: TwitterStream) = { buffer: Buffer<String>, hashtags: List<String> ->

    TwitterBot(twitterStream,
            Consumer { status -> buffer.put(status.text) }
    ).subscribe(* hashtags.toTypedArray())

}

fun searchTweets(twitter: Twitter) = { hashtags: List<String> ->

    val query = Query(hashtags[0])
    val result = twitter.search(query)
    result.tweets
            .map { status -> status.text }

}
