package com.epam.jwd_final.web.listener;

import com.epam.jwd_final.web.connection.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    public static Payout payout;
    private static final String WIN_USER_EVENT_TYPE = "winUser";
    private static final String CANCEL_MATCH_EVENT_TYPE = "cancelMatch";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        payout = new Payout();
        ConnectionPool.getInstance();
        payout.events.subscribe(WIN_USER_EVENT_TYPE, WinUserListener.INSTANCE);
        payout.events.subscribe(CANCEL_MATCH_EVENT_TYPE, CancelMatchListener.INSTANCE);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroy();
        payout.events.unsubscribe(WIN_USER_EVENT_TYPE, WinUserListener.INSTANCE);
        payout.events.unsubscribe(CANCEL_MATCH_EVENT_TYPE, CancelMatchListener.INSTANCE);
    }
}
