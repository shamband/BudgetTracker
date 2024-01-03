import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BudgetTracker {

    private static ArrayList<Expense> expenses = new ArrayList<>();
    private static HashMap<String, Double> categoryLimits = new HashMap<>();
    private static ArrayList<Income> incomeList = new ArrayList<>();

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
                    addIncome(); // Invoke addIncome() when choice is 2
                    break;
                case 3:
                    viewRecentTransactions();
                    break;
                case 4:
                    viewBalance();
                    break;
                case 5:
                    setCategoryLimit();
                    break;
                case 6:
                    generateReport();
                    break;
                case 7:
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
        System.out.println("2. Add Income");
        System.out.println("3. View Recent Transactions");
        System.out.println("4. View Balance");
        System.out.println("5. Set Category Limit");
        System.out.println("6. Generate Report");
        System.out.println("7. Exit");

        System.out.print("Enter your choice: ");
    }

    private static void addExpense() {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter expense category: ");
            String category = scanner.nextLine();

            System.out.print("Enter expense amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

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

    private static void addIncome() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter income source: ");
            String source = scanner.nextLine();

            System.out.print("Enter income amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character

            System.out.print("Enter income date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            Date date = parseDate(dateStr);

            System.out.print("Enter income description (optional): ");
            String description = scanner.nextLine();

            Income income = new Income(source, amount, date, description);
            incomeList.add(income); // Store the income object in incomeList

            System.out.println("Income added successfully!");
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
        double totalIncome = 0;
        double totalExpenses = 0;

        // Calculate total expenses
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        // Calculate total income
        for (Income income : incomeList) {
            totalIncome += income.getAmount();
        }

        double balance = totalIncome - totalExpenses;

        System.out.println("\nBalance:");
        System.out.println("--------");
        System.out.println("Total Income:  $" + totalIncome);
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

    private static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateStr);
    }

    private static void generateReport() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();

        try {
            Date startDate = parseDate(startDateStr);

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
            System.out.println("Total Income:  $" + totalIncome);
            System.out.println("Total Expenses: $" + totalExpenses);
            System.out.println("Balance:    $" + balance);

            // Display expense breakdown by category (if applicable)
            if (!expenses.isEmpty()) {
                System.out.println("\nExpense Breakdown:");

                HashMap<String, Double> categoryTotals = new HashMap<>();

                // Group expenses by category and calculate totals
                for (Expense expense : expenses) {
                    String category = expense.getCategory();
                    double amount = expense.getAmount();

                    categoryTotals.putIfAbsent(category, 0.0); // Add category if not already present
                    categoryTotals.put(category, categoryTotals.get(category) + amount); // Increment total for the
                                                                                         // category
                }

                // Display category totals
                for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                    String category = entry.getKey();
                    double total = entry.getValue();
                    System.out.printf("%-15s $%.2f\n", category, total);
                }
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            // Optionally, prompt the user to enter the date again or take other corrective
            // actions.
        }
    }

}

class Income {
    private String source;
    private double amount;
    private Date date;
    private String description;

    // Constructor
    public Income(String source, double amount, Date date, String description) {
        this.source = source;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // Getters
    public String getSource() {
        return source;
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
    public void setSource(String source) {
        this.source = source;
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
