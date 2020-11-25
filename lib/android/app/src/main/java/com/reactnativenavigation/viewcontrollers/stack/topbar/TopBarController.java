package com.reactnativenavigation.viewcontrollers.stack.topbar;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;

import com.reactnativenavigation.options.AnimationOptions;
import com.reactnativenavigation.options.Options;
import com.reactnativenavigation.options.animations.ViewAnimationOptions;
import com.reactnativenavigation.viewcontrollers.stack.topbar.button.ButtonController;
import com.reactnativenavigation.viewcontrollers.stack.topbar.title.TitleBarReactViewController;
import com.reactnativenavigation.views.stack.StackLayout;
import com.reactnativenavigation.views.stack.topbar.TopBar;
import com.reactnativenavigation.views.stack.topbar.titlebar.TitleBar;

import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.viewpager.widget.ViewPager;

import static com.reactnativenavigation.utils.CollectionUtils.*;
import static com.reactnativenavigation.utils.ObjectUtils.perform;
import static com.reactnativenavigation.utils.ViewUtils.isVisible;


public class TopBarController {
    private TopBar topBar;
    private TitleBar titleBar;
    private TopBarAnimator animator;

    public TopBarAnimator getAnimator() {
        return animator;
    }

    public MenuItem getRightButton(int index) {
        return titleBar.getRightButton(index);
    }

    public TopBar getView() {
        return topBar;
    }

    public int getHeight() {
        return perform(topBar, 0, View::getHeight);
    }

    public int getRightButtonsCount() {
        return topBar.getRightButtonsCount();
    }

    public Drawable getLeftButton() {
        return titleBar.getNavigationIcon();
    }

    @VisibleForTesting
    public void setAnimator(TopBarAnimator animator) {
        this.animator = animator;
    }

    public TopBarController() {
        animator = new TopBarAnimator();
    }

    public TopBar createView(Context context, StackLayout parent) {
        if (topBar == null) {
            topBar = createTopBar(context, parent);
            titleBar = topBar.getTitleBar();
            animator.bindView(topBar, parent);
        }
        return topBar;
    }

    protected TopBar createTopBar(Context context, StackLayout stackLayout) {
        return new TopBar(context);
    }

    public void initTopTabs(ViewPager viewPager) {
        topBar.initTopTabs(viewPager);
    }

    public void clearTopTabs() {
        topBar.clearTopTabs();
    }

    public Animator getPushAnimation(Options appearingOptions, ViewAnimationOptions topBar) {
        return animator.getPushAnimation(appearingOptions, topBar);
    }

    public Animator getPopAnimation(Options appearingOptions, ViewAnimationOptions topBar) {
        return animator.getPopAnimation(appearingOptions, topBar);
    }

    public void show() {
        if (isVisible(topBar) || animator.isAnimatingShow()) return;
        topBar.setVisibility(View.VISIBLE);
    }

    public void showAnimate(AnimationOptions options, int translationDy) {
        if (isVisible(topBar) || animator.isAnimatingShow()) return;
        animator.show(options, translationDy);
    }

    public void hide() {
        if (!animator.isAnimatingHide()) topBar.setVisibility(View.GONE);
    }

    public void hideAnimate(AnimationOptions options, float translationStart, float translationEnd) {
        hideAnimate(options, translationStart, translationEnd, () -> {});
    }

    private void hideAnimate(AnimationOptions options, float translationStart, float translationEnd, Runnable onAnimationEnd) {
        if (!isVisible(topBar) || animator.isAnimatingHide()) return;
        animator.hide(options, translationStart, translationEnd, onAnimationEnd);
    }

    public void setTitleComponent(TitleBarReactViewController component) {
        topBar.setTitleComponent(component.getView());
    }

    public void applyRightButtons(List<ButtonController> toAdd) {
        topBar.clearRightButtons();
        forEachIndexed(toAdd, (b, i) -> b.addToMenu(titleBar, (toAdd.size() - i) * 10));
    }

    public void mergeRightButtons(List<ButtonController> toAdd, List<ButtonController> toRemove) {
        forEach(toRemove, btn -> topBar.removeRightButton(btn));
        forEachIndexed(toAdd, (b, i) -> b.addToMenu(titleBar, (toAdd.size() - i) * 10));
    }

    public void setLeftButtons(List<ButtonController> leftButtons) {
        titleBar.setLeftButtons(leftButtons);
    }
}
