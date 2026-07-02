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