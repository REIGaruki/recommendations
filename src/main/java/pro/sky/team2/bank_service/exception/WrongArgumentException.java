package pro.sky.team2.bank_service.exception;

public class WrongArgumentException extends RuntimeException{
    public WrongArgumentException(String message) {
        super(message);
    }
}
