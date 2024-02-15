package com.example.tourmanagement.listeners;

import com.example.tourmanagement.model.entity.base.IdHolder;
import com.example.tourmanagement.model.entity.base.ImagesSetter;
import com.example.tourmanagement.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadEventListener implements PostLoadEventListener {

    private final FileUtils fileUtils;

    @Override
    public void onPostLoad(PostLoadEvent postLoadEvent) {

        var entity = postLoadEvent.getEntity();

        if (entity instanceof ImagesSetter imagesSetterEntity && entity instanceof IdHolder idHolderEntity) {
            imagesSetterEntity.setImages(fileUtils.getImageNames(
                    imagesSetterEntity.getClass(),
                    idHolderEntity.getId()));
        }

    }
}