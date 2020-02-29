package com.input;

import java.io.IOException;

public interface Input<T> extends AutoCloseable{


    T read() throws IOException;



}
