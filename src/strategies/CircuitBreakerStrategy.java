package strategies;

import core.Task;
import core.Executor;
import core.ResilienceException;

public class CircuitBreakerStrategy implements RetryStrategy {
    
    private enum State { CLOSED, OPEN, HALF_OPEN }

    private int failureThreshold;
    private long recoveryTimeout; // em milissegundos
    
    private State state = State.CLOSED;
    private int failureCount = 0;
    private long lastFailureTime = 0;

    public CircuitBreakerStrategy(int failureThreshold, long recoveryTimeoutMs) {
        this.failureThreshold = failureThreshold;
        this.recoveryTimeout = recoveryTimeoutMs;
    }

    @Override
    public <T> T attempt(Task<T> task, Executor context) throws ResilienceException {
        updateState();

        if (state == State.OPEN) {
            throw new ResilienceException("Circuito ABERTO para '" + task.getName() + "'. Requisicao bloqueada por seguranca.");
        }

        try {
            T result = task.execute();
            onSuccess(context, task.getName());
            return result;
        } catch (ResilienceException e) {
            onFailure(context, task.getName(), e);
            throw e;
        }
    }

    private void updateState() {
        if (state == State.OPEN) {
            long timeSinceLastFailure = System.currentTimeMillis() - lastFailureTime;
            if (timeSinceLastFailure > recoveryTimeout) {
                state = State.HALF_OPEN;
                System.out.println(">>> Circuito em MEIO-ABERTO: Testando recuperacao...");
            }
        }
    }

    private void onSuccess(Executor context, String taskName) {
        failureCount = 0;
        state = State.CLOSED;
        context.notifySuccess(taskName);
    }

    private void onFailure(Executor context, String taskName, Exception e) {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
        context.notifyFail(taskName, failureCount, e);

        if (failureCount >= failureThreshold) {
            state = State.OPEN;
            System.err.println("!!! CIRCUITO ABERTO: Limite de falhas atingido (" + failureThreshold + ")");
        }
    }
}