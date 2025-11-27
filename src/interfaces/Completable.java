package interfaces;

public interface Completable {
    enum STATUS {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
    STATUS status = STATUS.PENDING;
}
