package academy.prog;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String log = req.getParameter("newLogin");
        String pass = req.getParameter("newPassword");
        String passConf = req.getParameter("confirmPassword");

        if (log != null && log.length() >=5 && pass != null && pass.length() >= 10 && passConf != null && !LoginServlet.getAccounts().containsKey(log) && pass.equals(passConf)) {
            LoginServlet.getAccounts().put(log, pass);
            HttpSession session = req.getSession(true);
            session.setAttribute("new_account", "created");
            resp.sendRedirect("index.jsp");
        } else {
            if (log == null || log.length() < 5) {
                HttpSession session = req.getSession(false);
                session.setAttribute("login", "short");
            }
            if (pass == null || pass.length() < 10) {
                HttpSession session = req.getSession(false);
                session.setAttribute("password", "short");
            }
            if (LoginServlet.getAccounts().containsKey(log)) {
                HttpSession session = req.getSession(false);
                session.setAttribute("login", "already_exists");
            }
            if (pass != null && passConf != null && !pass.equals(passConf)) {
                HttpSession session = req.getSession(false);
                session.setAttribute("password", "mismatch");
            }
            resp.sendRedirect("registration.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("registration.jsp");
    }
}
