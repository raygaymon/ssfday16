package sg.edu.nusiss.day16practice.Model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Pieces implements Serializable{
    private DecodingBoard decoding_board;
    private Pegs pegs;
    private Rulebook rulebook;


    public DecodingBoard getDecoding_board() {
        return decoding_board;
    }
    public void setDecoding_board(DecodingBoard decoding_board) {
        this.decoding_board = decoding_board;
    }
    public Pegs getPegs() {
        return pegs;
    }
    public void setPegs(Pegs pegs) {
        this.pegs = pegs;
    }
    public Rulebook getRulebook() {
        return rulebook;
    }
    public void setRulebook(Rulebook rulebook) {
        this.rulebook = rulebook;
    }
    
    public JsonObjectBuilder toJSON(){
        return Json.createObjectBuilder()
                .add("deocding_board", this.getDecoding_board().toJSON())
                .add("pegs", this.getPegs().toJSON())
                .add("rulebook", this.getRulebook().toJSON());
    }

    public static Pieces createJson (JsonObject o) {
        Pieces p = new Pieces();
        JsonObject decodingboard =o.getJsonObject("decoding_board");
        JsonObject pegs =o.getJsonObject("pegs");
        JsonObject rulebook =o.getJsonObject("rulebook");

        p.setDecoding_board(DecodingBoard.createJson(decodingboard));
        p.setPegs(Pegs.createJson(pegs));
        p.setRulebook(Rulebook.createJson(rulebook));
        return p;
    }
}
