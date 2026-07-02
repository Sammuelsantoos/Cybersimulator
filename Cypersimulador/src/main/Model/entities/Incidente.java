package Model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import Model.exceptions.IpInvalidoException;
import Model.interfaces.IAcaoDefensiva;
import Model.interfaces.IRelatorioAuditavel;

/**
 * Classe abstrata que representa qualquer tipo de incidente de segurança.
 * Implementa as interfaces de ação defensiva e auditoria.
 */
public abstract class Incidente implements IAcaoDefensiva, IRelatorioAuditavel {
    private String id;
    private String ipOrigem;
    private String ipDestino;
    private LocalDateTime timestamp;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Construtor que valida IPs e gera ID único.
     * 
     * @param ipOrigem IP de onde partiu a ameaça
     * @param ipDestino IP alvo da ameaça
     * @throws IpInvalidoException se algum IP for inválido
     */
    public Incidente(String ipOrigem, String ipDestino) throws IpInvalidoException {
        validarIp(ipOrigem);
        validarIp(ipDestino);
        
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.ipOrigem = ipOrigem;
        this.ipDestino = ipDestino;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Valida se o IP segue o padrão xxx.xxx.xxx.xxx com octetos entre 0-255.
     * 
     * @param ip Endereço IP a ser validado
     * @throws IpInvalidoException se o IP for nulo, vazio ou inválido
     */
    private void validarIp(String ip) throws IpInvalidoException {
        if (ip == null || ip.isBlank()) {
            throw new IpInvalidoException("IP não pode ser nulo ou vazio.");
        }
        
        String regex = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}" +
                       "(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
        
        if (!ip.matches(regex)) {
            throw new IpInvalidoException("IP inválido: " + ip + ". Deve seguir o formato xxx.xxx.xxx.xxx");
        }
    }

    public String getId() { 
        return id; 
    }
    
    public String getIpOrigem() { 
        return ipOrigem; 
    }
    
    public String getIpDestino() { 
        return ipDestino; 
    }
    
    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }

    /**
     * Retorna o timestamp formatado para exibição.
     * 
     * @return String no formato dd/MM/yyyy HH:mm:ss
     */
    public String getTimestampFormatado() {
        return timestamp.format(FORMATTER);
    }
}
