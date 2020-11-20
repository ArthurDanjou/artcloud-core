package fr.arthurdanjou.artcloud.common.managers;

import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.commands.AbstractCommand;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsManager extends AbstractManager {

    @Getter
    private final List<AbstractCommand> commands;

    public CommandsManager(ArtCloud artCloud) {
        super(artCloud, "Commands");
        this.commands = new ArrayList<>();
    }

    @Override
    public void enable() {

    }

    public void inputCommand(String data) {
        String[] args = data.split(" ");
        String command = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        if (command.equals("help")) {
            showHelp();
            return;
        }

        for (AbstractCommand command1 : commands) {
            if (command1.getCommand().equals(command)) {
                if (!command1.execute(args)) {
                    artCloud.getLogger().warn("Erreur pendant l'éxecution de la commande !");
                }
                return;
            }
        }
        artCloud.getLogger().warn("La commande n'existe pas !");
    }

    public void showHelp() {
        for (AbstractCommand command : commands) {
            String help = command.getHelp();
            if (help != null) {
                artCloud.getLogger().info(help);
            }
        }
    }

    @Override
    public void disable() {

    }
}
