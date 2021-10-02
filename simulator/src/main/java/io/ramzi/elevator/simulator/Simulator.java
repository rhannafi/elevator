package io.ramzi.elevator.simulator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** Represents an employee.
 * @author Doug Lowe
 * @author www.LoweWriter.com
 * @version 1.5
 * @since 1.0
*/
public class Simulator {

    /**
     * Résumé du rôle de la méthode.
    * Commentaires détaillés sur le role de la methode
    * @param args la valeur a traiter
    * @since 1.0
    * @deprecated Utiliser la nouvelle methode xyz
    */
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader reader = new FileReader("/mnt/vm_shared/TPs/elevator/simulator/request_sequence.json");
            Object object = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;
            JSONArray callsArray = (JSONArray) jsonObject.get("calls");
            Iterator<JSONObject> iterator = callsArray.iterator();

            Long oldTimeStamp = 0L;
            Long newTimeStamp = 0L;
            Long period = 0L;

            Timestamp ts = Timestamp.from(Instant.now());
            long tsStart = ts.getTime();
            int callIndex = 0;
            while (iterator.hasNext()) {
                newTimeStamp = (Long) (iterator.next().get("timestamp"));
                period = newTimeStamp.longValue() - oldTimeStamp.longValue();
                ts = Timestamp.from(Instant.now());

                System.out.printf("sleep during %d ms\n", period);
                try {
                    TimeUnit.MILLISECONDS.sleep(period);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                oldTimeStamp = Long.valueOf(newTimeStamp);
                callIndex++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
