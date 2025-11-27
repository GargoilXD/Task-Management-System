package interfaces;

public interface Completable {
    enum STATUS {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
    STATUS Status = STATUS.PENDING;
}
