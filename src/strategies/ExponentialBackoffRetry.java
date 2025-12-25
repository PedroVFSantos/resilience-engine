package strategies;

import core.Task;
import core.Executor;
import core.ResilienceException;

public class ExponentialBackoffRetry implements RetryStrategy {
    @Override
    public <T> T attempt(Task<T> task, Executor context) throws ResilienceException {
        int count = 0;
        ResilienceException lastError = null;

        while (count < 3) {
            try {
                T result = task.execute();
                context.notifySuccess(task.getName());
                return result;
            } catch (ResilienceException e) {
                count++;
                lastError = e;
                context.notifyFail(task.getName(), count, e);
                
                // Cálculo do tempo de espera exponencial: 2^count (2s, 4s, 8s...)
                long wait = (long) Math.pow(2, count);
                try { 
                    Thread.sleep(wait * 1000); 
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new ResilienceException("Falha total após backoff. Último erro: " + lastError.getMessage());
    }
}