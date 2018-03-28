package pl.inzynierka.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Report of maintenance doesn't exist")
public class ReportOfMaintenanceDoesntExist extends RuntimeException {
}
