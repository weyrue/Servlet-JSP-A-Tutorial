package app10d.dao;

public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
