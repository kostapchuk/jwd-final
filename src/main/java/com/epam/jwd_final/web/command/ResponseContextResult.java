package com.epam.jwd_final.web.command;

public class ResponseContextResult implements ResponseContext {

    private final String page;
    private final boolean isRedirect;

    private ResponseContextResult(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public static ResponseContextResult forward(String page) {
        return new ResponseContextResult(page, false);
    }

    public static ResponseContextResult redirect(String page) {
        return new ResponseContextResult(page, true);
    }

    @Override
    public String getPage() {
        return page;
    }

    @Override
    public boolean isRedirect() {
        return isRedirect;
    }
}
