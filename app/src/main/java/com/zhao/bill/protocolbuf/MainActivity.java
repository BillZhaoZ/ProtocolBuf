package com.zhao.bill.protocolbuf;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import net.protocol.bean.BookOuterClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private static final String TAG = "MainActivity";
    private static final String SRC_DIR = "book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textTest);

        BookOuterClass.Book book = BookOuterClass.Book.newBuilder()
                .setId(1)
                .setName("一江夜雨")
                .setDesc("Read the fuck code!!!")
                .build();

        save(book);
        read();
    }

    /**
     * 保存数据
     *
     * @param book
     */
    private void save(BookOuterClass.Book book) {
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, SRC_DIR);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            book.writeTo(outputStream);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 读取数据
     */
    private void read() {
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, SRC_DIR);

        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] data = new byte[1024];

            int len = -1;
            while ((len = inputStream.read(data)) != -1) {
                out.write(data, 0, len);
                out.flush();
            }

            BookOuterClass.Book book = BookOuterClass.Book.parseFrom(out.toByteArray());
            out.close();

            textView.setText("name: " + book.getName() + " ,desc: " + book.getDesc());

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
