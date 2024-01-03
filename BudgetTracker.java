
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BudgetTrackerApp {

    private static ArrayList<Expence> expenses = new ArrayList<>();
    private static HashMap<String, Double> categoryLimits = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewRecentTransactions();
                    break;
                case 3:
                    viewBalance();
                    break;
                case 4:
                    setCategoryLimit();
                    break;
                case 5:
                    generateReport();
                    break;
                case 6:
                    System.out.println("Exiting budget tracker...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nBudget Tracker Menu:");
        System.out.println("1. Add Expense");
        System.out.println("2. View Recent Transactions");
        System.out.println("3. View Balance");
        System.out.println("4. Set Category Limit");
        System.out.println("5. Generate Report");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addExpense() {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter expense category: ");
            String category = scanner.nextLine();

            System.out.print("Enter expense amount: ");
            double amount = scanner.nextDouble();

            System.out.print("Enter expense date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine(); // Consume newline from previous input
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            System.out.print("Enter expense description: ");
            String description = scanner.nextLine();

            Expense expense = new Expense(category, amount, date, description);
            expenses.add(expense);

            System.out.println("Expense added successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private static void viewRecentTransactions() {

        System.out.println("\nRecent Transactions:");
        System.out.println("--------------------");

        int numTransactionsToShow = 10; // Adjust as needed
        int count = 0;

        for (int i = expenses.size() - 1; i >= 0 && count < numTransactionsToShow; i--) {
            Expense expense = expenses.get(i);
            System.out.printf("%-15s %-10s %-15s %s\n", expense.getDate(), expense.getCategory(), expense.getAmount(),
                    expense.getDescription());
            count++;
        }

        if (count == 0) {
            System.out.println("No transactions to display.");
        }

    }

    private static void viewBalance() {

        double totalIncome = 0; // Assuming income is not yet implemented
        double totalExpenses = 0;

        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        double balance = totalIncome - totalExpenses;

        System.out.println("\nBalance:");
        System.out.println("--------");
        System.out.println("Total Income:   $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpenses);
        System.out.println("Current Balance: $" + balance);

    }

    private static void setCategoryLimit() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();

        try {
            System.out.print("Enter spending limit for " + category + ": $");
            double limit = scanner.nextDouble();

            categoryLimits.put(category, limit);
            System.out.println("Category limit set successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid limit amount. Please enter a valid number.");
        }

    }

    private static void generateReport() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();
        Date startDate = parseDate(startDateStr); // Use your date parsing method

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine();
        Date endDate = parseDate(endDateStr);

        System.out.print("Enter category filter (leave blank for all categories): ");
        String categoryFilter = scanner.nextLine();

        System.out.println("\nReport:");
        System.out.println("-------");
        System.out.println("Period: " + startDate + " - " + endDate);

        // Calculate total income, expenses, and balance for the filtered period
        double totalIncome = 0; // Assuming income is not yet implemented
        double totalExpenses = 0;

        for (Expense expense : expenses) {
            if (expense.getDate().compareTo(startDate) >= 0 && expense.getDate().compareTo(endDate) <= 0 &&
                    (categoryFilter.isEmpty() || expense.getCategory().equalsIgnoreCase(categoryFilter))) {
                totalExpenses += expense.getAmount();
            }
        }

        double balance = totalIncome - totalExpenses;

        // Display summary
        System.out.println("Total Income:   $" + totalIncome);
        System.out.println("Total Expenses: $" + totalExpenses);
        System.out.println("Balance:        $" + balance);

        // Display expense breakdown by category (if applicable)
        if (!expenses.isEmpty()) {
            System.out.println("\nExpense Breakdown:");
            // ... (implement logic to group expenses by category and display their totals)
        }

    }
}

class Expense {
    private String category;
    private double amount;
    private Date date;
    private String description;

    // Constructor
    public Expense(String category, double amount, Date date, String description) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
