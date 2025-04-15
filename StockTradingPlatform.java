import java.util.HashMap;
import java.util.Scanner;
public class StockTradingPlatform {
    private static HashMap<String, Double> stockMarket = new HashMap<>();
    private static HashMap<String, Integer> portfolio = new HashMap<>();
    private static double balance = 10000.0;
    public static void main(String[] args) {
        stockMarket.put("AAPL", 150.0);
        stockMarket.put("GOOGL", 2800.0);
        stockMarket.put("TSLA", 700.0);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. View Market Data\n2. Buy Stock\n3. Sell Stock\n4. View Portfolio\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> viewMarketData();
                case 2 -> buyStock(scanner);
                case 3 -> sellStock(scanner);
                case 4 -> viewPortfolio();
                case 5 -> {
                    System.out.println("Exiting application. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
    private static void viewMarketData() {
        System.out.println("\nMarket Data:");
        stockMarket.forEach((stock, price) -> System.out.println(stock + ": $" + price));
    }

    private static void buyStock(Scanner scanner) {
        System.out.print("Enter stock symbol to buy: ");
        String stock = scanner.next().toUpperCase();
        if (!stockMarket.containsKey(stock)) {
            System.out.println("Invalid stock symbol.");
            return;
        }
        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();
        double totalCost = stockMarket.get(stock) * quantity;

        if (totalCost > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= totalCost;
            portfolio.put(stock, portfolio.getOrDefault(stock, 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + stock);
        }
    }
    private static void sellStock(Scanner scanner) {
        System.out.print("Enter stock symbol to sell: ");
        String stock = scanner.next().toUpperCase();
        if (!portfolio.containsKey(stock)) {
            System.out.println("You don't own this stock.");
            return;
        }
        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        if (portfolio.get(stock) < quantity) {
            System.out.println("Insufficient shares.");
        } else {
            double totalSale = stockMarket.get(stock) * quantity;
            balance += totalSale;
            portfolio.put(stock, portfolio.get(stock) - quantity);
            if (portfolio.get(stock) == 0) {
                portfolio.remove(stock);
            }
            System.out.println("Sold " + quantity + " shares of " + stock);
        }
    }
    private static void viewPortfolio() {
        System.out.println("\nPortfolio Summary:");
        if (portfolio.isEmpty()) {
            System.out.println("No stocks in portfolio.");
        } else {
            portfolio.forEach((stock, quantity) -> System.out.println(stock + ": " + quantity + " shares"));
        }
        System.out.println("Balance: $" + balance);
    }
}
