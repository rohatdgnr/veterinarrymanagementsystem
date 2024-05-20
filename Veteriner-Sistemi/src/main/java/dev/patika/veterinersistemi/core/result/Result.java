package dev.patika.veterinersistemi.core.result;

import lombok.Getter;

@Getter
public class Result {
    private boolean sutatus;
    private String message;
    private String code;

    public Result(boolean sutatus, String message, String code) {
        this.sutatus = sutatus;
        this.message = message;
        this.code = code;
    }
}
