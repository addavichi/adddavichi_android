package com.example.davichiar.addavichi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterCheck extends StringRequest {

    final static private String URL = "http://davichiar1.cafe24.com/UserCheck.php";
    private Map<String, String> parameters;

    public RegisterCheck(String userID, String userCheck, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();

        parameters.put("userID", userID);
        parameters.put("userCheck", userCheck);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
