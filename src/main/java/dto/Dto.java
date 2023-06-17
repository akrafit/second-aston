package dto;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public interface Dto {
    Gson gson = new Gson();
    default IdDto getIdDto (HttpServletRequest request){
        StringBuilder jb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(String.valueOf(jb), IdDto.class);
    }
}
