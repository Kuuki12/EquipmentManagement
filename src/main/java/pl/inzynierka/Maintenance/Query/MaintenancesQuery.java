package pl.inzynierka.Maintenance.Query;

import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.User.Domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MaintenancesQuery {
    private Long id;
    private String name;
    private LocalDate dateScheduled;
    private LocalDateTime dateTaken;
    private LocalDateTime dateDone;
    private ProgressStatus progress;
    private User repairman;

    public MaintenancesQuery(Long id, String name, LocalDate dateScheduled, ProgressStatus progress) {
        this.id = id;
        this.name = name;
        this.dateScheduled = dateScheduled;
        this.progress = progress;
    }

    public MaintenancesQuery(Long id, String name, User repairman, LocalDate dateScheduled, LocalDateTime dateTaken, LocalDateTime dateDone, ProgressStatus progress) {
        this.id = id;
        this.name = name;
        this.repairman = repairman;
        this.dateScheduled = dateScheduled;
        this.dateTaken = dateTaken;
        this.dateDone = dateDone;
        this.progress = progress;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getRepairman() {
        return repairman;
    }

    public LocalDate getDateScheduled() {
        return dateScheduled;
    }

    public ProgressStatus getProgress() {
        return progress;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    public LocalDateTime getDateDone() {
        return dateDone;
    }
}


