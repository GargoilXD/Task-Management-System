package utils;

import models.Task;

public class OperationResult {
    public enum STATUS {
        SUCCESS,
        FAILURE,
        PENDING,
    }
    public String message;
    public STATUS status;
    //public T data;
    public OperationResult(STATUS status, String message) {
        this.message = message;
        //this.data = data;
        this.status = status;
    }
    //public OperationResult(STATUS status, String message) {
    //    this.message = message;
    //    this.status = status;
    //}
    //public OperationResult(STATUS status, T data) {
    //    this.status = status;
    //    this.data = data;
    //}
    public OperationResult(STATUS status) {
        this.status = status;
    }
    /*
    public static OperationResult<Task.T> success() {
        return new OperationResult<Task.T>(STATUS.SUCCESS);
    }
    public static OperationResult<Object> success(Object data) {
        return new OperationResult<Object>(STATUS.SUCCESS, data);
    }
    public static OperationResult<Task.T> failure(String message) {
        return new OperationResult<Task.T>(STATUS.FAILURE, null, message);
    }
    */
}
