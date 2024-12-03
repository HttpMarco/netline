package dev.httpmarco.netline.excpetions;

public class SimpleResponderTypeException extends Exception{

    public SimpleResponderTypeException(Object value) {
        super("Cannot translate value of type " + value.getClass().getSimpleName() + " to a simple responder type");
    }
}
