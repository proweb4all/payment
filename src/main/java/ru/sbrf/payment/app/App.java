package ru.sbrf.payment.app;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class App {
    //приложение (sbol) метод pay (адрес хоста, ip, port, protocol)
    private String hostAddress = "";
    private String ipAddress = "";
    private String port = "";
    private String protocol = "";
    private UserApp user;

    public App(UserApp user) {
        this.user = user;
    }
//    public App(String hostAddress, String ipAddress, String port, String protocol, UserApp user) {
//        this.hostAddress = hostAddress;
//        this.ipAddress = ipAddress;
//        this.port = port;
//        this.protocol = protocol;
//        this.user = user;
//    }

    public String authUser(String password) {
        // Посылает на сервер phone и password, возвращает результат проверки
        boolean res = true;
        String result = "Ошибка авторизации";
        if (res) {
            String name = "Ваня Ветров";
            double balance = 100.0;
            this.user.setAuth(String.valueOf(res));
            this.user.setUserName(name);
            this.user.setBalance(balance);
            result = "Успешная авторизация: " + this.user.getUserName() + " (т." + this.user.getPhone() +
                    ") " + this.user.getBalance() + " (" + this.user.getAuth() + ")" + " (" + password + ")";
        }
        return result;
    }

    public String payApp() {
        if (this.user.getAuth() != "") {
            //Отправить платеж
            return "Платеж от " + this.user.getUserName() + " (т." + this.user.getPhone() + ") на сумму ... отправлен";
        } else {
            return "Вы не авторизованы в приложении. Без этого отправка платежей невозможна.";
        }
        //PaymentApp payment = new PaymentApp();
    }


}
