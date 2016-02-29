package com.portea.commp.web.rapi.domain;

import com.portea.commp.web.rapi.exception.IncompleteRequestException;
import com.portea.commp.web.rapi.exception.InvalidRequestException;

import javax.ws.rs.BadRequestException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsSenderCreateReq {

    private String name;
    private String description;
    private String password;
    private String email;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void validate() {
        if (name == null) {
            throw new IncompleteRequestException("name");
        }
        else if (password == null) {
            throw new IncompleteRequestException("password");
        }
        else if (email == null) {
            throw new IncompleteRequestException("email");
        }
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        boolean hasSpecialChar = p.matcher(name).find();
        if (hasSpecialChar) {
            throw new InvalidRequestException("name", name);
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (! matcher.matches()) {
            throw new InvalidRequestException("email", email);
        }
    }
}
