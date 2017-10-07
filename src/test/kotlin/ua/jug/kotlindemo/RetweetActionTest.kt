package ua.jug.kotlindemo

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.kotlintest.specs.FunSpec
import org.mockito.ArgumentMatchers.anyLong
import twitter4j.Status
import twitter4j.Twitter


class RetweetActionTest : FunSpec() {
    init {
        test ("do not retweet reetweets") {
            val status: Status = mock{
                on {isRetweet} doReturn true
            }
            val twitter: Twitter = mock()
            val action = RetweetAction(twitter)

            action.accept(status)

            verify(twitter, never()).retweetStatus(anyLong())
        }

        test ("do retweet original status") {
            val status: Status = mock{
                on {isRetweet} doReturn false
            }
            val twitter: Twitter = mock()
            val action = RetweetAction(twitter)

            action.accept(status)

            verify(twitter).retweetStatus(anyLong())
        }

        test ("do retweet original status with tweet id") {
            val tweetId = 42L
            val status: Status = mock{
                on {isRetweet} doReturn false
                on {id} doReturn tweetId

            }
            val twitter: Twitter = mock()
            val action = RetweetAction(twitter)

            action.accept(status)

            verify(twitter).retweetStatus(tweetId)
        }

        test ("do retweet original status with tweet id") {
            val tweetId = 42L
            val status: Status = mock{
                on {isRetweet} doReturn false
                on {id} doReturn tweetId

            }
            val twitter: Twitter = mock()
            val action = RetweetAction(twitter)

            action.accept(status)

            verify(twitter).retweetStatus(tweetId)
        }

        test ("do not retweet status already retweeted by me") {
            val tweetId = 42L
            val status: Status = mock{
                on {isRetweet} doReturn false
                on {id} doReturn tweetId
                on {isRetweetedByMe} doReturn true

            }
            val twitter: Twitter = mock()
            val action = RetweetAction(twitter)

            action.accept(status)

            verify(twitter, never()).retweetStatus(tweetId)
        }
    }
}