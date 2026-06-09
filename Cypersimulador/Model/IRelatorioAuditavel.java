package model;

public interface IRelatorioAuditavel {
    String gerarRegistroAuditoria();
    String gerarRegistroAuditoria(double risco);
}
