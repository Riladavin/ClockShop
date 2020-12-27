package command;

import clock.Clock;
import clock.ClockShop;
import clock.ClocksForShops;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ToString
public class CommandList {
    private List<Command> commandList;

    public CommandList() {}

    public void initDefaultCommands() {
        commandList.add(new Command("add_clock", "adds clock", sessionScannerPair -> {
            //bootstrap
            var session = sessionScannerPair.getKey();
            var in = sessionScannerPair.getValue();
            var transaction = session.beginTransaction();

            try {
                System.out.println("print name of clocks please: ");
                var name = in.nextLine();
                var newClock = new Clock(name);
                session.save(newClock);
                transaction.commit();
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }));
        commandList.add(new Command("add_shop", "adds shop", sessionScannerPair -> {
            //bootstrap
            var session = sessionScannerPair.getKey();
            var in = sessionScannerPair.getValue();
            var transaction = session.beginTransaction();

            try {
                System.out.println("print name of shop please: ");
                var name = in.nextLine();
                var newClockShop = new ClockShop(name);
                session.save(newClockShop);
                transaction.commit();
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }));
        commandList.add(new Command("delete_clock", "deletes clock", sessionScannerPair -> {
            //bootstrap
            var session = sessionScannerPair.getKey();
            var in = sessionScannerPair.getValue();
            var transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Clock> query = builder.createQuery(Clock.class);
            Root<Clock> clockRoot = query.from(Clock.class);
            query.select(clockRoot);

            try {
                var clockList = session.createQuery(query).getResultList();
                if (clockList.isEmpty()) {
                    System.out.println("no clocks");
                    transaction.commit();
                    return null;
                }
                int currentNumber = 1;
                for (var clock : clockList) {
                    System.out.printf("%d)%s\n", currentNumber++, clock.getName());
                }
                int indexToDelete = 0;
                while (!(1 <= indexToDelete && indexToDelete <= clockList.size())) {
                    System.out.printf("Please select number of clocks to delete(from 1 to %d):\n", clockList.size());
                    indexToDelete = in.nextInt();
                }
                session.delete(clockList.get(--indexToDelete));
                transaction.commit();
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }));
        commandList.add(new Command("delete_shop", "deletes shop", sessionScannerPair -> {
            //bootstrap
            var session = sessionScannerPair.getKey();
            var in = sessionScannerPair.getValue();
            var transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ClockShop> query = builder.createQuery(ClockShop.class);
            Root<ClockShop> clockShopRoot = query.from(ClockShop.class);
            query.select(clockShopRoot);

            try {
                var clockShopList = session.createQuery(query).getResultList();
                if (clockShopList.isEmpty()) {
                    System.out.println("no clock shops");
                    transaction.commit();
                    return null;
                }
                int currentNumber = 1;
                for (var clockShop : clockShopList) {
                    System.out.printf("%d)%s\n", currentNumber++, clockShop.getName());
                }
                int indexToDelete = 0;
                while (!(1 <= indexToDelete && indexToDelete <= clockShopList.size())) {
                    System.out.printf("Please select number of clock shop to delete(from 1 to %d):\n", clockShopList.size());
                    indexToDelete = in.nextInt();
                }
                session.delete(clockShopList.get(--indexToDelete));
                transaction.commit();
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }));
        commandList.add(new Command("add_clock_to_shop", "adding clock in shop", sessionScannerPair -> {
            //bootstrap
            var session = sessionScannerPair.getKey();
            var in = sessionScannerPair.getValue();
            var transaction = session.beginTransaction();

            CriteriaBuilder builderForClockShops = session.getCriteriaBuilder();
            CriteriaQuery<ClockShop> queryClockShops = builderForClockShops.createQuery(ClockShop.class);
            Root<ClockShop> clockShopRoot = queryClockShops.from(ClockShop.class);
            queryClockShops.select(clockShopRoot);

            CriteriaBuilder builderForClocks = session.getCriteriaBuilder();
            CriteriaQuery<Clock> queryClocks = builderForClocks.createQuery(Clock.class);
            Root<Clock> clockRoot = queryClocks.from(Clock.class);
            queryClocks.select(clockRoot);

            try {
                var clockShopList = session.createQuery(queryClockShops).getResultList();
                var clockList = session.createQuery(queryClocks).getResultList();
                if (clockList.isEmpty()) {
                    System.out.println("no clocks");
                    transaction.commit();
                    return null;
                }
                if (clockShopList.isEmpty()) {
                    System.out.println("no clock shops");
                    transaction.commit();
                    return null;
                }
                System.out.println("Clock shops:");
                int currentNumber = 1;
                for (var clockShop : clockShopList) {
                    System.out.printf("%d)%s\n", currentNumber++, clockShop.getName());
                }
                System.out.println("Clocks:");
                currentNumber = 1;
                for (var clock : clockList) {
                    System.out.printf("%d)%s\n", currentNumber++, clock.getName());
                }
                int shopIndexToDelete = 0, clockIndexToDelete = 0;
                while (!(1 <= shopIndexToDelete && shopIndexToDelete <= clockShopList.size() && 1 <= clockIndexToDelete && clockIndexToDelete <= clockList.size())) {
                    System.out.printf("Please select number of clock shop and clock(two numbers from 1 to %d and from 1 to %d):\n", clockShopList.size(), clockList.size());
                    shopIndexToDelete = in.nextInt();
                    clockIndexToDelete = in.nextInt();
                }

                var link = new ClocksForShops(clockShopList.get(--shopIndexToDelete).getId(), clockList.get(--clockIndexToDelete).getId());
                session.save(link);
                transaction.commit();

            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }));
        commandList.add(new Command("list_clocks_by_shop", "displays watches from a specific store", sessionScannerPair -> {
//bootstrap
            var session = sessionScannerPair.getKey();
            var in = sessionScannerPair.getValue();
            var transaction = session.beginTransaction();

            CriteriaBuilder builderForClockShops = session.getCriteriaBuilder();
            CriteriaQuery<ClockShop> queryClockShops = builderForClockShops.createQuery(ClockShop.class);
            Root<ClockShop> clockShopRoot = queryClockShops.from(ClockShop.class);
            queryClockShops.select(clockShopRoot);

            try {
                var clockShopList = session.createQuery(queryClockShops).getResultList();
                if (clockShopList.isEmpty()) {
                    System.out.println("no clock shops");
                    transaction.commit();
                    return null;
                }
                System.out.println("Clock shops:");
                int currentNumber = 1;
                for (var clockShop : clockShopList) {
                    System.out.printf("%d)%s\n", currentNumber++, clockShop.getName());
                }
                int shopIndexToDelete = 0;
                while (!(1 <= shopIndexToDelete && shopIndexToDelete <= clockShopList.size())) {
                    System.out.printf("Please select number of clock shop(number from 1 to %d):\n", clockShopList.size());
                    shopIndexToDelete = in.nextInt();
                }

                for (var clock: clockShopList.get(--shopIndexToDelete).getClocks()) {
                    System.out.println(clock.getName());
                }
                transaction.commit();

            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }));
        commandList.add(new Command("exit", "exits from the program", sessionScannerPair -> {
            //bootstrap
            var session = sessionScannerPair.getKey();
            var in = sessionScannerPair.getValue();
            in.close();
            session.close();
            System.exit(0);
            return null;
        }));
    }

    public void listCommands() {
        System.out.printf("we have %d commands:\n", commandList.size());
        for (var command: commandList) {
            command.describe();
        }
    }

    public Command getCommand(String commandName) {
        for (var command: commandList) {
            if (command.equals(commandName)) return command;
        }
        return Command.invalidCommand;
    }
}
