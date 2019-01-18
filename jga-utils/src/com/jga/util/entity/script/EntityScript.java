package com.jga.util.entity.script;

import com.jga.util.entity.EntityBase;

/**
 * Created by Nicolas Wiedel on 18.01.2019.
 */
public interface EntityScript<T extends EntityBase> {

    void added(T entity);

    void removed(T removed);

    void update(float delta);

    boolean isFinished();
}
