package com.epam.jwd_final.web.command;

import com.epam.jwd_final.web.exception.CommandException;

/**
 * Command.
 *
 * @author Ostapchuk Kirill
 */
public interface Command {

    /**
     * Execute command.
     *
     * @param req {@link RequestContext}
     * @return {@link ResponseContext}
     */
    ResponseContext execute(RequestContext req) throws CommandException;

    static Command of(String name) {
        return CommandManager.of(name);
    }
}
