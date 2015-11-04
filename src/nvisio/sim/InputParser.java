/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nvisio.sim;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author esinhep
 */
public class InputParser {
        // TODO move to other class or something
    static public void parseHistoryEntry(String line){
        if (line.startsWith("[")){
            parseTimestamp(line);
            parseEvent(line);
            parseSyncPortStatus(line);
        }
    }
    
    static private boolean parseTimestamp(String line){
        try{
            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
            LocalDateTime dateTime = LocalDateTime.parse(line.substring(1, line.indexOf("]")), formater);
            System.out.println(dateTime.toString());
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    static private boolean parseEvent(String line){
        try{
            System.out.println(getSubstring(line, "event = ", ", "));
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    static private boolean parseSyncPortStatus(String line){
        try{
            System.out.println(getSubstring(line, "syncPortStatus = [ ", " ],"));
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    static private String getSubstring(String input, String firstDelimiter, String lastDelimiter){
        int indexOfFirstDelimiter = input.indexOf(firstDelimiter);
        int indexOfSecondDelimiter = input.indexOf(lastDelimiter, indexOfFirstDelimiter);
        return input.substring(
                indexOfFirstDelimiter + firstDelimiter.length(), 
                indexOfSecondDelimiter);
    }
}
