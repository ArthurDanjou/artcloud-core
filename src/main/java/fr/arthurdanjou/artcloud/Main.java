package fr.arthurdanjou.artcloud;

import fr.arthurdanjou.artcloud.client.ArtCloudClient;
import fr.arthurdanjou.artcloud.master.ArtCloudMaster;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

public class Main {

    private static final String ascii =
            """
                    _____/\\\\\\\\\\\\\\\\\\_______________________________________/\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\________________________________________/\\\\\\__       \s
                     ___/\\\\\\\\\\\\\\\\\\\\\\\\\\__________________________________/\\\\\\////////__\\////\\\\\\_______________________________________\\/\\\\\\__      \s
                      __/\\\\\\/////////\\\\\\___________________/\\\\\\________/\\\\\\/______________\\/\\\\\\_______________________________________\\/\\\\\\__     \s
                       _\\/\\\\\\_______\\/\\\\\\__/\\\\/\\\\\\\\\\\\\\___/\\\\\\\\\\\\\\\\\\\\\\__/\\\\\\________________\\/\\\\\\________/\\\\\\\\\\_____/\\\\\\____/\\\\\\________\\/\\\\\\__    \s
                        _\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_\\/\\\\\\/////\\\\\\_\\////\\\\\\////__\\/\\\\\\________________\\/\\\\\\______/\\\\\\///\\\\\\__\\/\\\\\\___\\/\\\\\\___/\\\\\\\\\\\\\\\\\\__   \s
                         _\\/\\\\\\/////////\\\\\\_\\/\\\\\\___\\///_____\\/\\\\\\______\\//\\\\\\_______________\\/\\\\\\_____/\\\\\\__\\//\\\\\\_\\/\\\\\\___\\/\\\\\\__/\\\\\\////\\\\\\__  \s
                          _\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\____________\\/\\\\\\_/\\\\___\\///\\\\\\_____________\\/\\\\\\____\\//\\\\\\__/\\\\\\__\\/\\\\\\___\\/\\\\\\_\\/\\\\\\__\\/\\\\\\__ \s
                           _\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\____________\\//\\\\\\\\\\______\\////\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\__\\///\\\\\\\\\\/___\\//\\\\\\\\\\\\\\\\\\__\\//\\\\\\\\\\\\\\/\\\\_\s
                            _\\///________\\///__\\///______________\\/////__________\\/////////__\\/////////_____\\/////______\\/////////____\\///////\\//__

                    """;

    public static void main(String[] args) {
        System.out.println(" ");
        System.out.println(ascii);
        System.out.println(" ");
        System.out.println("Please choose between Client & Master !");
        System.out.println("> [1] Client");
        System.out.println("> [2] Master");

        try {
            ArtCloud artCloud = null;
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext(">")) {
                switch (scanner.next()) {
                    case "1", "client", "c" -> artCloud = new ArtCloudClient();
                    case "2", "master", "m" -> artCloud = new ArtCloudMaster();
                    default -> {
                        if (artCloud != null && artCloud.isRunning()) {
                            artCloud.getCommandsManager().inputCommand(scanner.next());
                        }
                    }
                }
            }

        /*
        TODO check if master is already running
         */

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
            System.exit(42);
        }
    }

}
