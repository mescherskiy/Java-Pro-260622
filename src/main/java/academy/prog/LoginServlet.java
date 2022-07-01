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

    protected static String checkPasswordStrength(String pass) {
        String result = "";
        String specSymbols = "/*!@#$%^&*()\\\"{}_[]|\\\\?/<>,.':;`~+-=№";
        boolean length = false;
        boolean containUpperCase = false;
        boolean containLowerCase = false;
        boolean containSpecialSymbols = false;
        boolean containDigit = false;
        if (pass.length() >= 10) {length = true;}
        for (int i = 0; i < pass.length(); i++) {
            if (Character.isUpperCase(pass.charAt(i))) {containUpperCase = true;}
            else if (Character.isLowerCase(pass.charAt(i))) {containLowerCase = true;}
            else if (specSymbols.contains("" + pass.charAt(i))) {containSpecialSymbols = true;}
            else if (Character.isDigit(pass.charAt(i))) {containDigit = true;}
        }
        if (!length || !containUpperCase || !containLowerCase || !containSpecialSymbols || !containDigit) {
            result += "Warning! Your password is not safe! It must be minimum 10 symbols length and contain:";
            result += " digits, lowercase and uppercase characters, special symbols.";
        }
        return result;
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
            session.setAttribute("pass_strength", checkPasswordStrength(password));
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
