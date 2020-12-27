package main;

import command.CommandList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //bootstrap
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        Scanner in = new Scanner(System.in);
        var commandList = new CommandList();
        commandList.initDefaultCommands();

        while (true) {
            commandList.listCommands();
            String currentCommandName = in.next();
            var currentCommand = commandList.getCommand(currentCommandName);
            try {
                currentCommand.perform(session, in);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}