package com.reactnativenavigation.options

import android.app.Activity
import android.view.ViewGroup
import androidx.annotation.RestrictTo
import com.facebook.react.ReactInstanceManager
import com.facebook.react.bridge.ReactContext
import com.reactnativenavigation.options.LayoutNode
import com.reactnativenavigation.options.parsers.TypefaceLoader
import com.reactnativenavigation.react.events.EventEmitter
import com.reactnativenavigation.utils.Assertions
import com.reactnativenavigation.utils.CollectionUtils
import com.reactnativenavigation.utils.ImageLoader
import com.reactnativenavigation.utils.RenderChecker
import com.reactnativenavigation.viewcontrollers.bottomtabs.BottomTabPresenter
import com.reactnativenavigation.viewcontrollers.bottomtabs.BottomTabsAnimator
import com.reactnativenavigation.viewcontrollers.bottomtabs.BottomTabsController
import com.reactnativenavigation.viewcontrollers.bottomtabs.BottomTabsPresenter
import com.reactnativenavigation.viewcontrollers.bottomtabs.attacher.BottomTabsAttacher
import com.reactnativenavigation.viewcontrollers.child.ChildControllersRegistry
import com.reactnativenavigation.viewcontrollers.component.ComponentPresenter
import com.reactnativenavigation.viewcontrollers.component.ComponentViewController
import com.reactnativenavigation.viewcontrollers.externalcomponent.ExternalComponentCreator
import com.reactnativenavigation.viewcontrollers.externalcomponent.ExternalComponentPresenter
import com.reactnativenavigation.viewcontrollers.externalcomponent.ExternalComponentViewController
import com.reactnativenavigation.viewcontrollers.parent.ParentController
import com.reactnativenavigation.viewcontrollers.sidemenu.SideMenuController
import com.reactnativenavigation.viewcontrollers.sidemenu.SideMenuPresenter
import com.reactnativenavigation.viewcontrollers.stack.StackControllerBuilder
import com.reactnativenavigation.viewcontrollers.stack.StackPresenter
import com.reactnativenavigation.viewcontrollers.stack.topbar.TopBarController
import com.reactnativenavigation.viewcontrollers.stack.topbar.button.IconResolver
import com.reactnativenavigation.viewcontrollers.toptabs.TopTabsController
import com.reactnativenavigation.viewcontrollers.viewcontroller.Presenter
import com.reactnativenavigation.viewcontrollers.viewcontroller.ViewController
import com.reactnativenavigation.views.component.ComponentViewCreator
import com.reactnativenavigation.views.stack.topbar.TopBarBackgroundViewCreator
import com.reactnativenavigation.views.stack.topbar.titlebar.TitleBarButtonCreator
import com.reactnativenavigation.views.stack.topbar.titlebar.TitleBarReactViewCreator
import com.reactnativenavigation.views.toptabs.TopTabsLayoutCreator
import java.lang.RuntimeException
import java.util.*

class LayoutFactory(private val reactInstanceManager: ReactInstanceManager) {
    private lateinit var activity: Activity
    private lateinit var childRegistry: ChildControllersRegistry
    private lateinit var eventEmitter: EventEmitter
    private var externalComponentCreators: Map<String, ExternalComponentCreator>? = null
    private var defaultOptions = Options()
    private var typefaceManager: TypefaceLoader? = null
    fun setDefaultOptions(defaultOptions: Options) {
        Assertions.assertNotNull(defaultOptions)
        this.defaultOptions = defaultOptions
    }

    fun init(activity: Activity, eventEmitter: EventEmitter, childRegistry: ChildControllersRegistry, externalComponentCreators: Map<String, ExternalComponentCreator>?) {
        this.activity = activity
        this.eventEmitter = eventEmitter
        this.childRegistry = childRegistry
        this.externalComponentCreators = externalComponentCreators
        typefaceManager = TypefaceLoader(activity!!)
    }

    fun create(node: LayoutNode?): ViewController<ViewGroup> {
        return node?.id?.let {
            val context = reactInstanceManager.currentReactContext ?: return@let null
            when (node.type) {
                LayoutNode.Type.Component -> createComponent(context, node)
                LayoutNode.Type.ExternalComponent -> createExternalComponent(context, node)
                LayoutNode.Type.Stack -> createStack(context, node)
                LayoutNode.Type.BottomTabs -> createBottomTabs(context, node)
                LayoutNode.Type.SideMenuRoot -> createSideMenuRoot(context, node)
                LayoutNode.Type.SideMenuCenter -> createSideMenuContent(node)
                LayoutNode.Type.SideMenuLeft -> createSideMenuLeft(node)
                LayoutNode.Type.SideMenuRight -> createSideMenuRight(node)
                LayoutNode.Type.TopTabs -> createTopTabs(context, node)
                else -> throw IllegalArgumentException("Invalid node type: " + node.type)
            }
        } ?: throw  RuntimeException("Can't create controller for null node or null node.id, node: $node")
    }

