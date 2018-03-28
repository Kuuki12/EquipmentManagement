package pl.inzynierka.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User doen't exist")
public class UserDoesntExist extends RuntimeException{
}
