package com.reactnativenavigation.options

import com.reactnativenavigation.BaseTest
import com.reactnativenavigation.options.LayoutNode
import com.reactnativenavigation.options.parsers.LayoutNodeParser
import org.assertj.core.api.Java6Assertions
import org.json.JSONObject
import org.junit.Test

class LayoutNodeParserTest : BaseTest() {
    @Test
    fun dto() {
        val node = LayoutNode("the id", LayoutNode.Type.Component)
        Java6Assertions.assertThat(node.id).isEqualTo("the id")
        Java6Assertions.assertThat(node.type).isEqualTo(LayoutNode.Type.Component)
        Java6Assertions.assertThat(node.data.keys()).isEmpty()
        Java6Assertions.assertThat(node.children).isEmpty()
    }

    @Test
    fun parseType() {
        Java6Assertions.assertThat(LayoutNode.Type.valueOf("Component")).isEqualTo(LayoutNode.Type.Component)
    }

    @Test(expected = RuntimeException::class)
    fun invalidType() {
        LayoutNode.Type.valueOf("some type")
    }

    @Test
    fun parseFromTree() {
        val tree = JSONObject("{id: node1, " +
                "type: Stack, " +
                "data: {dataKey: dataValue}, " +
                "children: [{id: childId1, type: Component}]}")
        val result = LayoutNodeParser.parse(tree)
        Java6Assertions.assertThat(result).isNotNull()
        Java6Assertions.assertThat(result.id).isEqualTo("node1")
        Java6Assertions.assertThat(result.type).isEqualTo(LayoutNode.Type.Stack)
        Java6Assertions.assertThat(result.data.length()).isEqualTo(1)
        Java6Assertions.assertThat(result.data.getString("dataKey")).isEqualTo("dataValue")
        Java6Assertions.assertThat(result.children).hasSize(1)
        Java6Assertions.assertThat(result.children[0]?.id).isEqualTo("childId1")
        Java6Assertions.assertThat(result.children[0]?.type).isEqualTo(LayoutNode.Type.Component)
        Java6Assertions.assertThat(result.children[0]?.data?.keys()).isEmpty()
        Java6Assertions.assertThat(result.children[0]?.children).isEmpty()
    }
}