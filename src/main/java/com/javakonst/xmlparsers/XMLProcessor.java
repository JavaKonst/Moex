package com.javakonst.xmlparsers;

import com.javakonst.entity.Entity;

import java.util.List;

public interface XMLProcessor<T extends Entity> {
    List<T> dataFromXml(String file);
}
