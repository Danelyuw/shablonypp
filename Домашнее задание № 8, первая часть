
interface Beverage {
    String getDescription();
    double cost();
}

class Espresso implements Beverage {
    @Override
    public String getDescription() {
        return "Эспрессо";
    }

    @Override
    public double cost() {
        return 250.0;
    }
}

class Tea implements Beverage {
    @Override
    public String getDescription() {
        return "Чай";
    }

    @Override
    public double cost() {
        return 200.0;
    }
}

class Cocoa implements Beverage {
    @Override
    public String getDescription() {
        return "Какао";
    }

    @Override
    public double cost() {
        return 280.0;
    }
}

class Matcha implements Beverage {
    @Override
    public String getDescription() {
        return "Матча";
    }

    @Override
    public double cost() {
        return 300.0;
    }
}

abstract class BeverageDecorator implements Beverage {
    protected Beverage beverage;

    public BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription();
    }

    @Override
    public double cost() {
        return beverage.cost();
    }
}

class Milk extends BeverageDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", молоко";
    }

    @Override
    public double cost() {
        return beverage.cost() + 50.0;
    }
}

class Sugar extends BeverageDecorator {
    public Sugar(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", сахар";
    }

    @Override
    public double cost() {
        return beverage.cost() + 20.0;
    }
}

class WhippedCream extends BeverageDecorator {
    public WhippedCream(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", сливки";
    }

    @Override
    public double cost() {
        return beverage.cost() + 70.0;
    }
}

class Lemon extends BeverageDecorator {
    public Lemon(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", лимон";
    }

    @Override
    public double cost() {
        return beverage.cost() + 40.0;
    }
}

 public class CafeOrderDemo {
    public static void main(String[] args) {
        Beverage espressoOrder = new Espresso();
        espressoOrder = new Milk(espressoOrder);
        espressoOrder = new Sugar(espressoOrder);
        System.out.println(espressoOrder.getDescription() + "  Стоимость: " + espressoOrder.cost() + " тг");

        Beverage teaOrder = new Tea();
        teaOrder = new Lemon(teaOrder);
        teaOrder = new Sugar(teaOrder);
        System.out.println(teaOrder.getDescription() + " -> Стоимость: " + teaOrder.cost() + " тг");

        Beverage cocoaOrder = new Cocoa();
        cocoaOrder = new WhippedCream(cocoaOrder);
        cocoaOrder = new Milk(cocoaOrder);
        System.out.println(cocoaOrder.getDescription() + " Стоимость: " + cocoaOrder.cost() + " тг");

        Beverage matchaOrder = new Matcha();
        matchaOrder = new WhippedCream(matchaOrder);
        System.out.println(matchaOrder.getDescription() + "  Стоимость: " + matchaOrder.cost() + " тг");
    }
 }
