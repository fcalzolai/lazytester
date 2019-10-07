package com.lazytester.report;

import com.lazytester.runner.FeatureResult;
import com.lazytester.validator.ValidatedAssertions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.function.Consumer;

public class CsvReport implements Consumer<FeatureResult> {

    private final RandomAccessFile stream;

    public CsvReport(File file) throws FileNotFoundException {
        stream = new RandomAccessFile(file, "rw");
    }

    @Override
    public void accept(FeatureResult results) {
        FileChannel channel = stream.getChannel();

        results.rowKeySet().forEach(row ->
                results.row(row).forEach((column, value)
                        -> writeLineToFile(channel, row, column, value)));

        RuntimeException e = null;
        try {
            stream.close();
        } catch (IOException ex) {
            e = new RuntimeException(ex);
        }

        try {
            channel.close();
        } catch (IOException ex) {
            e = new RuntimeException(ex);
        }

        if(e != null) {
            throw e;
        }
    }

    private void writeLineToFile(FileChannel channel, Integer row, Integer column, ValidatedAssertions value) {
        String csv = convertToCsv(row, column, value);
        byte[] strBytes = csv.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        try {
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertToCsv(Integer row, Integer column, ValidatedAssertions value) {
        return row + "," + column + ","
                + "\"" + value.isValid() + "\""
                + "\"" + value.getError() + "\""
                + "\"" + value.getExecutionTime() + "\""
                + "\n";
    }
}
