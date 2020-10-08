package ru.sbrf.payment.app;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import ru.sbrf.payment.common.*;
import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
import ru.sbrf.payment.db.PaymentsDB;
import ru.sbrf.payment.db.UsersDB;
import ru.sbrf.payment.server.OperatorAPI;

import lombok.*;
@ToString
@Getter
@NoArgsConstructor
@Slf4j

public class WebApp implements App {
    private Settings settings = new Settings();
    private UserApp user = new UserApp();
    private UsersDB usersDB = new UsersDB();
    private PaymentsDB paymentsDB = new PaymentsDB();

    static <T> T validationValue(ValidationValueFunc<T> f, T s) throws SomeException {
        return f.func(s);
    }

    public void init() {
        usersDB.init();
        paymentsDB.init();
    }

    public void runApp() {
        init();
        log.info("=====================================================");
        log.info("☎☎☎ Приложение Payment запущено ");
        int code = 11;
        boolean auth = false;
        String inStr;
        do {
            System.out.println("=====================================================");
            System.out.println("☎☎☎ ПРИЛОЖЕНИЕ ДЛЯ ОПЛАТЫ МОБИЛЬНОГО ТЕЛЕФОНА ☎☎☎");
            System.out.println("=====================================================");
            auth = (this.getUser().getAuthStatus() == AuthStatus.A1);
            if (auth) {
                System.out.println("+++ Вы авторизованы как " + getUser().getUserName() + ", т." + getUser().getPhone() + ", остаток средств " + getUser().getBalance() + "руб.");
                System.out.println("==============================================\nВведите номер дальнейшего действия: ");
                System.out.println("1 - авторизация в приложении");
                System.out.println("2 - оплата мобильного телефона");
            } else {
                System.out.println("!-- Вы не авторизованы в приложении");
                System.out.println("==============================================\nВведите номер дальнейшего действия: ");
                System.out.println("1 - авторизация в приложении");
            }
            System.out.print("0 - выход\n> ");
            do {
                inStr = SomeMethods.getUserStr();
                if (inStr.length() > 0) { break; }
            } while (true);
            code = Character.getNumericValue(inStr.charAt(0));
            log.info("Выбран пункт меню: " + code);
            switch (code) {
                case 1:
                    System.out.print("Введите свой номер телефона (10 цифр): ");
                    String phone, pass;
                    try {
                        phone = validationValue(Validation::checkPhone, SomeMethods.getUserStr());
                    } catch (SomeException e) {
                        System.out.println(e);
                        log.info(String.valueOf(e));
                        break;
                    }
                    System.out.print("Введите пароль: ");
//                    // Вариант сокрытия символов пароля при вводе с консоли. Не работает из-под IDE.
//                    Console console = System.console();
//                    char passwordChars[] = console.readPassword();
//                    String pass = new String(passwordChars);
                    try {
                        pass = validationValue(Validation::checkPass, SomeMethods.getUserStr());
                    } catch (SomeException e) {
                        System.out.println(e);
                        log.info(String.valueOf(e));
                        break;
                    }
                    authUser(phone, pass, getUsersDB());
                    break;
                case 2:
                    if (auth) {
                        System.out.print("Введите номер телефона получателя платежа (10 цифр): ");
                        String payeePhone;
                        try {
                            payeePhone = validationValue(Validation::checkPhone, SomeMethods.getUserStr());
                        } catch (SomeException e) {
                            System.out.println(e);
                            log.info(String.valueOf(e));
                            break;
                        }
                        System.out.print("Введите сумму платежа в пределах Вашего остатка (0 - отмена): ");
                        double amount = 0.0;
                        do {
                            try {
                                amount = Validation.checkAmount(SomeMethods.getUserStr(), getUser().getBalance());
                                break;
                            } catch (SomeException e) {
                                System.out.println(e);
                                log.info(String.valueOf(e));
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка. Вы не ввели число. Введите сумму платежа в пределах Вашего остатка (0 - отмена): ");
                                log.info("Ошибка ввода суммы платежа: не введено число.");
                            }
                        } while (true);
                        if (amount != 0) {
                            payApp(payeePhone, amount, getUsersDB());
                        } else {
                            System.out.println("--- Вы отменили платеж.");
                            log.info("--- Пользователь отменил платеж.");
                        }
                    } else {
                        System.out.println("Введите правильное значение...");
                    }
                    break;
                case 0:
                    System.out.println("====================================");
                    System.out.println("Вы вышли из приложения. До свидания!");
                    System.out.println("====================================");
                    break;
                default:
                    System.out.print("Введите правильное значение...");
                    break;
            }
            System.out.println();
        } while (code != 0);
        log.info("☎☎☎ Приложение Payment закрыто");
        log.info("=====================================================");
    }

