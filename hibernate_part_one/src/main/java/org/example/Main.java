package org.example;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.Customer_Product;
import org.example.Product;
import org.example.HibernetUtil;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Session session = HibernetUtil.getSessionFactory().openSession();
        Scanner scanner;
        boolean exitKey = true;

        while (exitKey) {
            scanner = new Scanner(System.in);

            if (scanner.hasNext()) {
                List<String> sc = Arrays.stream(scanner.nextLine().split(" ")).filter(s -> s.length() > 0).collect(Collectors.toList());

                switch (sc.get(0)) {
                    case "/q":
                    case "/quit":
                        exitKey = false;
                        System.out.println("Выход из программы");
                        break;
                    case "/showProductsByPerson":
                        if (sc.size() > 1) {
                            try {
                                String personName = sc.get(1);

                                Session session2 = HibernetUtil.getSessionFactory().getCurrentSession();
                                session2.beginTransaction();

                                List<Customer_Product> productList = session2.createQuery("select cp from Customer_Product cp join cp.customer c join cp.product p where c.name = :name", Customer_Product.class)
                                        .setParameter("name", personName)
                                        .getResultList();

                                System.out.println(personName + " имеет следующие товары:");

                                // Создаем словарь для подсчета количества товаров по ценам
                                Map<Integer, Integer> priceCountMap = new HashMap<>();
                                Map<Integer, String> priceProductMap = new HashMap<>();

                                // Подсчитываем количество товаров по ценам и сохраняем название товара
                                for (Customer_Product cp : productList) {
                                    int price = cp.getValue();
                                    priceCountMap.put(price, priceCountMap.getOrDefault(price, 0) + 1);
                                    priceProductMap.put(price, cp.getProduct().getName());
                                }

                                // Выводим информацию о товарах
                                for (Map.Entry<Integer, Integer> entry : priceCountMap.entrySet()) {
                                    int price = entry.getKey();
                                    int count = entry.getValue();
                                    String productName = priceProductMap.get(price);
                                    System.out.println(productName + ", Цена " + price + " руб: " + count + " штуки");
                                }

                                session2.getTransaction().commit();
                            } catch (Exception e) {
                                System.out.println("Ошибка чтения: человек не найден");
                            }
                        } else {
                            System.out.println("Ошибка чтения: неверное использование команды /showProductsByPerson; проверьте помощь");
                        }
                        break;
                    case "/findPersonsByProductTitle":
                        if (sc.size() > 1) {
                            try {
                                List<Customer_Product> productList = session.createQuery("select cp from Customer_Product cp join cp.product p where p.name = :name", Customer_Product.class)
                                        .setParameter("name", sc.get(1))
                                        .getResultList();

                                System.out.println(sc.get(1) + " куплено следующими клиентами:");

                                // Вывод информации о клиентах
                                for (Customer_Product cp : productList) {
                                    System.out.println(cp.getCustomer().getName());
                                }
                            } catch (Exception e) {
                                System.out.println("Ошибка чтения: товар не найден");
                            }
                        } else {
                            System.out.println("Ошибка чтения: неверное использование команды /findPersonsByProductTitle; проверьте помощь");
                        }
                        break;
                    case "/removePerson":
                        if (sc.size() > 1) {
                            try {
                                session.beginTransaction();

                                session.createQuery("delete from Customer_Product where customer.name = :name")
                                        .setParameter("name", sc.get(1))
                                        .executeUpdate();

                                session.createQuery("delete from Customer where name = :name")
                                        .setParameter("name", sc.get(1))
                                        .executeUpdate();

                                session.getTransaction().commit();

                                System.out.println("Клиент '" + sc.get(1) + "' удален");
                            } catch (Exception e) {
                                System.out.println("Ошибка чтения: клиент не найден");
                            }
                        } else {
                            System.out.println("Ошибка чтения: неверное использование команды /removePerson");
                        }
                        break;
                    case "/removeProduct":
                        if (sc.size() > 1) {
                            try {
                                session.beginTransaction();

                                session.createQuery("delete from Customer_Product where product.name = :name")
                                        .setParameter("name", sc.get(1))
                                        .executeUpdate();

                                session.createQuery("delete from Product where name = :name")
                                        .setParameter("name", sc.get(1))
                                        .executeUpdate();

                                session.getTransaction().commit();

                                System.out.println("Товар '" + sc.get(1) + "' удален");
                            } catch (Exception e) {
                                System.out.println("Ошибка чтения: товар не найден");
                            }
                        } else {
                            System.out.println("Ошибка чтения: неверное использование команды /removeProduct");
                        }
                        break;
                    case "/buy":
                        if (sc.size() > 2) {
                            try {
                                session.beginTransaction();

                                String personName = sc.get(1);
                                String productName = sc.get(2);

                                Customer customer = session.createQuery("from Customer where name = :name", Customer.class)
                                        .setParameter("name", personName)
                                        .uniqueResult();

                                Product product = session.createQuery("from Product where name = :name", Product.class)
                                        .setParameter("name", productName)
                                        .uniqueResult();

                                if (customer != null && product != null) {
                                    if (product.getValue() > 0) {
                                        int price = product.getPrice();

                                        Customer_Product cp = new Customer_Product();
                                        cp.setCustomer(customer);
                                        cp.setProduct(product);
                                        cp.setValue(price);

                                        session.save(cp);

                                        product.buy();

                                        System.out.println(customer.getName() + " купил товар '" + product.getName() + "' за " + price + " руб.");
                                        System.out.println("Остаток товара '" + product.getName() + "' на складе: " + product.getValue());
                                    } else {
                                        System.out.println("Ошибка чтения: товар '" + product.getName() + "' недоступен на складе");
                                    }
                                } else {
                                    System.out.println("Ошибка чтения: клиент или товар не найден");
                                }

                                session.getTransaction().commit();
                            } catch (Exception e) {
                                System.out.println("Ошибка чтения: клиент или товар не найден");
                            }
                        } else {
                            System.out.println("Ошибка чтения: неверное использование команды /buy");
                        }
                        break;
                    case "/updatePrice":
                        if (sc.size() > 2) {
                            try {
                                String productName = sc.get(1);
                                int newPrice = Integer.parseInt(sc.get(2));

                                updatePrice(productName, newPrice, session);

                                System.out.println("Цена товара '" + productName + "' успешно обновлена: " + newPrice + " руб.");
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка: некорректное значение цены");
                            } catch (Exception e) {
                                System.out.println("Ошибка: не удалось обновить цену товара");
                            }
                        } else {
                            System.out.println("Ошибка: неверное использование команды /updatePrice");
                        }
                        break;
                    case "/h":
                    case "/help":
                        System.out.println("/q или /quit - выйти из программы\n" +
                                "/showProductsByPerson <person_name> - показать товары, купленные этим человеком\n" +
                                "/findPersonsByProductTitle <product_title> - показать людей, купивших этот товар\n" +
                                "/findPersonByPriceAndProductTitle <price> <product_title> - показать людей, купивших этот товар по определенной цене\n" +
                                "/removePerson <person_name> - удалить клиента\n" +
                                "/removeProduct <product_title> - удалить товар\n" +
                                "/buy <person_name> <product_title> - купить товар\n" +
                                "/updatePrice <product> <product_price> - изменить цену товара");
                        break;
                    case "/findPersonByPriceAndProductTitle":
                        if (sc.size() > 2) {
                            try {
                                int price = Integer.parseInt(sc.get(1));
                                String productName = sc.get(2);

                                List<Customer_Product> customerProductList = session.createQuery("select cp from Customer_Product cp join cp.product p join cp.customer c where p.name = :name and cp.value = :price", Customer_Product.class)
                                        .setParameter("name", productName)
                                        .setParameter("price", price)
                                        .getResultList();

                                System.out.println("Клиенты, купившие товар '" + productName + "' по цене " + price + " руб.");

                                for (Customer_Product cp : customerProductList) {
                                    System.out.println(cp.getCustomer().getName());
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка чтения: некорректное значение цены");
                            } catch (Exception e) {
                                System.out.println("Ошибка чтения: товар не найден");
                            }
                        } else {
                            System.out.println("Ошибка чтения: неверное использование команды /findPersonByPriceAndProductTitle");
                        }
                        break;
                    default:
                        System.out.println("Ошибка чтения: некорректный ввод");
                        break;
                }
            } else {
                System.out.println("Ошибка чтения: пустой ввод");
            }
        }

        session.close();
        HibernetUtil.getSessionFactory().close();
    }

    public static void updatePrice(String productName, int newPrice, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Product product = session.createQuery("from Product where name = :name", Product.class)
                    .setParameter("name", productName)
                    .uniqueResult();

            if (product != null) {
                product.setPrice(newPrice);
                session.update(product);
                transaction.commit();
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
