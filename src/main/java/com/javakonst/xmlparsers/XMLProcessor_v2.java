package com.javakonst.xmlparsers;

import java.util.List;

public interface XMLProcessor_v2 {
    <T> List<T> dataFromXML(String filePath, T entity);
}
