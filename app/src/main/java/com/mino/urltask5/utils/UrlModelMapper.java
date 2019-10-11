package com.mino.urltask5.utils;

import com.mino.urltask5.data.db.entity.UrlEntity;
import com.mino.urltask5.ui.main.viewmodel.UrlModel;

import java.util.ArrayList;
import java.util.List;

public abstract class UrlModelMapper {

    public static List<UrlModel> convert2UrlModel(final List<UrlEntity> urlEntities) {
        final List<UrlModel> urlModels = new ArrayList<>();
        for (UrlEntity  urlEntity : urlEntities) {
            urlModels.add(new UrlModel(urlEntity.getUrl(), urlEntity.getAvailability()));
        }
        return urlModels;
    }
}
