package com.mino.urltask5.ui.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.domain.UrlUseCase;

import java.util.List;

import javax.inject.Inject;

import static com.mino.urltask5.utils.Constants.URL_LOADING;

public class UrlViewModel extends ViewModel {

    public MutableLiveData<String> insertUrl = new MutableLiveData<>();

    @Inject
    UrlUseCase useCase;

    @Inject
    public UrlViewModel() {
    }

    public void insert() {
        useCase.insert(new UrlEntity(insertUrl.getValue(), URL_LOADING, 0));
    }

    public void update() {
//        useCase.update(url);
    }

    public void delete() {
//        urlRepository.delete(url);
    }

    public LiveData<List<UrlModel>> getUrlsOrderByUrl() {
        return useCase.getUrlsOrderByUrl();
    }
}
