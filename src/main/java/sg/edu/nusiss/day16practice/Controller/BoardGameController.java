package sg.edu.nusiss.day16practice.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nusiss.day16practice.Model.Mastermind;
import sg.edu.nusiss.day16practice.Service.BoardGameService;

@RestController
@RequestMapping(path ="/api/boardgame", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
public class BoardGameController {
    @Autowired
    private BoardGameService service;
    
    @PostMapping
    public ResponseEntity<String> createBoardGame(@RequestBody Mastermind m) {

        int insertCount = service.saveGame(m);
        Mastermind mmd = new Mastermind();
        mmd.setId(m.getId());
        mmd.setInsertCount(insertCount);
        //if there was no count inserted need to return internal server error
        if (insertCount == 0) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mmd.toJSONInsert().toString());
        }
        //how to set status codes like 404
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(mmd.toJSON().toString());
    }

    @GetMapping(path="{msId}") 
    public ResponseEntity<String> getBoardGame (@PathVariable String msId) throws IOException {
        Mastermind result = new Mastermind();
        if (result == null || result.getName()==null) {
            return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(result.toJSONInsert().toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(result.toJSONInsert().toString());
    }


    @PutMapping(path = "{msId}")
    public ResponseEntity<String> updateBoardGame(
            @RequestBody Mastermind ms,
            @PathVariable String msId,
            @RequestParam(defaultValue = "false") boolean isUpSert

    ) throws IOException {
        Mastermind result = null;
        ms.setUsUpsert(isUpSert);
        if (!isUpSert) {
            result = service.findById(msId);
            if (result == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("");
            }
        }
        ms.setId(msId);
        int updateCount = service.updateBoardGame(ms);
        ms.setUpdateCount(updateCount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ms.toJSONUpdate().toString());
    }
}
