package com.example.davichiar.addavichi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class MainDestroy extends StringRequest {
    final static private String URL = "http://davichiar1.cafe24.com/Delete.php";
    private Map<String, String> parameters;

    public MainDestroy(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }
}