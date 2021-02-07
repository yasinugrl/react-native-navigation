package com.reactnativenavigation.newoptions

import com.nhaarman.mockitokotlin2.*
import com.reactnativenavigation.BaseTest
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test

fun createDemoOptions(): Options {
    val jsonObject = JSONObject()
    jsonObject.put("x", 1)
    jsonObject.put("y", 1.0)
    jsonObject.put("z", 1L)
    jsonObject.put("w", true)
    jsonObject.put("m", "txt")
    jsonObject.put("w", 10)
    jsonObject.put("arrInt", JSONArray().apply {
        put(1)
        put(2)
        put(3)
    })
    jsonObject.put("arrStr", JSONArray().apply {
        put("")
        put("")
        put("")
    })
    jsonObject.put("arrObject", JSONArray().apply {
        put(JSONObject())
        put(JSONObject())
        put(JSONObject().apply {
            put("c", "wow")
            put("d", "wow!")
        })
    })
    jsonObject.put("o", JSONObject().apply {
        put("a", 1)
        put("b", "txt2")
        put("o2", JSONObject().apply {
        })
    })
    return jsonOptionsParser(jsonObject)
}

class ParseOptionsTest : BaseTest() {

    @Test
    fun `parse first level props`() {
        val options = jsonOptionsParser(JSONObject())
        assertThat(options.isEmpty()).isTrue()

        val options2 = jsonOptionsParser(JSONObject().apply {
            put("a", 4)
        })
        assertThat(options2.isEmpty()).isFalse()
        assertThat(options2["a"]?.value).isEqualTo(4)
    }

    @Test
    fun `parse nested object value`() {
        val options = jsonOptionsParser(JSONObject().apply {
            put("a", JSONObject().apply {
                put("x", "prop")
            })
        })
        assertThat(options["a"]).isInstanceOf(OptionsProperty::class.java)
        val optionsProperty = options["a"] as OptionsProperty
        assertThat(optionsProperty["x"]?.value).isEqualTo("prop")

        val options2 = jsonOptionsParser(JSONObject().apply {
            put("a", JSONObject().apply {
                put("b", JSONObject().apply {
                    put("c", JSONObject().apply {
                        put("x", "prop")
                    })
                })
            })
        })

        assertThat(options2["a"]).isInstanceOf(OptionsProperty::class.java)
        val aProp = options2["a"] as OptionsProperty
        assertThat(aProp["b"]).isInstanceOf(OptionsProperty::class.java)
        val bProp = aProp["b"] as OptionsProperty
        assertThat(bProp["c"]).isInstanceOf(OptionsProperty::class.java)
        val cProp = bProp["c"] as OptionsProperty
        assertThat(cProp["x"]).isInstanceOf(Property::class.java)
        assertThat(cProp["x"]?.value).isEqualTo("prop")
    }

