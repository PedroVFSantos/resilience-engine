package strategies;

import core.Task;
import core.Executor;
import core.ResilienceException;

public interface RetryStrategy {
    <T> T attempt(Task<T> task, Executor context) throws ResilienceException;
}