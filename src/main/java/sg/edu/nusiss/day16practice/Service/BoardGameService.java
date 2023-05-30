package sg.edu.nusiss.day16practice.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nusiss.day16practice.Model.Mastermind;
import sg.edu.nusiss.day16practice.Repository.BoardGameRepository;

@Service
public class BoardGameService {
    
    @Autowired
    private BoardGameRepository repo;

    public int saveGame (final Mastermind m) {
        return this.repo.saveGame(m);
    }

    public Mastermind findById(final String mid) throws IOException {
        return this.repo.findById(mid);
    }

    public int updateBoardGame(final Mastermind ms) {
        return repo.updateBoardGame(ms);
    }

}
