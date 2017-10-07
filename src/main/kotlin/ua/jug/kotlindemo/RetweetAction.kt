package ua.jug.kotlindemo

import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.util.function.Consumer

class RetweetAction(val twitter: Twitter = TwitterFactory.getSingleton()) : Consumer<Status> {
    override fun accept(status: Status) {

        if(!(status.isRetweet or status.isRetweetedByMe)) {
            twitter.retweetStatus(status.id)
        }

    }
}