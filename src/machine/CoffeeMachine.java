package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private static Scanner scanner = new Scanner(System.in);

    private static int water = 400;
    private static int milk = 540;
    private static int coffee_beans = 120;
    private static int cups = 9;
    private static int money = 550;

    public static void main(String[] args) {
        intro();
        //cupsOfCoffee();
        //machineCapacity();
        simulateCoffeeMachine();
    }

    static void intro() {
        System.out.println("Starting to make a coffee");
        System.out.println("Grinding coffee beans");
        System.out.println("Boiling water");
        System.out.println("Mixing boiled water with crushed coffee beans");
        System.out.println("Pouring coffee into the cup");
        System.out.println("Pouring some milk into the cup");
        System.out.println("Coffee is ready!");
    }

    static void cupsOfCoffee() {
        System.out.println("Write how many cups of coffee you will need: ");
        int cups = scanner.nextInt();

        System.out.println("For " + cups + " cups of coffee you will need:");
        System.out.println((cups * water) + " ml of water");
        System.out.println((cups * milk) + " ml of milk");
        System.out.println((cups * coffee_beans) + " g of coffee beans");
    }

    static void machineCapacity() {
        System.out.println();
        System.out.println("Write how many ml of water the coffee machine has:");
        int mac_water = scanner.nextInt();
        System.out.println("Write how many ml of milk the coffee machine has:");
        int mac_milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        int mac_coffee_beans = scanner.nextInt();
        System.out.println("Write how many cups of coffee you will need:");
        int reqd_coffee = scanner.nextInt();

        int number_of_cups = 0;

        if (!(mac_water < water || mac_milk < milk || mac_coffee_beans < coffee_beans)) {
            if(number_of_cups == 0) {
                number_of_cups = (int)(mac_water / water);
                //System.out.println("1: " + number_of_cups);

                if(number_of_cups > (int)(mac_milk / milk)) {
                    number_of_cups = (int)(mac_milk / milk);
                    //System.out.println("2: " + (mac_milk / milk));
                }

                if(number_of_cups > (int)(mac_coffee_beans / coffee_beans)) {
                    number_of_cups = (int) (mac_coffee_beans / coffee_beans);
                    //System.out.println("3: " + number_of_cups);
                }
            }
        }


        if(reqd_coffee <= number_of_cups) {
            System.out.print("Yes, I can make that amount of coffee ");
            int diff = number_of_cups - reqd_coffee;
            if(diff > 0) {
                System.out.print("(and even " + diff + " more than that)");
            }
        } else {
            System.out.println("No, I can make only " + number_of_cups + " cup(s) of coffee");
        }
    }

    static void simulateCoffeeMachine() {
        String action;
        do {
            System.out.println();
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            action = scanner.nextLine();

            performAction(action);
        } while (!action.equalsIgnoreCase("exit"));
    }

    static void performAction(String action) {
        switch (action.toLowerCase()) {
            case "buy":
                System.out.println();
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                String buy_val = scanner.nextLine();
                if(buy_val.equalsIgnoreCase("back")) {
                    break;
                }

                switch (Integer.parseInt(buy_val)) {
                    case 1:
                        isCoffeePossible(Espresso.Water.value, 0, Espresso.Coffee_Beans.value, Espresso.Cost.value);
                        break;
                    case 2:
                        isCoffeePossible(Latte.Water.value, Latte.Milk.value, Latte.Coffee_Beans.value, Latte.Cost.value);
                        break;
                    case 3:
                        isCoffeePossible(Cappuccino.Water.value, Cappuccino.Milk.value, Cappuccino.Coffee_Beans.value, Cappuccino.Cost.value);
                        break;
                    default:
                        System.out.println("Wrong input");
                }
                break;
            case "fill":
                fill();
                break;
            case "take":
                System.out.println("I gave you $" + money);
                money = 0;
                break;
            case "remaining":
                ingredientsAvailability();
                break;
        }

    }

    static void ingredientsAvailability() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffee_beans + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        System.out.println(((money > 0) ? "$": "") + money + " of money");
    }

    static void isCoffeePossible(int water, int milk, int coffee_beans, int money) {
        water = CoffeeMachine.water - water;
        milk = CoffeeMachine.milk - milk;
        coffee_beans = CoffeeMachine.coffee_beans - coffee_beans;
        int cups = CoffeeMachine.cups - 1;

        if(water > 0) {
            if(milk > 0) {
                if(coffee_beans > 0) {
                    if(cups > 0) {
                        System.out.println("I have enough resources, making you a coffee!");
                        CoffeeMachine.water = water;
                        CoffeeMachine.milk = milk;
                        CoffeeMachine.coffee_beans = coffee_beans;
                        CoffeeMachine.cups = cups;
                        CoffeeMachine.money += money;
                        return;
                    } else {
                        System.out.println("Sorry, not enough cups!");
                    }
                } else {
                    System.out.println("Sorry, not enough coffee beans!");
                }
            } else {
                System.out.println("Sorry, not enough milk!");
            }
        } else {
            System.out.println("Sorry, not enough water!");
        }
    }

    static void fill() {
        System.out.println("Write how many ml of water do you want to add:");
        CoffeeMachine.water += scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        CoffeeMachine.milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        CoffeeMachine.coffee_beans += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        CoffeeMachine.cups += scanner.nextInt();
    }

    enum Espresso {
        //250 ml of water and 16 g of coffee beans. It costs $4.
        Water(250),
        Coffee_Beans(16),
        Cost(4);

        private int value;
        Espresso(int i) {
            this.value = i;
        }
    }

    enum Latte {
        //350 ml of water, 75 ml of milk, and 20 g of coffee beans. It costs $7.
        Water(350),
        Milk(75),
        Coffee_Beans(20),
        Cost(7);

        private int value;
        Latte(int i) {
            this.value = i;
        }
    }

    enum Cappuccino {
        //200 ml of water, 100 ml of milk, and 12 g of coffee. It costs $6.
        Water(200),
        Milk(100),
        Coffee_Beans(12),
        Cost(6);

        private int value;
        Cappuccino(int i) {
            this.value = i;
        }
    }
}


