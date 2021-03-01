package com.epam.jwd_final.web.controller;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.Page;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.ResponseContextResult;
import com.epam.jwd_final.web.command.WrappingRequestContext;
import com.epam.jwd_final.web.exception.CommandException;
import com.epam.jwd_final.web.observer.CancelMatchListener;
import com.epam.jwd_final.web.observer.Payout;
import com.epam.jwd_final.web.observer.WinUserListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private static final String COMMAND_PARAMETER_NAME = "command";
    private static final String WIN_USER_EVENT_TYPE = "winUser";
    private static final String CANCEL_MATCH_EVENT_TYPE = "cancelMatch";
    private static final String ERROR = "error";


    public static Payout payout = new Payout();

    @Override
    public void init() throws ServletException {
        super.init();
        payout.events.subscribe(WIN_USER_EVENT_TYPE, new WinUserListener());
        payout.events.subscribe(CANCEL_MATCH_EVENT_TYPE, new CancelMatchListener());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final String command = req.getParameter(COMMAND_PARAMETER_NAME);
            final Command businessCommand = Command.of(command);
            final ResponseContext result = businessCommand.execute(WrappingRequestContext.of(req));
            if (result.isRedirect()) {
                resp.sendRedirect(req.getContextPath() + result.getPage());
            } else {
                final RequestDispatcher dispatcher = req.getRequestDispatcher(result.getPage());
                dispatcher.forward(req, resp);
            }
        } catch (CommandException e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute(ERROR, e.getMessage());
            final RequestDispatcher dispatcher = req.getRequestDispatcher(Page.ERROR.getLink());
            dispatcher.forward(req, resp);
        }
    }
}
