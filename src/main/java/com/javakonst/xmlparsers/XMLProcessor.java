package com.javakonst.xmlparsers;

import java.util.List;

public interface XMLProcessor {
    <T> List<T> dataFromXML(String filePath, T entity);
}
