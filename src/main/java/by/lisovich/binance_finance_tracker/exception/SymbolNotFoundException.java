package by.lisovich.binance_finance_tracker.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND) Ця аннотація позволяє встановити статус відповіді, но без повідомлення
public class SymbolNotFoundException extends RuntimeException{
    public SymbolNotFoundException(String message) {
        super(message);
    }
}
