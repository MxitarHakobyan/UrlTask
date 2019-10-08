package com.mino.urltask5.ui.common.binding;

import com.mino.urltask5.di.main.PerMainActivity;
import com.mino.urltask5.ui.main.viewmodel.UrlViewModel;

import javax.inject.Inject;

@PerMainActivity
public class ClickHandler {

    @Inject
    public ClickHandler() {
    }

    public void addClick(final UrlViewModel urlViewModel) {
        urlViewModel.insert();
    }
}
