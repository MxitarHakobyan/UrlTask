package com.mino.urltask5.ui.main.adaptors;

import androidx.recyclerview.widget.DiffUtil;

import com.mino.urltask5.di.main.PerMainActivity;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerMainActivity
public class UrlDiffCallBack extends DiffUtil.Callback {

    private List<UrlModel> oldItemsList = new ArrayList<>();
    private List<UrlModel> newItemsList = new ArrayList<>();

    @Inject
    public UrlDiffCallBack() {
    }

    public void setItems(final List<UrlModel> oldItemsList,
                         final List<UrlModel> newItemsList) {

        this.oldItemsList = oldItemsList;
        this.newItemsList = newItemsList;
    }

    @Override
    public int getOldListSize() {
        return oldItemsList.size();
    }

    @Override
    public int getNewListSize() {
        return newItemsList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemsList.get(oldItemPosition).getUrl().equals(newItemsList.get(newItemPosition).getUrl());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemsList.get(oldItemPosition).equals(newItemsList.get(newItemPosition));
    }
}