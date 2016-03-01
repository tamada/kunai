package com.github.kunai.sink;

/**
 * 
 * @author Haruaki Tamada
 */
public class TargetExistsException extends SinkException {
    private static final long serialVersionUID = 906476014309175700L;

    public TargetExistsException(String message){
        super(message);
    }
}
