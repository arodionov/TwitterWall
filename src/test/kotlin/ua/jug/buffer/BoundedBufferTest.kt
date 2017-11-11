package ua.jug.buffer

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.FunSpec

class BoundedBufferTest : FunSpec() {
    init {

        test("Creation") {
            BoundedBuffer<String>(10)
        }

        test("Creation negative should fail") {
            shouldThrow<IllegalArgumentException> {
                BoundedBuffer<String>(-10)
            }.message shouldBe "Negative size"
        }

        test("one element buffer") {
            val boundedBuffer = BoundedBuffer<String>(1)
            boundedBuffer.put("a")
            boundedBuffer.buffer() shouldBe listOf("a")
        }

        test("one element buffer should override old value") {
            val boundedBuffer = BoundedBuffer<String>(1)
            boundedBuffer.put("a")
            boundedBuffer.put("b")
            boundedBuffer.buffer() shouldBe listOf("b")
        }


        test("two element buffer should override old value") {
            val boundedBuffer = BoundedBuffer<String>(2)
            boundedBuffer.put("a")
            boundedBuffer.put("b")
            boundedBuffer.put("c")
            boundedBuffer.buffer() shouldBe listOf("b", "c")
        }

        test("Create" ) {
            BoundedBuffer<String>(2, listOf<String>())
        }

        test("Create and fill" ) {
            val preFilledBuffer = BoundedBuffer<String>(2, listOf<String>("a", "b"))
            preFilledBuffer.buffer() shouldBe listOf("a", "b")
        }
    }
}