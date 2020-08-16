package com.javakonst.core;

import com.javakonst.entity.Entity;

import java.util.List;

public interface XMLProcessor {
    List<? extends Entity> dataFromXml(Entity entity, String file);
}
