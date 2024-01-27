package org.Griffty;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import static org.Griffty.enums.InputType.*;

public class Main {
    public static void main(String[] args) {
        new SingleDeviceGameController(CLI);
    }
}
