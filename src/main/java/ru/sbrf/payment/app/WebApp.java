package ru.sbrf.payment.app;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import ru.sbrf.payment.common.*;
import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
import ru.sbrf.payment.server.BaseServer;
import lombok.*;
@ToString
@Getter
@Slf4j
public class WebApp implements IApp {
    private Settings settings = new Settings();
    private UserApp user = new UserApp();

    static <T> T validationValue(IValidationValueFunc<T> f, T s) throws SomeException {
        return f.func(s);
    }

    @Override
    public void runApp() {
        log.info("=====================================================");
        log.info("☎☎☎ Приложение Payment запущено ");
        int code = 11;
        boolean auth;
        String inStr;
        do {
            System.out.println("=====================================================");
            System.out.println("☎☎☎ ПРИЛОЖЕНИЕ ДЛЯ ОПЛАТЫ МОБИЛЬНОГО ТЕЛЕФОНА ☎☎☎");
            System.out.println("=====================================================");
            System.out.println("Работа с сервером " + BaseServer.serverLink.get().getNameServer());
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
                    try {
                        pass = validationValue(Validation::checkPass, SomeMethods.getUserStr());
                    } catch (SomeException e) {
                        System.out.println(e);
                        log.info(String.valueOf(e));
                        break;
                    }
                    if (serverAvailable()) {
                        authUserApp(BaseServer.serverLink.get().authUserServer(phone, pass));
                    } else {
                        System.out.println("Проблема авторизации: нет ссылки на сервер.");
                        log.info("Проблема авторизации: нет ссылки на сервер.");
                    }
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
                            payApp(payeePhone, amount);
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

    @Override
    public boolean authUserApp(UserApp userApp) {
        // Получить пользователя после попытки авторизации
        this.user = userApp;
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

    @Override
    public boolean payApp(String payeePhone, double amount) {
        // Создание платежа
        if (!serverAvailable()) {
            System.out.println("Проблема проведения платежа: нет ссылки на сервер.");
            log.info("Проблема проведения платежа: нет ссылки на сервер.");
            return false;
        }
        Date dateNow = new Date();
        Payment payment = new Payment(Payment.createPaymentID(this.user.getPhone(), dateNow),
                this.user.getPhone(), this.user.getAccount(), dateNow, PaymentStatus.PS1, payeePhone, amount);
        System.out.println(payment.getPaymentStatus().getDescr() + " №" + payment.getId() + " от " + this.getUser().getUserName() + " (т." + payment.getPayerPhone()
                + ") пользователю (т." + payment.getPayeePhone() + ") на сумму " + payment.getAmount() + "руб.");
        log.info(payment.getPaymentStatus().getDescr() + " №" + payment.getId() + " от " + this.getUser().getUserName() + " (т." + payment.getPayerPhone()
                + ") пользователю (т." + payment.getPayeePhone() + ") на сумму " + payment.getAmount() + "руб.");
        // Проведение платежа на сервере
        payment = BaseServer.serverLink.get().payServer(payment);
        // Возврат управления с сервера
        if (payment.getPaymentStatus() == PaymentStatus.PS12 || payment.getPaymentStatus() == PaymentStatus.PS13) {
            return false;
        }
        if (updateBalanceUserApp(payment.getAmount(), this.getUser())) {
            BaseServer.serverLink.get().setPaymentStatusAndLogging(payment, PaymentStatus.PS6);
        } else {
            BaseServer.serverLink.get().setPaymentStatusAndLogging(payment, PaymentStatus.PS16);
        }
        System.out.printf("=== Успешно проведен платеж №%s от %s (т.%s) пользователю (т.%s) на сумму %.2fруб. ===\n",
                   payment.getId(), this.getUser().getUserName(), payment.getPayerPhone(), payment.getPayeePhone(), payment.getAmount());
        log.info("=== Успешно проведен платеж №" + payment.getId() + " от " + this.getUser().getUserName() + " (т." + payment.getPayerPhone()
                + ") пользователю (т." + payment.getPayeePhone() + ") на сумму " + payment.getAmount() + "руб. ===");
        return true;
    }

    boolean updateBalanceUserApp(double amount, UserApp user) {
        // Корректировка остатка клиента в приложении
        user.setBalance(user.getBalance() - amount);
        return true;
    }

    boolean serverAvailable() {
        return BaseServer.serverLink.isPresent();
    }

}
