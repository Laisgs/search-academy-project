package co.empathy.academy.search.exceptions;

public class BulkIndexException extends RuntimeException{
    public BulkIndexException(String msg){
        super(msg);
    }
}
