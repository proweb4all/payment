package ru.sbrf.sbol;

public class AppSbol {
    //приложение (sbol) метод pay (адрес хоста, ip, port, protocol)
    private String hostAddress = "";
    private String ipAddress = "";
    private String port = "";
    private String protocol = "";

    public AppSbol() {}
    public AppSbol(String hostAddress, String ipAddress, String port, String protocol) {
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
