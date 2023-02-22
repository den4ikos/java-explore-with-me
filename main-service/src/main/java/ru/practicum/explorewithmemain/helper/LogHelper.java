package ru.practicum.explorewithmemain.helper;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class LogHelper {
    public static void dump(Map<String, Object> params, HttpServletRequest request) {
        StringBuilder message = new StringBuilder("\nQUERY to endpoint ");
        message.append(request.getRequestURI()).append(" ");
        message.append("METHOD: ");
        message.append(request.getMethod()).append(" ");
        message.append("PARAMS: ");
        for (String p: params.keySet()) {
            message.append("'").append(p).append("'").append(": ").append(params.get(p)).append(" ");
        }
        message.append("\n");
        log.info(message.toString());
    }
}
