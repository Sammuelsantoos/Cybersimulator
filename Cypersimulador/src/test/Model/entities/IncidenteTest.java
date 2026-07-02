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
