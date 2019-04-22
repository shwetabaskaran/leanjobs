package com.example.leanjobs;

import org.json.JSONArray;

import java.util.ArrayList;

public interface AsyncResponse {
    void processFinish(ArrayList<Job> output);
}