package Model.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.entities.Incidente;

/**
 * Motor central do SOC (Security Operations Center).
 * Gerencia todos os incidentes detectados pelas threads de análise.
 * É thread-safe para receber incidentes de múltiplas threads simultaneamente.
 */
public class SOCEngine {
    private Map<String, List<Incidente>> incidentesAgrupados;

    /**
     * Inicializa o motor com o mapa de incidentes vazio.
     */
    public SOCEngine() {
        this.incidentesAgrupados = new HashMap<>();
    }

    /**
     * Método sincronizado para ser Thread-Safe e evitar corrupção de dados.
     * Recebe um incidente, agrupa por tipo e dispara a mitigação polimórfica.
     * 
     * @param i Incidente detectado
     */
    public synchronized void receberIncidente(Incidente i) {
        String tipo = i.getClass().getSimpleName();
        this.incidentesAgrupados.putIfAbsent(tipo, new ArrayList<>());
        this.incidentesAgrupados.get(tipo).add(i);
        
        i.executarMitigacao();
    }

    /**
     * Exibe resumo quantitativo das ações tomadas.
     */
    public void gerarAcoesTomadas() {
        System.out.println("\n--- Resumo de Ações Tomadas ---");
        for (String tipo : incidentesAgrupados.keySet()) {
            System.out.println("-> " + tipo + ": " + 
                    incidentesAgrupados.get(tipo).size() + " ameaças neutralizadas.");
        }
    }

    /**
     * Gera relatório completo de auditoria e salva em arquivo.
     * Utiliza o polimorfismo para chamar gerarLinhaAuditoria() de cada incidente.
     */
    public void gerarRelatorioFinal() {
        System.out.println("\n--- Exportando Relatório de Auditoria ---");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("relatorio_soc.txt"))) {
            for (List<Incidente> lista : incidentesAgrupados.values()) {
                for (Incidente i : lista) {
                    String linhaLog = i.gerarLinhaAuditoria();
                    bw.write(linhaLog);
                    bw.newLine();
                    System.out.println(linhaLog);
                }
            }
            System.out.println("\n[SISTEMA] Relatório salvo com sucesso em 'relatorio_soc.txt'.");
        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo de relatório: " + e.getMessage());
        }
    }

    /**
     * Verifica se já existem incidentes registrados.
     * 
     * @return true se houver pelo menos um incidente
     */
    public boolean temIncidentes() {
        return !incidentesAgrupados.isEmpty();
    }
    
    /**
     * Retorna o mapa de incidentes (útil para testes).
     * 
     * @return Map com incidentes agrupados por tipo
     */
    public Map<String, List<Incidente>> getIncidentesAgrupados() {
        return incidentesAgrupados;
    }
}