    @Test
    fun `parse jsonArray into ListProperty`() {
        val options = jsonOptionsParser(JSONObject().apply {
            put("a", JSONArray().apply {
                this.put("st1")
                this.put("st2")
            })
            put("b", JSONArray().apply {
                this.put(1)
                this.put(2)
                this.put(2)
            })

            put("c", JSONArray().apply {
                this.put(1.5)
                this.put(2.5)
            })
            put("d", JSONArray().apply {
                this.put(false)
                this.put(true)
            })
            put("e", JSONArray().apply {
                this.put(JSONObject())
                this.put(JSONObject().apply {
                    this.put("x", "prop")
                })
                this.put(JSONObject().apply {
                    this.put("x", "prop2")
                })
            })
        })
        assertThat(options["a"]).isInstanceOf(ListProperty::class.java)
        val aProp = options["a"] as ListProperty<*>
        assertThat(aProp.length()).isEqualTo(2)
        assertThat(aProp[0]).isEqualTo("st1")
        assertThat(aProp[1]).isEqualTo("st2")

        assertThat(options["b"]).isInstanceOf(ListProperty::class.java)
        val bProp = options["b"] as ListProperty<*>
        assertThat(bProp.length()).isEqualTo(3)
        assertThat(bProp[0]).isEqualTo(1)
        assertThat(bProp[1]).isEqualTo(2)
        assertThat(bProp[2]).isEqualTo(2)

        assertThat(options["c"]).isInstanceOf(ListProperty::class.java)
        val cProp = options["c"] as ListProperty<*>
        assertThat(cProp.length()).isEqualTo(2)
        assertThat(cProp[0]).isEqualTo(1.5)
        assertThat(cProp[1]).isEqualTo(2.5)

        assertThat(options["d"]).isInstanceOf(ListProperty::class.java)
        val dProp = options["d"] as ListProperty<*>
        assertThat(dProp.length()).isEqualTo(2)
        assertThat(dProp[0]).isEqualTo(false)
        assertThat(dProp[1]).isEqualTo(true)

        assertThat(options["e"]).isInstanceOf(ListProperty::class.java)
        val eProp = options["e"] as ListProperty<*>
        assertThat(eProp.length()).isEqualTo(3)
        assertThat(eProp[0]).isInstanceOf(Options::class.java)
        assertThat((eProp[0] as Options).isEmpty()).isTrue()
        assertThat(eProp[1]).isInstanceOf(Options::class.java)
        assertThat((eProp[1] as Options).isEmpty()).isFalse()
        assertThat((eProp[1] as Options)["x"]?.value).isEqualTo("prop")
        assertThat(eProp[2]).isInstanceOf(Options::class.java)
        assertThat((eProp[2] as Options).isEmpty()).isFalse()
        assertThat((eProp[2] as Options)["x"]?.value).isEqualTo("prop2")
    }

    @Test
    fun `parse from json should return proper parsed types`() {

        val uut = createDemoOptions()
        assert(uut.has("x"))
        assert(uut["x"] is IntProperty)
        assertThat(uut["x"]?.value).isEqualTo(1)
        assertThat(uut["y"]?.value).isEqualTo(1.0)
        assertThat(uut["z"]?.value).isEqualTo(1L)
        assertThat(uut["w"]?.value).isEqualTo(true)
        assertThat(uut["m"]?.value).isEqualTo("txt")
        assertThat(uut["o"]).isInstanceOf(OptionsProperty::class.java)
        assertThat((uut["o"] as OptionsProperty)["o2"]).isInstanceOf(OptionsProperty::class.java)
        assertThat((uut["o"] as OptionsProperty)["b"]?.value).isEqualTo("txt2")
        assertThat((uut["arrInt"] as ListProperty<*>)[1]).isEqualTo(2)
        assertThat((uut["arrObject"] as ListProperty<*>)[0]).isInstanceOf(Options::class.java)
        assertThat(((uut["arrObject"] as ListProperty<*>)[0] as Options).isEmpty()).isTrue()
        assertThat(((uut["arrObject"] as ListProperty<*>)[1] as Options).isEmpty()).isTrue()
        assertThat(((uut["arrObject"] as ListProperty<*>)[2] as Options).isEmpty()).isFalse()
    }
}

class OptionsTest : BaseTest() {

    lateinit var uut: Options
    override fun beforeEach() {
        super.beforeEach()
        uut = createDemoOptions()
    }

