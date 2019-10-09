package com.mino.urltask5.ui.main.adaptors;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mino.urltask5.databinding.UrlItemBinding;
import com.mino.urltask5.di.main.PerMainActivity;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

@PerMainActivity
public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.UrlViewHolder> {

    private UrlDiffCallBack diffCallBack;
    private List<UrlModel> urlModels = new ArrayList<>();
    private List<UrlModel> fullUrlModels = new ArrayList<>();

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

    public Filter getFilter() {
        return urlFilter;
    }

    private Filter urlFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UrlModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullUrlModels);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (UrlModel item : urlModels) {
                    if (Objects.requireNonNull(item.getUrl().get()).toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            updateUrlsList((List<UrlModel>) results.values);
        }
    };

    public void setFullUrlModels(List<UrlModel> fullUrlModels) {
        this.fullUrlModels.clear();
        this.fullUrlModels.addAll(fullUrlModels);
        updateUrlsList(fullUrlModels);
    }

    private void updateUrlsList(final List<UrlModel> urls) {
        diffCallBack.setItems(urlModels, urls);
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
