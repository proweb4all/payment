package ru.sbrf.payment.common;

import java.awt.*;
import java.applet.*;

/*
<applet code="" width=200 height=60></applet>
 */
public class SimpleApplet extends Applet {
    public void paint(Graphics g) {
        g.drawString("Простейший аплет", 20,20);
    }
}
