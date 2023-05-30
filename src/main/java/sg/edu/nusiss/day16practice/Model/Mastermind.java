package sg.edu.nusiss.day16practice.Model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Mastermind implements Serializable{
    
    private String name;
    private Pieces pieces;
    private String id;
    private int insertCount;
    private int updateCount;
    private boolean isUpsert;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Pieces getPieces() {
        return pieces;
    }
    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getInsertCount() {
        return insertCount;
    }
    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }
    public int getUpdateCount() {
        return updateCount;
    }
    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }
    public boolean isIsUpsert() {
        return isUpsert;
    }
    public void setUsUpsert(boolean isUpsert) {
        this.isUpsert = isUpsert;
    }

    public Mastermind() {
    }

    public synchronized String generateID1(int limit) {

        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i =0; i < limit; i++) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, limit);
    }

    public synchronized String generateID2(int maxChar) {
        //securerandom less likely to have repeats due to larger bit range
        SecureRandom sr = new SecureRandom();
        StringBuilder strbuilder = new StringBuilder();
        while(strbuilder.length() < maxChar) {
            strbuilder.append(Integer.toHexString(sr.nextInt()));
        }

        return strbuilder.toString().substring(0, maxChar);
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                .add("name", this.getName())
                //since pieces is a complex object, must call toJSON so that it can be added
                .add("pieces", this.getPieces().toJSON())
                .add("id", this.getId())
                .build();
    }

    public JsonObject toJSONInsert(){
        return Json.createObjectBuilder()
                .add("insert_count", this.getInsertCount())
                .add("id", this.getId())
                .build();
    }

    public JsonObject toJSONUpdate(){
        return Json.createObjectBuilder()
                .add("update_count", this.getUpdateCount())
                .add("id", this.getId())
                .build();
    }
    
    public static Mastermind create(String json) throws IOException {
        Mastermind m = new Mastermind();
        if (json!=null){
            try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
                JsonReader jr = Json.createReader(is);
                JsonObject job = jr.readObject();
                m.setName(job.getString("name"));
                m.setPieces(Pieces.createJson(job.getJsonObject("pieces")));
            }
        }

        return m;
    }
}
