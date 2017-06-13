package goodyapp.example.com.goodyapp.presentation.views.listeners;

import android.view.View;

public abstract class BasketMenuItemStuffListener implements View.OnClickListener{
    private View view;

    public BasketMenuItemStuffListener(View view) {
        this.view = view;
        view.setOnClickListener(this);
    }

    @Override abstract public void onClick(View v);

}