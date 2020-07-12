package com.reactnativenavigation.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.MenuItem;
import android.widget.TextView;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.fakes.IconResolverFake;
import com.reactnativenavigation.parse.params.Bool;
import com.reactnativenavigation.parse.params.Button;
import com.reactnativenavigation.parse.params.Colour;
import com.reactnativenavigation.parse.params.Number;
import com.reactnativenavigation.parse.params.Text;
import com.reactnativenavigation.viewcontrollers.TitleBarButtonController;
import com.reactnativenavigation.views.titlebar.TitleBar;
import com.reactnativenavigation.views.titlebar.TitleBarButtonCreator;

import org.junit.Test;
import org.robolectric.annotation.LooperMode;
import org.robolectric.shadows.ShadowLooper;

import androidx.appcompat.widget.ActionMenuView;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;

@LooperMode(LooperMode.Mode.PAUSED)
public class ButtonPresenterTest extends BaseTest {
    private static final String BTN_TEXT = "button1";

    private TitleBar titleBar;
    private ButtonPresenter uut;
    private TitleBarButtonController buttonController;
    private Button button;

    @Override
    public void beforeEach() {
        Activity activity = newActivity();
        titleBar = new TitleBar(activity);
        activity.setContentView(titleBar);
        button = createButton();

        uut = new ButtonPresenter(button, new IconResolverFake(activity));
        buttonController = new TitleBarButtonController(
                activity,
                uut,
                button,
                mock(TitleBarButtonCreator.class),
                mock(TitleBarButtonController.OnClickListener.class)
        );
    }

    @Test
    public void applyOptions_buttonIsAddedToMenu() {
        addButtonAndApplyOptions();

        assertThat(findButtonView().getText().toString()).isEqualTo(BTN_TEXT);
    }

    @Test
    public void applyOptions_appliesColorOnButtonTextView() {
        button.color = new Colour(Color.RED);
        addButtonAndApplyOptions();

        assertThat(findButtonView().getCurrentTextColor()).isEqualTo(Color.RED);
    }

    @Test
    public void apply_disabledColor() {
        button.enabled = new Bool(false);
        addButtonAndApplyOptions();

        assertThat(findButtonView().getCurrentTextColor()).isEqualTo(ButtonPresenter.DISABLED_COLOR);
    }

    private void addButtonAndApplyOptions() {
        MenuItem menuItem = buttonController.createAndAddButtonToTitleBar(titleBar, 0);
        uut.applyOptions(titleBar, menuItem, buttonController::getView);
    }

    private TextView findButtonView() {
        ShadowLooper.idleMainLooper();
        return (TextView) ViewUtils.findChildrenByClass(
                requireNonNull(ViewUtils.findChildByClass(titleBar, ActionMenuView.class)),
                TextView.class,
                child -> true
        ).get(0);
    }

    private Button createButton() {
        Button b = new Button();
        b.id = "btn1";
        b.text = new Text(BTN_TEXT);
        b.showAsAction = new Number(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return b;
    }
}
