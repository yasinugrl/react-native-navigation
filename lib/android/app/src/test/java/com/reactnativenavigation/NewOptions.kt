package com.reactnativenavigation

import com.reactnativenavigation.newoptions.IntProperty
import com.reactnativenavigation.newoptions.Options
import org.assertj.core.api.Assertions
import org.junit.Test

class BaseOptionsTest : BaseTest() {
    private lateinit var uut: Options

    override fun beforeEach() {
        super.beforeEach()
        uut = Options()
    }

    @Test
    fun `base test`() {
        uut.addProperty(IntProperty("width", 10))
        uut.addProperty(IntProperty("height", 20))
        uut.addProperty(IntProperty("color", 0xFF))

        val result = uut.merge(listOf(IntProperty("width", 5), IntProperty("xxx", 8)).associateBy { it.name })

        Assertions.assertThat(result.newProps.size).isEqualTo(1)
        Assertions.assertThat(result.updatedProps.size).isEqualTo(1)
        Assertions.assertThat(result.updatedProps.first().value).isEqualTo(5)
    }
}