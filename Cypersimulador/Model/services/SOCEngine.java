import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SOCEngine {
    private Map<String, List<Incidente>> incidentesAgrupados;

    public SOCEngine() {
        this.incidentesAgrupados = new HashMap<>();
    }

    // Método sincronizado para ser Thread-Safe e evitar corrupção de dados
    public synchronized void receberIncidente(Incidente i) {
        String tipo = i.getClass().getSimpleName();
        this.incidentesAgrupados.putIfAbsent(tipo, new ArrayList<>());
        this.incidentesAgrupados.get(tipo).add(i);
        
        // Dispara a defesa autônoma e polimórfica no exato momento da detecção
        i.executarMitigacao();
    }

    public void gerarAcoesTomadas() {
        System.out.println("\n--- Resumo de Ações Tomadas ---");
        for (String tipo : incidentesAgrupados.keySet()) {
            System.out.println("-> " + tipo + ": " + incidentesAgrupados.get(tipo).size() + " ameaças neutralizadas.");
        }
    }

    public void gerarRelatorioFinal() {
        System.out.println("\n--- Exportando Relatório de Auditoria ---");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("relatorio_soc.txt"))) {
            for (List<Incidente> lista : incidentesAgrupados.values()) {
                for (Incidente i : lista) {
                    String linhaLog = i.gerarLinhaAuditoria();
                    bw.write(linhaLog);
                    bw.newLine();
                    System.out.println(linhaLog); // Imprime na tela do terminal também
                }
            }
            System.out.println("\n[SISTEMA] Relatório salvo com sucesso em 'relatorio_soc.txt'.");
        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo de relatório: " + e.getMessage());
        }
    }

    public boolean temIncidentes() {
        return !incidentesAgrupados.isEmpty();
    }
}