/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.sim;

import java.time.LocalDateTime;
import nvisio.unit.DigitalUnit;

/**
 *
 * @author esinhep
 */
public class SocpriEntry {
    LocalDateTime timestamp;
    DigitalUnit du;
    String event;
    int socpryPortStatus[];
    int inciRemoteStatus[];
    
    public SocpriEntry(LocalDateTime time, DigitalUnit du, String event){
        this.timestamp = time;
        this.du = du;
        this.event = event;
        this.socpryPortStatus = new int[du.getPorts().size()];
        this.inciRemoteStatus = new int[du.getPorts().size()];
    }
}
