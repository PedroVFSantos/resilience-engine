package strategies;

import core.Task;
import core.Executor;
import core.ResilienceException;

public class FixedIntervalRetry implements RetryStrategy {
    private int limit;

    public FixedIntervalRetry(int limit) {
        this.limit = limit;
    }

    @Override
    public <T> T attempt(Task<T> task, Executor context) throws ResilienceException {
        int count = 0;
        ResilienceException lastError = null;

        while (count < limit) {
            try {
                T result = task.execute();
                context.notifySuccess(task.getName());
                return result;
            } catch (ResilienceException e) {
                count++;
                lastError = e;
                context.notifyFail(task.getName(), count, e);
            }
        }
        throw new ResilienceException("Falha total apos " + limit + " tentativas. Ultimo erro: " + lastError.getMessage());
    }
}