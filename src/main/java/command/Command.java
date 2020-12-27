package command;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

@ToString
public class Command {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    private Function<Pair<Session, Scanner>, Void> func;

    Command(String name, String description, Function<Pair<Session, Scanner>, Void> func) {
        this.name = name;
        this.description = description;
        this.func = func;
    }

    public void perform(Session session, Scanner in) {
        func.apply(new Pair(session, in));
    }

    @Override
    public boolean equals(Object commandName) {
        return name.equals(commandName);
    }

    public void describe() {
        System.out.println("func " + name + ", description: " + description);
    }

    public static Command invalidCommand;
    static {
        invalidCommand = new Command("invalid command", "just invalid func", func -> {
            System.out.println("invalid function");
            return null;
        });
    }
}



