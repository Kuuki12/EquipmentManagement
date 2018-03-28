package pl.inzynierka.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Client doesn't exist")
public class ClientDoesntExist extends RuntimeException {
}
