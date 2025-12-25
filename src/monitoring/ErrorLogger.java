package monitoring;

public class ErrorLogger implements ResilienceListener {
    @Override
    public void onFail(String taskName, int attempt, Exception e) {
        System.err.println("[LOG] Falha em " + taskName + " (Tenta: " + attempt + "): " + e.getMessage());
    }

    @Override
    public void onSuccess(String taskName) {
        System.out.println("[LOG] Sucesso em: " + taskName);
    }
}