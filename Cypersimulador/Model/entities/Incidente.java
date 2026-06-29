// ======================== Importação de bibliotecas e declaração do package =========================
package model.entities;

import model.interfaces.IAcaoDefensiva;
import model.interfaces.IRelatorioAuditavel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// ==================================== Declaração da classe e seus atributos =========================

public abstract class Incidente implements IAcaoDefensiva, IRelatorioAuditavel{
    private String id;
    private String ipOrigem;
    private String ipDestino;
    private LocalDateTime timestamp;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

// ============================ Validação dos IPs e inicialização dos atributos =======================

    public Incidente(String prefixoId, String ipOrigem, String ipDestino){
        if (prefixoId == null || prefixoId.trim().isEmpty())
            throw new IllegalArgumentException("Prefixo do ID nao pode ser vazio.");
        if (ipOrigem == null || ipOrigem.trim().isEmpty())
            throw new IllegalArgumentException("IP de origem invalido.");
        if (ipDestino == null || ipDestino.trim().isEmpty())
            throw new IllegalArgumentException("IP de destino invalido.");

        // Gera um ID dinamico e unico para o incidente, com prefixo por tipo
        this.id = prefixoId + "-" + UUID.randomUUID().toString().substring(0, 8);
        this.ipOrigem = ipOrigem;
        this.ipDestino = ipDestino;
        this.timestamp = LocalDateTime.now();
    }

// ============================================= Metodos Getters ======================================
    
    public String getId(){ 
        return id; 
    }

    public String getIpOrigem(){ 
        return ipOrigem; 
    }

    public String getIpDestino(){ 
        return ipDestino; 
    }

    public LocalDateTime getTimestamp(){ 
        return timestamp; 
    }

    public String getTimestampFormatado(){ 
        return timestamp.format(FORMATTER); 
    }

// ====================================== Utilização da IAcaoDefensiva =================================
    
    public abstract void executarMitigacao();
}
