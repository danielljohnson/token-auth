package tokenAuth;

import java.util.Enumeration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    
    private final String token = "abc123";

    @RequestMapping(method=RequestMethod.POST, value="/login", produces="application/json")
    public String login(
        @RequestParam(value="username", required=true) String username,
        @RequestParam(value="password", required=true) String password
    ) {
        return "{\"token\": \"abc123\"}";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/books", produces="application/json")
    public String login(HttpServletRequest request) {
        String requestToken = request.getHeader("x-auth-token");
        
        // use this to display all headers
        /*Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println("Name: " + headerName + " Value: " + headerValue);
        }*/
        
        String response;
        
        if (requestToken != null && requestToken.equals(token)) {
            response = "{\"message\": \"Some books that needed a token\"}";
        } else {
            // I don't know how to trigger a 401 response yet, so this works for now.
            response = "{\"message\": \"Bad token\"}";
        }
        
        return response;
    }
}
