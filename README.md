# Cybersimulator
Simulador de um Centro de Operações de Segurança(SOC) projetado para ingerir, processar e mitigar eventos de rede em tempo real


**Para compilar o programa**

javac -d bin -sourcepath src/main src/main/Main.java src/main/Model/*/*.java

**Para compilar os testes**

javac -d bin -cp "lib/junit-platform-console-standalone.jar:bin" src/test/Model/entities/IncidenteTest.java

**Para executar os teste** 

java -jar lib/junit-platform-console-standalone.jar --class-path bin --scan-class-path

**Para executar o programa**

java -cp bin Main

**Para gerar o JavaDoc**

javadoc -d docs -sourcepath src/main -subpackages Model src/main/Main.java -encoding UTF-8 -charset UTF-8

**Diagrama**

classDiagram
    class IAcaoDefensiva {
        <<interface>>
        +executarMitigacao() void
    }

    class IRelatorioAuditavel {
        <<interface>>
        +gerarLinhaAuditoria() String
    }

    class Incidente {
        <<abstract>>
        -String id
        -String ipOrigem
        -String ipDestino
        -LocalDateTime timestamp
        +Incidente(ipOrigem, ipDestino)
        +getId() String
        +getIpOrigem() String
        +getIpDestino() String
        +getTimestamp() LocalDateTime
        +getTimestampFormatado() String
        #validarIp(ip) void
        +executarMitigacao()* void
        +gerarLinhaAuditoria()* String
    }

    class ForcaBruta {
        -int tentativasFalhas
        +ForcaBruta(ipOrigem, ipDestino)
        +getTentativasFalhas() int
        +executarMitigacao() void
        +gerarLinhaAuditoria() String
    }

    class PortScan {
        -int portasVarridas
        +PortScan(ipOrigem, ipDestino, portasVarridas)
        +getPortasVarridas() int
        +executarMitigacao() void
        +gerarLinhaAuditoria() String
    }

    class IpInvalidoException {
        <<exception>>
        +IpInvalidoException(mensagem)
    }

    class SeveridadeInvalidaException {
        <<exception>>
        +SeveridadeInvalidaException(mensagem)
    }

    class SOCEngine {
        -Map~String, List~Incidente~~ incidentesAgrupados
        +SOCEngine()
        +receberIncidente(i) void
        +gerarAcoesTomadas() void
        +gerarRelatorioFinal() void
        +temIncidentes() boolean
        +getIncidentesAgrupados() Map
    }

    class AnalisadorTrafego {
        <<abstract>>
        #SOCEngine engine
        #String caminhoArquivo
        +AnalisadorTrafego(engine, caminhoArquivo)
        +processarLinhaBruta(linhaLog)* void
        +run() void
    }

    class AnalisadorForcaBruta {
        -Map~String, Integer~ falhasPorIp
        -int LIMITE
        +AnalisadorForcaBruta(engine, caminhoArquivo)
        +processarLinhaBruta(linhaLog) void
    }

    class AnalisadorPortScan {
        -Map~String, Set~String~~ portasVarridasPorIp
        -int LIMITE_PORTAS
        +AnalisadorPortScan(engine, caminhoArquivo)
        +processarLinhaBruta(linhaLog) void
    }

    class Main {
        +main(args) void
    }

    Incidente ..|> IAcaoDefensiva : implementa
    Incidente ..|> IRelatorioAuditavel : implementa
    Incidente <|-- ForcaBruta : herda
    Incidente <|-- PortScan : herda
    Incidente ..> IpInvalidoException : lança
    AnalisadorTrafego <|-- AnalisadorForcaBruta : herda
    AnalisadorTrafego <|-- AnalisadorPortScan : herda
    AnalisadorTrafego --> SOCEngine : usa
    AnalisadorTrafego ..|> Runnable : implementa
    SOCEngine o-- Incidente : gerencia
    Main --> SOCEngine : cria
    Main --> AnalisadorForcaBruta : cria
    Main --> AnalisadorPortScan : cria