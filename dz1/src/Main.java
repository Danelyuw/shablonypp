import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface IPaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements IPaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплата " + amount + " USD с помощью банковской карты (номер: " + cardNumber + ").");
    }
}

class PayPalPayment implements IPaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплата " + amount + " USD через PayPal (email: " + email + ").");
    }
}

class CryptoPayment implements IPaymentStrategy {
    private String walletAddress;

    public CryptoPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплата " + amount + " USD с использованием криптовалюты (адрес кошелька: " + walletAddress + ").");
    }
}

class PaymentContext {
    private IPaymentStrategy paymentStrategy;

    public void setPaymentStrategy(IPaymentStrategy paymentStrategy) {
        System.out.println("\nУстановлена новая стратегия оплаты.");
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("Не выбрана стратегия оплаты. Оплата невозможна.");
            return;
        }
        System.out.println("--- Начинается процесс оплаты ---");
        paymentStrategy.pay(amount);
        System.out.println("--- Процесс оплаты завершен ---\n");
    }
}

interface IObserver {
    void update(Map<String, Double> rates);
}

interface ISubject {
    void attach(IObserver observer);
    void detach(IObserver observer);
    void notifyObservers();
}

class CurrencyExchange implements ISubject {
    private List<IObserver> observers = new ArrayList<>();
    private Map<String, Double> currentRates = new HashMap<>();

    public CurrencyExchange() {
        currentRates.put("USD_TO_EUR", 0.92);
        currentRates.put("USD_TO_KZT", 470.5); // KZT вместо RUB
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
        System.out.println("Наблюдатель " + observer.getClass().getSimpleName() + " добавлен.");
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
        System.out.println("Наблюдатель " + observer.getClass().getSimpleName() + " удален.");
    }

    @Override
    public void notifyObservers() {
        System.out.println("\nУведомление всех наблюдателей об изменении курсов...");
        for (IObserver observer : observers) {
            observer.update(currentRates);
        }
    }

    public void setRate(String currencyPair, double newRate) {
        if (currentRates.containsKey(currencyPair) && currentRates.get(currencyPair).equals(newRate)) {
            System.out.println("Курс " + currencyPair + " не изменился.");
            return;
        }

        System.out.println("\nИзменение курса: " + currencyPair + " с " + currentRates.getOrDefault(currencyPair, 0.0) + " на " + newRate);
        currentRates.put(currencyPair, newRate);
        notifyObservers();
    }
}

class Trader implements IObserver {
    private String name;

    public Trader(String name) {
        this.name = name;
    }

    @Override
    public void update(Map<String, Double> rates) {
        System.out.println("\nТрейдер " + name + " получил обновление:");
        if (rates.get("USD_TO_EUR") < 0.92) {
            System.out.println("  Курс USD_TO_EUR упал (" + rates.get("USD_TO_EUR") + "). Пора покупать евро!");
        } else {
            System.out.println("  Курс USD_TO_EUR: " + rates.get("USD_TO_EUR") + ". Наблюдаем.");
        }
    }
}

class TaxService implements IObserver {
    @Override
    public void update(Map<String, Double> rates) {
        System.out.println("\nНалоговая служба получила обновление:");
        System.out.println("  Официальный курс USD_TO_KZT: " + rates.get("USD_TO_KZT") + ". Записываем для отчетности.");
    }
}

class UserNotifier implements IObserver {
    private String user;

    public UserNotifier(String user) {
        this.user = user;
    }

    @Override
    public void update(Map<String, Double> rates) {
        System.out.println("\nУведомление для пользователя " + user + ":");
        System.out.println("  Получены новые курсы валют. USD_TO_EUR: " + rates.get("USD_TO_EUR") + ".");
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      ДЕМОНСТРАЦИЯ ШАБЛОНОВ ПРОЕКТИРОВАНИЯ");
        System.out.println("=========================================");

        System.out.println("\n*** 1. Шаблон «Стратегия» (Оплата) ***");

        PaymentContext paymentContext = new PaymentContext();
        double amount = 100.0;
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("Сумма к оплате: " + amount + " USD.");

        paymentContext.setPaymentStrategy(new CreditCardPayment("1234-5678-****-****"));
        paymentContext.executePayment(amount);

        paymentContext.setPaymentStrategy(new PayPalPayment("user@example.com"));
        paymentContext.executePayment(amount);

        System.out.println("Выберите способ оплаты (1 - Карта, 2 - Крипта, 3 - PayPal):");
        choice = scanner.nextLine();

        IPaymentStrategy selectedStrategy;
        switch (choice) {
            case "1":
                selectedStrategy = new CreditCardPayment("9999-****-****-1111");
                break;
            case "2":
                selectedStrategy = new CryptoPayment("0xABC123...");
                break;
            case "3":
                selectedStrategy = new PayPalPayment("another@example.com");
                break;
            default:
                System.out.println("Неверный выбор. Используется оплата по умолчанию (Карта).");
                selectedStrategy = new CreditCardPayment("0000-****-****-0000");
                break;
        }

        paymentContext.setPaymentStrategy(selectedStrategy);
        paymentContext.executePayment(amount * 0.5);


        System.out.println("\n=========================================");
        System.out.println("*** 2. Шаблон «Наблюдатель» (Курсы Валют) ***");

        CurrencyExchange exchange = new CurrencyExchange();

        IObserver trader = new Trader("Alex");
        IObserver taxService = new TaxService();
        IObserver userNotifier = new UserNotifier("Bob");

        exchange.attach(trader);
        exchange.attach(taxService);
        exchange.attach(userNotifier);

        exchange.setRate("USD_TO_EUR", 0.90);

        exchange.setRate("USD_TO_KZT", 465.2);

        exchange.detach(userNotifier);

        exchange.setRate("USD_TO_EUR", 0.93);

        scanner.close();
    }
}