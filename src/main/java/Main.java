import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

record Car(String name, int speed) {}

class Race {
    public static ArrayList<Car> race (ArrayList<Car> cars) {
        //ArrayList на случай, если будут победители с одинаковой скоростью
        Car leader = new Car("default", 0);
        ArrayList<Car> winners = new ArrayList<>();
        for (Car car : cars) {
            if (car.speed() * 24 == leader.speed() * 24) {
                winners.add(car);
            }
            if (car.speed() * 24 > leader.speed() * 24) {
                winners.clear();
                winners.add(car);
                leader = car;
            }
        }
        return winners;
    }
}

public class Main {
    public static boolean contains (ArrayList<Car> cars, String name) {
        for (Car car : cars) {
            if (name.equals(car.name())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        /*Понимаю, что код выглядит бессмысленно сложным, но я хотел отработать все изученные
        навыки и даже больше. Жёстко кайфанул, пока писал его
         */
        ArrayList<Car> cars = new ArrayList<>();
        int carCounter = 0;
        while (true) {
            System.out.format("Введите название машины %d и её скорость (от 1 до 250 включительно): ",
                    carCounter + 1);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] nameAndSpeed = input.split(" ");
            if (nameAndSpeed.length != 2) {
                System.out.println("Некорректный ввод");
            } else {
                String name = nameAndSpeed[0];
                if (contains(cars, name)) {
                    System.out.println("Машина с таким названием уже существует");
                } else {
                    /*Обработка ошибки ввода, потому что хочу, чтобы пользователь вводил данные о
                    названии машины и скорости в одну строку. При конвертации из String в int
                    может возникнуть NumberFormatException
                    */
                    try {
                        int speed = Integer.parseInt(nameAndSpeed[1]);
                        if (speed > 0 && speed <= 250) {
                            Car car = new Car(name, speed);
                            cars.add(car);
                            carCounter++;
                            System.out.format("Машина %s со скоростью %d добавлена успешно\n", name, speed);
                        } else {
                            System.out.println("Введена неверная скорость");
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("Некорректная скорость");
                    }
                }
            }
            if (carCounter == 3) {
                System.out.println("Все машины добавлены успешно");
                for (Car car : cars) {
                    System.out.format("Машина %s со скоростью %d проехала %d километров\n",
                            car.name(), car.speed(), car.speed() * 24);
                }

                ArrayList<Car> winners = Race.race(cars);
                Iterator<Car> iterator = winners.iterator();
                //Итератор чисто для разнообразия
                while (iterator.hasNext()) {
                    Car winner = iterator.next();
                    System.out.format("Победила машина %s со скоростью %d, она проехала %d километров\n",
                            winner.name(), winner.speed(), winner.speed() * 24);
                    //Опять же цикл на случай множественных победителей с одинаковой скоростью
                }
                break;
            }
        }
    }
}