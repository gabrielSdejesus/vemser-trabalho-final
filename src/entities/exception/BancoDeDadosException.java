package entities.exception;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException{

    public BancoDeDadosException(Throwable cause) {
            super(cause);
        }
}
