package com.mino.urltask5.ui.main.adaptors;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mino.urltask5.databinding.UrlItemBinding;
import com.mino.urltask5.di.main.PerMainActivity;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerMainActivity
public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.UrlViewHolder> {

    private UrlDiffCallBack diffCallBack;
    private List<UrlModel> urlModels = new ArrayList<>();

    @Inject
    public UrlAdapter(final UrlDiffCallBack diffCallBack) {
        this.diffCallBack = diffCallBack;
    }

    @NonNull
    @Override
    public UrlViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                            final int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UrlItemBinding binding = UrlItemBinding.inflate(layoutInflater, parent, false);
        return new UrlViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UrlViewHolder holder, int position) {
        holder.bind(urlModels.get(position));
    }

    @Override
    public int getItemCount() {
        return urlModels.size();
    }

    public void updateUrlsList(final List<UrlModel> urls) {
        diffCallBack.setItems(urls, urlModels);
        DiffUtil.DiffResult diffResult =DiffUtil.calculateDiff(diffCallBack);
        urlModels.clear();
        urlModels.addAll(urls);
        diffResult.dispatchUpdatesTo(UrlAdapter.this);
    }

    static class UrlViewHolder extends RecyclerView.ViewHolder {

        UrlItemBinding binding;

        UrlViewHolder(@NonNull final UrlItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final UrlModel urlModel) {
            binding.setModel(urlModel);
        }
    }
}
