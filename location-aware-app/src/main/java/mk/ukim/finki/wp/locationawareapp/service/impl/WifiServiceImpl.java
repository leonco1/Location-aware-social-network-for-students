package mk.ukim.finki.wp.locationawareapp.service.impl;

import mk.ukim.finki.wp.locationawareapp.model.exceptions.NoWifiFoundException;
import mk.ukim.finki.wp.locationawareapp.service.WifiService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WifiServiceImpl implements WifiService {

    private static final List<String> availableDevices = new ArrayList<>();
    private static String myNetworkIp;
    private static String deviceIpv4;


    @Override
    public List<String> getIpAddress() throws IOException {
        getDeviceIpv4();
        getMyNetworkIp();

        Thread thread = createScanningThreads(20);

        while (thread.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        printAvailableDevices();
        return availableDevices;

    }
    public static void scanDevices(int start, int end) {
        for (int i = start; i <= end; ++i) {
            try {
                InetAddress addr = InetAddress.getByName(myNetworkIp + i);

                if (addr.isReachable(1000)) {
                    System.out.println("Available: " + addr.getHostAddress());
                    availableDevices.add(addr.getHostAddress());
                } else
                    System.out.println("Not available: " + addr.getHostAddress());

            } catch (IOException ignored) {

            }
        }
    }

    public static void getMyNetworkIp() {
        for (int i = deviceIpv4.length() - 1; i >= 0; --i) {
            if (deviceIpv4.charAt(i) == '.') {
                myNetworkIp = deviceIpv4.substring(0, i + 1);
                break;
            }
        }
    }
    public static List<String> getAllIpv4Networks() throws IOException {
        Process process = Runtime.getRuntime().
                exec("ipconfig");

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return reader.lines().filter(i->i.trim().startsWith("Wireless LAN adapter Wi-Fi:"))
                .flatMap(section -> reader.lines().skip(1).takeWhile(line -> !line.trim().isEmpty()))
                .filter(i->i.trim().startsWith("IPv4 Address"))
                .map(i->i.split(":")[1].trim()).collect(Collectors.toList());

    }
    public static void getDeviceIpv4() {
        try {

            List<String>ipv4_addresses=getAllIpv4Networks();
           // InetAddress localhost = InetAddress.getLocalHost();
            for(String s: ipv4_addresses) {
                InetAddress localhost=InetAddress.getByName(s);
                deviceIpv4 = localhost.getHostAddress();

                if (deviceIpv4.equals("127.0.0.1")) {
                    System.out.println("This PC is not connected to any network!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Thread createScanningThreads(int threads) {
        Thread thread = null;

        for (int i = 0; i < threads; ++i) {
            int start = i * 256 / threads;
            int end = (i + 1) * 256 / threads - 1;
            System.out.println("Thread " + i + " will scan from " + start + " to " + end);

            thread = new Thread(() -> scanDevices(start, end));
            thread.start();
        }

        return thread;
    }

    public static void printAvailableDevices() {
        System.out.println("\nAll Connected devices(" + availableDevices.size() + "):");
        for (String availableDevice : availableDevices) System.out.println(availableDevice);
    }

    private Optional<String> TrimIpAddress(Optional<String> ipaddress)
    {
        String ip_address=ipaddress.orElseThrow(NoWifiFoundException::new);
        String[]ip_parts=ip_address.split("\\.");
        StringBuilder new_ip=new StringBuilder();
        for(int i=0;i<3;i++)
        {
            new_ip.append(ip_parts[i]);
            new_ip.append(".");
        }
        new_ip.deleteCharAt(new_ip.length()-1);
        return Optional.of(new_ip.toString());
    }
}
