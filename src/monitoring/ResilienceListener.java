package monitoring;

public interface ResilienceListener {
    void onFail(String taskName, int attempt, Exception e);
    void onSuccess(String taskName);
}