    private fun createSideMenuRoot(context: ReactContext, node: LayoutNode): ViewController<*> {
        val sideMenuController = SideMenuController(activity,
                childRegistry,
                node.id!!,
                Options.parse(context, typefaceManager, node.options),
                SideMenuPresenter(),
                Presenter(activity, defaultOptions)
        )
        var childControllerCenter: ViewController<*>? = null
        var childControllerLeft: ViewController<*>? = null
        var childControllerRight: ViewController<*>? = null
        for (child in node.children) {
            when (child!!.type) {
                LayoutNode.Type.SideMenuCenter -> {
                    childControllerCenter = create(child)
                    childControllerCenter.parentController = sideMenuController
                }
                LayoutNode.Type.SideMenuLeft -> {
                    childControllerLeft = create(child)
                    childControllerLeft.parentController = sideMenuController
                }
                LayoutNode.Type.SideMenuRight -> {
                    childControllerRight = create(child)
                    childControllerRight.parentController = sideMenuController
                }
                else -> throw IllegalArgumentException("Invalid node type in sideMenu: " + node.type)
            }
        }
        if (childControllerCenter != null) {
            sideMenuController.setCenterController(childControllerCenter)
        }
        if (childControllerLeft != null) {
            sideMenuController.setLeftController(childControllerLeft)
        }
        if (childControllerRight != null) {
            sideMenuController.setRightController(childControllerRight)
        }
        return sideMenuController
    }

    private fun createSideMenuContent(node: LayoutNode): ViewController<*> {
        return create(node.children[0])
    }

    private fun createSideMenuLeft(node: LayoutNode): ViewController<*> {
        return create(node.children[0])
    }

    private fun createSideMenuRight(node: LayoutNode): ViewController<*> {
        return create(node.children[0])
    }

    private fun createComponent(context: ReactContext?, node: LayoutNode): ViewController<*> {
        val id = node.id ?: ""
        val name = node.data.optString("name")
        return ComponentViewController(activity,
                childRegistry,
                id,
                name,
                ComponentViewCreator(reactInstanceManager),
                Options.parse(context, typefaceManager, node.options),
                Presenter(activity, defaultOptions),
                ComponentPresenter(defaultOptions)
        )
    }

    private fun createExternalComponent(context: ReactContext, node: LayoutNode): ViewController<*> {
        val externalComponent = ExternalComponent.parse(node!!.data)
        return ExternalComponentViewController(activity,
                childRegistry,
                node.id,
                Presenter(activity, defaultOptions),
                externalComponent,
                externalComponentCreators!![externalComponent.name.get()],
                reactInstanceManager,
                EventEmitter(context),
                ExternalComponentPresenter(),
                Options.parse(context, typefaceManager, node.options)
        )
    }

    private fun createStack(context: ReactContext, node: LayoutNode): ViewController<*> {
        return StackControllerBuilder(activity, eventEmitter)
                .setChildren(createChildren(node!!.children))
                .setChildRegistry(childRegistry)
                .setTopBarController(TopBarController())
                .setId(node.id)
                .setInitialOptions(Options.parse(context, typefaceManager, node.options))
                .setStackPresenter(StackPresenter(activity,
                        TitleBarReactViewCreator(reactInstanceManager),
                        TopBarBackgroundViewCreator(reactInstanceManager),
                        TitleBarButtonCreator(reactInstanceManager),
                        IconResolver(activity, ImageLoader()),
                        TypefaceLoader(activity!!),
                        RenderChecker(),
                        defaultOptions
                ))
                .setPresenter(Presenter(activity, defaultOptions))
                .build()
    }

    private fun createChildren(children: List<LayoutNode>): List<ViewController<*>> {
        val result: MutableList<ViewController<*>> = ArrayList()
        for (child in children) {
            result.add(create(child))
        }
        return result
    }

    private fun createBottomTabs(context: ReactContext, node: LayoutNode): ViewController<ViewGroup> {
        val tabs: List<ViewController<*>>? = CollectionUtils.map(node.children) { childNode: LayoutNode -> create(childNode) }
        val bottomTabsPresenter = BottomTabsPresenter(tabs!!, defaultOptions, BottomTabsAnimator())
        return BottomTabsController(activity,
                tabs,
                childRegistry,
                eventEmitter,
                ImageLoader(),
                node.id!!,
                Options.parse(context, typefaceManager, node.options),
                Presenter(activity, defaultOptions),
                BottomTabsAttacher(tabs, bottomTabsPresenter, defaultOptions),
                bottomTabsPresenter,
                BottomTabPresenter(activity, tabs, ImageLoader(), TypefaceLoader(activity), defaultOptions))
    }

    private fun createTopTabs(context: ReactContext?, node: LayoutNode): ViewController<*> {
        val tabs: MutableList<ViewController<*>> = ArrayList()
        for (i in node.children.indices) {
            val tabController = create(node.children[i])
            val options = Options.parse(context, typefaceManager, node.children[i]?.options)
            options.setTopTabIndex(i)
            tabs.add(tabController)
        }
        return TopTabsController(activity, childRegistry, node.id!!, tabs, TopTabsLayoutCreator(activity, tabs), Options.parse(context, typefaceManager, node.options), Presenter(activity,
                defaultOptions))
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    fun getDefaultOptions(): Options {
        return defaultOptions
    }

}