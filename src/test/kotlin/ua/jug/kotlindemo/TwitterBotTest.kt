package ua.jug.kotlindemo

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.FunSpec
import twitter4j.Status
import twitter4j.TwitterStream
import twitter4j.util.function.Consumer

class TwitterBotTest : FunSpec() {
    val stream: TwitterStream = mock {
        on {onStatus(any())} doReturn it
    }

    init {
        test("create TwitterBot") {
            TwitterBot(stream)
        }

        test("fail to subscribe for empty tags") {
            val bot = TwitterBot(stream)

            val exception = shouldThrow<IllegalArgumentException> {
                bot.subscribe()
            }
            exception.message shouldBe "Empty tags"
        }

        test("fail to subscribe for blank tags") {
            val bot = TwitterBot(stream)

            shouldThrow<IllegalArgumentException> {
                bot.subscribe("", " ", "      ")
            }.message shouldBe "Empty tags"

        }

        test("filter when subscribe for one tag") {
            val bot = TwitterBot(stream)

            bot.subscribe("#tag")
            verify(stream).filter("#tag")
        }

        test("subscribe for one tag") {
            val action = Consumer<Status> { println("I'm consumer!") }
            val bot = TwitterBot(stream, action)

            bot.subscribe("#tag")
            verify(stream).filter("#tag")
            verify(stream).onStatus(action)
        }

    }
}