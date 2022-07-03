package academy.prog;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static Map<String, String> accounts = new HashMap<>();
    static {
        accounts.put("admin", "admin");
    }

    public static Map<String, String> getAccounts() {
        return accounts;
    }

    public static void setAccounts(Map<String, String> accounts) {
        LoginServlet.accounts = accounts;
    }

    private boolean isPasswordSecure(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}";
        return password.matches(pattern);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String age = request.getParameter("age");

        if (accounts.containsKey(login) && accounts.get(login).equals(password) && (!age.matches("\\d+") || Integer.parseInt(age) < 18)) {
            HttpSession session = request.getSession(false);
            session.setAttribute("user_age", age);
        } else if (accounts.containsKey(login) && accounts.get(login).equals(password) && Integer.parseInt(age) >= 18) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user_login", login);
            session.setAttribute("user_age", age);
            session.setAttribute("pass_strength", isPasswordSecure(password));
        } else {
            HttpSession session = request.getSession(false);
            session.setAttribute("input", "wrong");
        }
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a = request.getParameter("a");
        HttpSession session = request.getSession(false);

        if ("exit".equals(a) && (session != null)) {
            session.removeAttribute("user_login");
            session.removeAttribute("user_age");
            session.removeAttribute("input");
            response.sendRedirect("index.jsp");
        } else if ("new".equals(a) && session != null) {
            session.removeAttribute("new_account");
            response.sendRedirect("registration.jsp");
        }


    }
}