    public boolean authUser(String phone, String password, UsersDB usersDB) {
        // Сделать отсылку на сервер phone и password, возвратить результат проверки boolean
        log.info("Попытка авторизации пользователя с т." + phone + "...");
        this.user = usersDB.authUser(phone, password);
        boolean result = (this.user.getAuthStatus() == AuthStatus.A1);
        if (result) {
            System.out.println(this.user.getAuthStatus().getDescr() + " " + this.user.getUserName() + " (т." + this.user.getPhone() + ") " + this.user.getBalance());
            log.info(this.user.getAuthStatus().getDescr() + " " + this.user.getUserName() + " (т." + this.user.getPhone() + ") " + this.user.getBalance());
        } else {
            System.out.println(this.user.getAuthStatus().getDescr());
            log.info(this.user.getAuthStatus().getDescr());
        }
        return result;
    }

    public boolean payApp(String payeePhone, double amount, UsersDB usersDB) {
        // Создание платежа
        Date dateNow = new Date();
        Payment payment = new Payment(PaymentsDB.createPaymentID(this.user.getPhone(), dateNow),
                this.user.getPhone(), this.user.getAccount(), dateNow, PaymentStatus.PS1, payeePhone, amount);
        System.out.println(payment.getPaymentStatus().getDescr() + " №" + payment.getId() + " от " + this.getUser().getUserName() + " (т." + payment.getPayerPhone()
                + ") пользователю (т." + payment.getPayeePhone() + ") на сумму " + payment.getAmount() + "руб.");
        log.info(payment.getPaymentStatus().getDescr() + " №" + payment.getId() + " от " + this.getUser().getUserName() + " (т." + payment.getPayerPhone()
                + ") пользователю (т." + payment.getPayeePhone() + ") на сумму " + payment.getAmount() + "руб.");
        // Проведение платежа
        if (checkPayment(payment.getId())) {
            payment.setPaymentStatus(PaymentStatus.PS2);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS12);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            return false;
        }
        if (paymentToAPI(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS3);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS13);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            return false;
        }
        if (usersDB.paymentToUsersDB(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS4);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS14);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            //return false;
        }
        if (updatePaymentsDB(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS5);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS15);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            //return false;
        }
        if (updateBalanceUserApp(payment.getAmount(), this.getUser())) {
            payment.setPaymentStatus(PaymentStatus.PS6);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS16);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        }
        System.out.printf("=== Успешно проведен платеж №%s от %s (т.%s) пользователю (т.%s) на сумму %.2fруб. ===\n",
                   payment.getId(), this.getUser().getUserName(), payment.getPayerPhone(), payment.getPayeePhone(), payment.getAmount());
        log.info("=== Успешно проведен платеж №" + payment.getId() + " от " + this.getUser().getUserName() + " (т." + payment.getPayerPhone()
                + ") пользователю (т." + payment.getPayeePhone() + ") на сумму " + payment.getAmount() + "руб. ===");
        return true;
    }

    boolean checkPayment(String paymentID) {
        // Проверка реквизитов платежа по БД платежей
        return paymentsDB.checkPaymentID(paymentID);
    }
    boolean paymentToAPI(Payment payment) {
        // Отправка и возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        return operAPI.procAPI(payment);
    }
    boolean updatePaymentsDB(Payment payment) {
        // Запись платежа в БД платежей
        return paymentsDB.addPaymentToDB(payment);
    }
    boolean updateBalanceUserApp(double amount, UserApp user) {
        // Корректировка остатка клиента в приложении
        user.setBalance(user.getBalance() - amount);
        return true;
    }

}
