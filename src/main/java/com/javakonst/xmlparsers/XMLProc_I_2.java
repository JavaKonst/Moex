package com.javakonst.xmlparsers;

import java.util.List;

public interface XMLProc_I_2 {
    <T> List<T> dataFromXML(String filePath, T entity);
}
