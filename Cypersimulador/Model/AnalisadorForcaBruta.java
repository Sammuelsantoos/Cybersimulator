import java.util.HashMap;
import java.util.Map;

public class AnalisadorForcaBruta extends AnalisadorTrafego {
    private Map<String, Integer> falhasPorIp;
    private static final int LIMITE = 5;

    public AnalisadorForcaBruta(SOCEngine engine, String caminhoArquivo) {
        super(engine, caminhoArquivo);
        this.falhasPorIp = new HashMap<>();
    }

    @Override
    public void processarLinhaBruta(String linhaLog) {
        try {
            if (linhaLog == null || linhaLog.isBlank()) return;
            String[] campos = linhaLog.split(",");
            if (campos.length != 5) return;

            String ipOrigem = campos[1].trim();
            String ipDestino = campos[2].trim();
            String acao = campos[4].trim();

            if (acao.equalsIgnoreCase("FALHA_LOGIN")) {
                int tentativas = falhasPorIp.getOrDefault(ipOrigem, 0) + 1;
                falhasPorIp.put(ipOrigem, tentativas);

                if (tentativas == LIMITE) {
                    ForcaBruta incidente = new ForcaBruta(ipOrigem, ipDestino);
                    this.engine.receberIncidente(incidente);
                    falhasPorIp.put(ipOrigem, 0); // Reseta para buscar novos ataques do mesmo IP
                }
            }
        } catch (Exception e) {
            // Ignora a linha quebrada silenciosamente e segue para a próxima
        }
    }
}