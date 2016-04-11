package excecoes;


public class SisEventoException extends Exception {

    public SisEventoException() {
    }

    public SisEventoException(String message) {
        super(message);

    }

    public SisEventoException(String message, Throwable cause) {
        super(message, cause);

    }

    public SisEventoException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
        super(message, cause, enableSupression, writableStackTrace);

    }

}
