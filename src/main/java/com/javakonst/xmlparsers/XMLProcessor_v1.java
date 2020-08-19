package com.javakonst.xmlparsers;

import com.javakonst.entity.Entity;

import java.util.List;

public interface XMLProcessor_v1<T extends Entity> {
    List<T> dataFromXml(String file);
}
