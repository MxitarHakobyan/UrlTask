package com.mino.urltask5.domain;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

abstract class BaseUseCase {

    void execute(final CompletableObserver observer,
                 final CompletableBody completableBody) {

        Completable.fromAction(completableBody::body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public interface CompletableBody {
        void body();
    }
}
