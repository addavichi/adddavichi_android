package com.example.davichiar.addavichi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainRequest extends StringRequest
{
    final static private String URL = "http://davichiar1.cafe24.com/Search.php";
    private Map<String, String> parameters;

    public MainRequest(String name, String nameCheck, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();

        parameters.put("name", name);
        parameters.put("nameCheck", nameCheck);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}