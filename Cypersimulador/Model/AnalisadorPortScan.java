import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AnalisadorPortScan extends AnalisadorTrafego {
    private Map<String, Set<String>> portasVarridasPorIp;
    private static final int LIMITE_PORTAS = 10;

    public AnalisadorPortScan(SOCEngine engine, String caminhoArquivo) {
        super(engine, caminhoArquivo);
        this.portasVarridasPorIp = new HashMap<>();
    }

    @Override
    public void processarLinhaBruta(String linhaLog) {
        try {
            if (linhaLog == null || linhaLog.isBlank()) return;
            String[] campos = linhaLog.split(",");
            if (campos.length != 5) return;

            String ipOrigem = campos[1].trim();
            String ipDestino = campos[2].trim();
            String porta = campos[3].trim();

            portasVarridasPorIp.putIfAbsent(ipOrigem, new HashSet<>());
            Set<String> portas = portasVarridasPorIp.get(ipOrigem);
            portas.add(porta);

            if (portas.size() == LIMITE_PORTAS) {
                PortScan incidente = new PortScan(ipOrigem, ipDestino, portas.size());
                this.engine.receberIncidente(incidente);
                portas.clear(); // Reseta o rastreio após mitigação
            }
        } catch (Exception e) {
            // Ignora silenciosamente
        }
    }
}