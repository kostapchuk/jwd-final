package com.epam.jwd_final.web.controller;

import com.epam.jwd_final.web.command.Command;
import com.epam.jwd_final.web.command.ResponseContext;
import com.epam.jwd_final.web.command.WrappingRequestContext;
import com.epam.jwd_final.web.observer.EventManager;
import com.epam.jwd_final.web.observer.Payout;
import com.epam.jwd_final.web.observer.PayoutNotificationListener;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static final String COMMAND_PARAMETER_NAME = "command";

    public static final Payout payout = new Payout();

    @Override
    public void init() throws ServletException {
        super.init();
        payout.events.subscribe("payout", new PayoutNotificationListener());
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
        final String command = req.getParameter(COMMAND_PARAMETER_NAME);
        final Command businessCommand = Command.of(command);
        final ResponseContext result = businessCommand.execute(WrappingRequestContext.of(req));
        if (result.isRedirect()) {
            resp.sendRedirect(result.getPage());
        } else {
            final RequestDispatcher dispatcher = req.getRequestDispatcher(result.getPage());
            dispatcher.forward(req, resp);
        }
    }
}
