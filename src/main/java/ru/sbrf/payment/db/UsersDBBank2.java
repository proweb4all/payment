package ru.sbrf.payment.db;

public class UsersDBBank2 extends UsersDB{
    @Override
    public void init() {
        getUsersDB().put("9101111111", new UserRecord("111111", "9101111111", "Ваня Ветров", "YYYYY810X53001111111", 300.0));
        getUsersDB().put("9102222222", new UserRecord("222222", "9102222222", "Клава Форточкина", "YYYYY810X53002222222", 400.0));
        getUsersDB().put("9103333333", new UserRecord("333333", "9103333333", "Никифор Ляпис-Трубецкой", "YYYYY810X53003333333", 500.0));
    }
}