package com.reactnativenavigation.views.stack.topbar;

import android.content.Context;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.reactnativenavigation.R;
import com.reactnativenavigation.options.params.Text;

public class SearchBar extends EditText implements TextWatcher {


    public SearchBar(Context context) {
        super(context);
        setBackgroundResource(R.color.transparent);
        this.setFocusableInTouchMode(true);
        this.setFocusable(true);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //send submit action
                    hideKeyboard(context);
                    return true;
                }
                return false;
            }
        });

        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(context);
                }
            }
        });


    }

    public void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setKeyListener();
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setKeyListener();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent ev) {
        if (ev.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //this.onKeyboardDismissed();
        }

        return super.onKeyPreIme(keyCode, ev);
    }

    private void setKeyListener() {
        //setOnKeyListener(onKeyListener);
    }

    public void setFocus() {
        this.requestFocus();
    }

    public void setPlaceholder(Text placeholder) {
        if (placeholder.hasValue()) {
            this.setHint(placeholder.toString());
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        System.out.println("TEXT");
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
