package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.View;

import com.reactnativenavigation.interfaces.ScrollEventListener;
import com.reactnativenavigation.parse.Options;
import com.reactnativenavigation.presentation.ComponentPresenter;
import com.reactnativenavigation.presentation.Presenter;
import com.reactnativenavigation.utils.StatusBarUtils;
import com.reactnativenavigation.views.ComponentLayout;
import com.reactnativenavigation.views.ReactComponent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import static com.reactnativenavigation.utils.ObjectUtils.perform;

public class ComponentViewController extends ChildController<ComponentLayout> {
    private final String componentName;
    private ComponentPresenter presenter;
    private final ReactViewCreator viewCreator;

    ReactComponent getComponent() {
        return view;
    }

    public ComponentViewController(final Activity activity,
                                   final ChildControllersRegistry childRegistry,
                                   final String id,
                                   final String componentName,
                                   final ReactViewCreator viewCreator,
                                   final Options initialOptions,
                                   final Presenter presenter,
                                   final ComponentPresenter componentPresenter) {
        super(activity, childRegistry, id, presenter, initialOptions);
        this.componentName = componentName;
        this.viewCreator = viewCreator;
        this.presenter = componentPresenter;
    }

    @Override
    public String getCurrentComponentName() {
        return this.componentName;
    }

    @Override
    public void setDefaultOptions(Options defaultOptions) {
        super.setDefaultOptions(defaultOptions);
        presenter.setDefaultOptions(defaultOptions);
    }

    @Override
    public ScrollEventListener getScrollEventListener() {
        return perform(view, null, ComponentLayout::getScrollEventListener);
    }

    @Override
    public void onViewAppeared() {
        super.onViewAppeared();
        if (view != null) view.sendComponentStart();
    }

    @Override
    public void onViewDisappear() {
        if (view != null) view.sendComponentStop();
        super.onViewDisappear();
    }

    @Override
    public void sendOnNavigationButtonPressed(String buttonId) {
        getView().sendOnNavigationButtonPressed(buttonId);
    }

    @Override
    public void applyOptions(Options options) {
        if (isRoot()) applyTopInset();
        super.applyOptions(options);
        getView().applyOptions(options);
        presenter.applyOptions(getView(), resolveCurrentOptions(presenter.defaultOptions));
    }

    @Override
    public boolean isViewShown() {
        return super.isViewShown() && view != null && view.isReady();
    }

    @NonNull
    @Override
    protected ComponentLayout createView() {
        ComponentLayout view = (ComponentLayout) viewCreator.create(getActivity(), getId(), componentName);
        return (ComponentLayout) view.asView();
    }

    @Override
    public void mergeOptions(Options options) {
        if (options == Options.EMPTY) return;
        if (isViewShown()) presenter.mergeOptions(getView(), options);
        super.mergeOptions(options);
    }

    @Override
    public void applyBottomInset() {
        if (view != null) presenter.applyBottomInset(view, getBottomInset());
    }

    @Override
    public void applyTopInset() {
        if (view != null) presenter.applyTopInsets(view, getTopInset());
    }

    @Override
    public int getTopInset() {
        int statusBarInset = resolveCurrentOptions().statusBar.isHiddenOrDrawBehind() ? 0 : StatusBarUtils.getStatusBarHeight(getActivity());
        return statusBarInset + perform(getParentController(), 0, p -> p.getTopInset(this));
    }

    @Override
    protected WindowInsetsCompat applyWindowInsets(ViewController view, WindowInsetsCompat insets) {
        final Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        int systemWindowInsetTop = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top +
                insets.getInsets(WindowInsetsCompat.Type.navigationBars()).top -
                systemBarsInsets.top;
        int systemWindowInsetBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom +
                insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom -
                systemBarsInsets.bottom;

        WindowInsetsCompat finalInsets = new WindowInsetsCompat.Builder()
                .setInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime(),
                        Insets.of(systemBarsInsets.left,
                                systemWindowInsetTop,
                                systemBarsInsets.right,
                                Math.max(systemWindowInsetBottom - getBottomInset(), 0)))
                .build();
        ViewCompat.onApplyWindowInsets(view.getView(), finalInsets);
        return insets;
    }

    protected WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
        final Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        int systemWindowInsetTop = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top +
                insets.getInsets(WindowInsetsCompat.Type.navigationBars()).top -
                systemBarsInsets.top;
        int systemWindowInsetBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom +
                insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom -
                systemBarsInsets.bottom;

        WindowInsetsCompat finalInsets = new WindowInsetsCompat.Builder()
                .setInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime(),
                        Insets.of(systemBarsInsets.left,
                                systemWindowInsetTop,
                                systemBarsInsets.right,
                                Math.max(systemWindowInsetBottom - getBottomInset(), 0)))
                .build();
        ViewCompat.onApplyWindowInsets(view, finalInsets);
        return insets;
    }

    @Override
    public void destroy() {
        final boolean blurOnUnmount = options != null && options.modal.blurOnUnmount.isTrue();
        if (blurOnUnmount) {
            blurActivityFocus();
        }
        super.destroy();
    }

    private void blurActivityFocus() {
        final Activity activity = getActivity();
        final View focusView = activity != null ? activity.getCurrentFocus() : null;
        if (focusView != null) {
            focusView.clearFocus();
        }
    }
}
