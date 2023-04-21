package com.example.ba2023.model;
import android.content.Context;

import com.example.ba2023.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigModel {
    public static String TIMER_WRITING = "timer.writing";
    public static String TIMER_PAUSE = "timer.pause";
    public static String TIMER_CYCLE = "timer.cycle";

    private static final String CONFIG_FILE_NAME = "config.conf";
    private Properties properties;
    private File configFile;

    public ConfigModel(Context context) {
        configFile = new File(context.getFilesDir(), CONFIG_FILE_NAME);
        if (!configFile.exists()) {
            try {
                InputStream inputStream = context.getResources().openRawResource(R.raw.config);
                OutputStream outputStream = new FileOutputStream(configFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(configFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(reader);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProperty(String key, String value) {
        // Update the property in the properties object
        properties.setProperty(key, value);

        // Save the updated properties to the config file
        try {
            OutputStream outputStream = new FileOutputStream(configFile);
            properties.store(outputStream, null);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
