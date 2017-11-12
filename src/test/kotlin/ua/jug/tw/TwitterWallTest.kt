package ua.jug.tw

import io.kotlintest.matchers.gt
import io.kotlintest.matchers.lte
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.FunSpec
import ua.jug.buffer.Buffer

class TwitterWallTest : FunSpec() {

    init {

        val twitter = { _: Buffer<String>, _: List<String> -> Unit }
        fun twitterWall() = TwitterWall( twitter, listOf("twitterMessagesBufferUpdater"))

        test("Create TW") {
                twitterWall()
            }

            test("List of tweets") {
                val twitterWall: TwitterWall = twitterWall()
                val tweets: List<String> = twitterWall.tweets()
            }

            test("Sized buffer") {
                TwitterWall(twitter, listOf("a"), 10)
            }

            test("Subscribe on hashtags") {
                val hashtags = listOf("#JavaDayUA", "#JUGUA")
                TwitterWall(twitter, hashtags = hashtags, size = 20)
            }

            test("hashtags should not be empty") {
                val hashtags = listOf<String>()
                shouldThrow<IllegalArgumentException> {
                    TwitterWall(twitter, hashtags = hashtags)
                }.message shouldBe "Empty list"
            }

            test("hashtags should not be blank") {
                val hashtags = listOf<String>(" ", "")
                shouldThrow<IllegalArgumentException> {
                    TwitterWall(twitter, hashtags = hashtags)
                }.message shouldBe "Empty list"
            }

            test("prefill buffer") {
                val twitterWall = TwitterWall(twitter, listOf("#JavaDayUA2017"), 2)

                twitterWall.fillBuffer{ listOf("a #JavaDayUA2017", "#JavaDayUA2017 b", "#JavaDayUA2017 c") }

                val size = twitterWall.tweets().size
                size shouldBe gt(0)
                size shouldBe lte(2)
                twitterWall.tweets().filter { it.contains("#JavaDayUA2017") }.size shouldBe size
            }

        }
   }


