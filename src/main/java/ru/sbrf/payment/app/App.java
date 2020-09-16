package ru.sbrf.payment.app;

public class App {
    //приложение (sbol) метод pay (адрес хоста, ip, port, protocol)
    private String hostAddress = "";
    private String ipAddress = "";
    private String port = "";
    private String protocol = "";

    public App() {}
    public App(String hostAddress, String ipAddress, String port, String protocol) {
        this.hostAddress = hostAddress;
        this.ipAddress = ipAddress;
        this.port = port;
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return hostAddress + ", " + ipAddress + ", " + port + ", " + protocol;
    }


}
