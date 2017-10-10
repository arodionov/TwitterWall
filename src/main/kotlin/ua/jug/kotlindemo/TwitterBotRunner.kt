package ua.jug.kotlindemo

import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory

fun main(args: Array<String>) {
    TwitterBot(TwitterStreamFactory().instance,
            RetweetAction(TwitterFactory().instance, listOf("ugly", "no soup", "bad", "yegor256")))
            .subscribe("#JUGUA", "#JavaDayUA", "#Kotlin")
}
