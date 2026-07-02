package Model.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.exceptions.IpInvalidoException;

/**
 * Testes unitários para validação de incidentes e IPs.
 */
public class IncidenteTest {

    @Test
    public void deveCriarForcaBrutaComIPsValidos() throws IpInvalidoException {
        ForcaBruta fb = new ForcaBruta("192.168.0.1", "10.0.0.5");
        assertNotNull(fb.getId());
        assertEquals("192.168.0.1", fb.getIpOrigem());
        assertEquals("10.0.0.5", fb.getIpDestino());
        assertEquals(5, fb.getTentativasFalhas());
    }
  @Test
    public void deveLancarExcecaoParaIPOrigemInvalido() {
        assertThrows(IpInvalidoException.class, () -> {
            new ForcaBruta("999.999.999.999", "10.0.0.1");
        });
    }
    @Test
    public void deveLancarExcecaoParaIPDestinoInvalido() {
        assertThrows(IpInvalidoException.class, () -> {
            new PortScan("192.168.1.1", "abc.def.ghi.jkl", 100);
        });
    }
      @Test
    public void deveLancarExcecaoParaIPNulo() {
        assertThrows(IpInvalidoException.class, () -> {
            new ForcaBruta(null, "10.0.0.1");
        });
    }

    @Test
    public void deveCriarPortScanComIPsValidos() throws IpInvalidoException {
        PortScan ps = new PortScan("172.16.0.1", "192.168.0.200", 1024);
        assertNotNull(ps.getId());
        assertEquals(1024, ps.getPortasVarridas());
    }
