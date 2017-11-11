package ua.jug.tw

import io.javalin.Javalin
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory


fun main(args: Array<String>) {
    val app = Javalin.start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }

    val tags = listOf("#JavaDayUA", "#JUGUA", "#JavaDayUA2017")

    val twitterWall = TwitterWall(TwitterStreamFactory().instance, tags)
    twitterWall.fillBuffer { twitterWall.searchTweets(TwitterFactory().instance) }

    app.get("/tw") { ctx -> ctx.json(twitterWall.tweets()) }
}
