package fr.arthurdanjou.artcloud.common.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public abstract class AbstractCommand {

    protected String command;
    protected List<String> aliases;

    public abstract boolean execute(String[] args);

    public abstract String getHelp();

}
