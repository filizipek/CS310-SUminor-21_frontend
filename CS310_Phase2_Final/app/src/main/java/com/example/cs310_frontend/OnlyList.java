package com.example.cs310_frontend;

public class OnlyList {

    private String program, type, code;

    public OnlyList(String program, String type, String code) {
        this.type = type;
        this.program = program;
        this.code = code;

    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
