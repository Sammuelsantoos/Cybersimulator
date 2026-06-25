// ======================== Importação de bibliotecas e declaração do package =========================

package model;

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

    public Incidente(String id, String ipOrigem, String ipDestino){
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("O ID nao pode estar vazio. Tente novamente");
        if (ipOrigem == null || ipOrigem.isBlank())
            throw new IllegalArgumentException("IP de origem invalido.");
        if (ipDestino == null || ipDestino.isBlank())
            throw new IllegalArgumentException("IP de destino invalido.");

        this.id = UUID.randomUUID().toString().substring(0, 8);
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
