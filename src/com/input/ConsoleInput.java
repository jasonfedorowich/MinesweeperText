package com.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput implements Input<String> {

    private BufferedReader bufferedReader;

    public ConsoleInput(){
        this.bufferedReader =  new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String read() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
