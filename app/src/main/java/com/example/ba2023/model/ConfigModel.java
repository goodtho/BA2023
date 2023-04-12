package com.example.ba2023.model;

import android.content.Context;
import android.content.res.Resources;

import com.example.ba2023.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigModel {
    private static final String CONFIG_FILE_NAME = "config.conf";

    private Properties properties;
    private File configFile;

    public ConfigModel(Context context) {
        // Load the properties from the config file
        configFile = new File(context.getFilesDir(), CONFIG_FILE_NAME);
        properties = new Properties();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.config);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(reader);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProperty(String key, String value) {
        // Update the property
        properties.setProperty(key, value);

        // Save the updated properties to the config file
        try {
            FileOutputStream outputStream = new FileOutputStream(configFile);
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