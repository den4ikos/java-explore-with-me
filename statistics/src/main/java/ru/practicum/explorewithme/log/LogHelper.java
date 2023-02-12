package ru.practicum.explorewithme.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class LogHelper {
    public static void dump(Map<String, Object> params, HttpServletRequest request) {
        StringBuilder message = new StringBuilder("Query to endpoint ");
        message.append(request.getRequestURI()).append(" ");
        message.append("with params: ");
        for (String p: params.keySet()) {
            message.append("'").append(p).append("'").append(": ").append(params.get(p)).append(" ");
        }
        log.info(message.toString());
    }
}