    @Test
    fun `merge that add properties must notify container options`(){
        val o2SubscriberMock: PropertySubscriber<Options> = mock { }

        val jsonObject = JSONObject().apply {
            this.put("o", JSONObject().apply {
                this.put("o2", JSONObject().apply {
                    this.put("c", 2)
                    this.put("b", "prop")
                })
            })
        }
        val optionsProperty = uut["o"] as OptionsProperty
        (optionsProperty["o2"] as OptionsProperty).subscribe(o2SubscriberMock)
        val mergeOptions = jsonOptionsParser(jsonObject)
        uut.merge(mergeOptions)


    }
    @Test
    fun `merge changed nested props must notify subscribers`() {
        val optionsProperty = uut["o"] as OptionsProperty

        val xSubscriberMock: PropertySubscriber<Int> = mock { }
        val oSubscriberMock: PropertySubscriber<Options> = mock { }
        val obSubscriberMock: PropertySubscriber<String> = mock { }
        val o2SubscriberMock: PropertySubscriber<Options> = mock { }
        val o2cSubscriberMock: PropertySubscriber<Int> = mock { }

        optionsProperty.subscribe(oSubscriberMock)
        (uut["x"] as IntProperty).subscribe(xSubscriberMock)
        (optionsProperty["o2"] as OptionsProperty).subscribe(o2SubscriberMock)
        (optionsProperty["b"] as StringProperty).subscribe(obSubscriberMock)

        val jsonObject = JSONObject().apply {
            this.put("o", JSONObject().apply {
                this.put("a", 4)
                this.put("b", "prop")
                this.put("o2", JSONObject().apply {
                    this.put("c", 2)
                    this.put("b", "prop")
                })
            })
        }

        val mergeOptions = jsonOptionsParser(jsonObject)
        uut.merge(mergeOptions)

        verify(xSubscriberMock, never()).invoke(any())
        verify(oSubscriberMock, never()).invoke(any())
        verify(o2SubscriberMock, times(1)).invoke(any())
        verify(obSubscriberMock, times(1)).invoke("prop")


        assertThat(((optionsProperty["o2"] as OptionsProperty)["c"] as IntProperty).value).isEqualTo(2)
        ((optionsProperty["o2"] as OptionsProperty)["c"] as IntProperty).subscribe(o2cSubscriberMock)

        val jsonObject2 = JSONObject().apply {
            this.put("o", JSONObject().apply {
                this.put("o2", JSONObject().apply {
                    this.put("c", 4)
                })
            })
        }
        uut.merge(jsonOptionsParser(jsonObject2))

        verify(xSubscriberMock, never()).invoke(any())
        verify(oSubscriberMock, never()).invoke(any())
        verify(o2SubscriberMock, times(1)).invoke(any())
        verify(obSubscriberMock, times(1)).invoke("prop")
        verify(o2cSubscriberMock, times(1)).invoke(4)
    }

    fun `merge changed nested objects props`() {
        assertThat((uut["o"] as OptionsProperty)["a"]?.value).isEqualTo(1)
        val jsonObject = JSONObject().apply {
            this.put("o", JSONObject().apply {
                this.put("a", 4)
                this.put("b", "txt3")
                this.put("o2", JSONObject().apply {
                    this.put("c", 2)
                    this.put("d", "prop")
                })
            })
        }

        val mergeOptions = jsonOptionsParser(jsonObject)
        uut.merge(mergeOptions)
        assertThat((uut["o"] as OptionsProperty)["a"]?.value).isEqualTo(4)
        assertThat((uut["o"] as OptionsProperty)["b"]?.value).isEqualTo("txt3")
        assertThat(((uut["o"] as OptionsProperty)["o2"] as OptionsProperty)["c"]?.value).isEqualTo(2)
        assertThat(((uut["o"] as OptionsProperty)["o2"] as OptionsProperty)["d"]?.value).isEqualTo("prop")
    }


    @Test
    fun `merge changed first level value`() {
        val options = jsonOptionsParser(JSONObject().apply {
            put("a", 4)
        })
        val options2 = jsonOptionsParser(JSONObject().apply {
            put("a", 5)
        })
        options2.merge(options)
        assertThat(options2.isEmpty()).isFalse()
        assertThat(options2["a"]?.value).isEqualTo(4)
    }

    @Test
    fun `merge added first level value`() {
        val options = jsonOptionsParser(JSONObject().apply {
            put("a", 4)
        })
        val options2 = jsonOptionsParser(JSONObject().apply {
        })
        options2.merge(options)
        assertThat(options2["a"]?.value).isEqualTo(4)

        val options3 = jsonOptionsParser(JSONObject().apply {
            put("b", 6)
        })
        options2.merge(options3)
        assertThat(options2["b"]?.value).isEqualTo(6)
    }


}