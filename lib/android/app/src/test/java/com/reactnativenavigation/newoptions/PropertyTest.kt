package com.reactnativenavigation.newoptions

import com.nhaarman.mockitokotlin2.*
import com.reactnativenavigation.BaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PropertyTest : BaseTest() {
    lateinit var uut: Property<Int>

    override fun beforeEach() {
        super.beforeEach()
        this.uut = IntProperty("P1", 0xff)
    }

    @Test
    fun `should get valid current value and after change`() {
        assertThat(this.uut.value).isEqualTo(0xff)
        this.uut.value = 10
        assertThat(this.uut.value).isEqualTo(10)
    }

    @Test
    fun `should notify subscriber about new values`() {
        val subscriberMock: PropertySubscriber<Int> = mock { }
        this.uut.subscribe(subscriberMock)
        verify(subscriberMock, never()).invoke(any<Int>());
        this.uut.value = 5
        verify(subscriberMock, times(1)).invoke(5);
    }

    @Test
    fun `should notify subscriber about current value upon subscribe with initial value enabled`() {
        val subscriberMock: PropertySubscriber<Int> = mock { }
        this.uut.value = 5
        this.uut.subscribe(subscriberMock, true)
        verify(subscriberMock, times(1)).invoke(5)
        this.uut.value = 11
        verify(subscriberMock, times(1)).invoke(11)
    }

    @Test
    fun `should not notify unsubscribed subscriber about new values once`() {
        val subscriberMock: PropertySubscriber<Int> = mock { }
        this.uut.subscribe(subscriberMock)
        this.uut.value = 5
        verify(subscriberMock, times(1)).invoke(5)
        this.uut.unsubscribe(subscriberMock)
        this.uut.value = 10
        verify(subscriberMock, never()).invoke(10)
    }

    @Test
    fun `should notify many subscribers about new values`() {
        val subscriberMock: PropertySubscriber<Int> = mock { }
        val subscriberMock2: PropertySubscriber<Int> = mock { }
        this.uut.subscribe(subscriberMock)
        this.uut.value = 5
        verify(subscriberMock, times(1)).invoke(5)

        this.uut.subscribe(subscriberMock2, true)
        verify(subscriberMock2, times(1)).invoke(5)
        this.uut.value = 10

        verify(subscriberMock, times(1)).invoke(10)
        verify(subscriberMock2, times(1)).invoke(10)

        this.uut.unsubscribe(subscriberMock)
        this.uut.value = 40
        verify(subscriberMock2, times(1)).invoke(40)
        verify(subscriberMock, never()).invoke(40)
    }

    @Test
    fun `should notify only about changes that are not equal (using ==)`() {
        val subscriberMock: PropertySubscriber<Int> = mock { }
        this.uut.subscribe(subscriberMock)
        this.uut.value = 5
        verify(subscriberMock, times(1)).invoke(5)
        this.uut.value = 5
        this.uut.value = 6
        verify(subscriberMock, times(1)).invoke(5)
        verify(subscriberMock, times(1)).invoke(6)
    }


}