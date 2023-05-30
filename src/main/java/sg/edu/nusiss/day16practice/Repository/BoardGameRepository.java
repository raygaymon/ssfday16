package sg.edu.nusiss.day16practice.Repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nusiss.day16practice.Model.Mastermind;

@Repository
public class BoardGameRepository {
    
    @Autowired
    private RedisTemplate<String, Object> template;

    public int saveGame(final Mastermind m) {

        template.opsForValue().set(m.getId(), m.toJSON().toString());
        String result = (String) template.opsForValue().get(m.getId());
        if(null!=result){
            return 1;
        }
        return 0;
    }

    public Mastermind findById(final String mid) throws IOException {
        String jsonVal = (String)template.opsForValue().get(mid);
        Mastermind m = null;
        if (jsonVal != null) {
            m = Mastermind.create(jsonVal);
        }
        return m;
    }

    public int updateBoardGame(final Mastermind ms) {
        String result = (String) template
                .opsForValue().get(ms.getId());
        if (ms.isIsUpsert()) {
            if (result != null) {
                template.opsForValue().set(ms.getId(), ms.toJSON().toString());
            } else {
                ms.setId(ms.generateID1(8));
                template.opsForValue()
                        .setIfAbsent(ms.getId(), ms.toJSON().toString());
            }

        } else {
            if (result != null) {
                template.opsForValue().set(ms.getId(), ms.toJSON().toString());
            }
        }

        result = (String) template.opsForValue().get(ms.getId());
        if (result != null)
            return 1;
        return 0;
    }
}
