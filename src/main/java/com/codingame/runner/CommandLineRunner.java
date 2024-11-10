package com.codingame.runner;

import com.codingame.gameengine.runner.MultiplayerGameRunner;
import com.codingame.gameengine.runner.simulate.GameResult;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class CommandLineRunner {
  public static void main(String[] args) {
    try {
      Options options = new Options();
      options.addOption("h", false, "Display help information")
        .addOption("p1", true, "Required. Player 1 command line.")
        .addOption("p2", true, "Required. Player 2 command line.")
        .addOption("p3", true, "Player 3 command line.")
        .addOption("p4", true, "Player 4 command line.")
        .addOption("server", false, "Start a server to visualize the game results")
        .addOption("seed", true, "Seed")
      ;
      CommandLine command = new DefaultParser().parse(options, args);
      if (command.hasOption("h") || !command.hasOption("p1") || !command.hasOption("p2") || !command.hasOption("p3")) {
          new HelpFormatter().printHelp(
            "-p1 <path_to_bot1> -p2 <path_to_bot2> -p3 <path_to_bot3> [-server] [-seed <seed>]",
            options);
          System.exit(0);
      }

      MultiplayerGameRunner runner = new MultiplayerGameRunner();
      runner.setLeagueLevel(4);
      runner.addAgent(command.getOptionValue("p1"));
      runner.addAgent(command.getOptionValue("p2"));
      runner.addAgent(command.getOptionValue("p3"));
      if (command.hasOption("seed")) {
        runner.setSeed(Long.valueOf(command.getOptionValue("seed")));
      }
      
      if (command.hasOption("server")) {
        runner.start();
      }
      else {
        GameResult result = runner.simulate();
      }
    } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace(System.err);
        System.exit(1);
    }    
  }
}