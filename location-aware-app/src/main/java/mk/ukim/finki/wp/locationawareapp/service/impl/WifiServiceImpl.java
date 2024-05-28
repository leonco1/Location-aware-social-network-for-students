package mk.ukim.finki.wp.locationawareapp.service.impl;

import mk.ukim.finki.wp.locationawareapp.model.exceptions.NoWifiFoundException;
import mk.ukim.finki.wp.locationawareapp.service.WifiService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
@Service
public class WifiServiceImpl implements WifiService {
    @Override
    public Optional<String> getIpAddress() throws IOException {
        Process process = Runtime.getRuntime().
                exec("ipconfig");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return TrimIpAddress(reader.lines().filter(i->i.trim().startsWith("Wireless LAN adapter Wi-Fi:"))
                .flatMap(section -> reader.lines().skip(1).takeWhile(line -> !line.trim().isEmpty()))
                .filter(i->i.trim().startsWith("IPv4 Address"))
                .map(i->i.split(":")[1].trim()).findFirst());
    }
    private Optional<String> TrimIpAddress(Optional<String> ipaddress)
    {
        String ip_address=ipaddress.orElseThrow(NoWifiFoundException::new);
        System.out.println(ip_address);
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
    @Override
    public void SendMessage() throws IOException {
        String ip_address=this.getIpAddress().orElseThrow(NoWifiFoundException::new);
        String subnet_mask=this.getSubnetMask().orElseThrow(NoWifiFoundException::new);
        int subnet_length=CalculateSubnetLength(subnet_mask);
    }

    private Integer CalculateSubnetLength(String subnet)
    {
        int sum=0;
        String []subnet_parts=subnet.split("\\.");
        for(String s :subnet_parts)
        {
            Integer part=Integer.parseInt(s);
            ++part;
            part= (int) (Math.log(part)/Math.log(2));
            sum+=part;
        }
        return sum;
    }

    @Override
    public Optional<String> getSubnetMask() throws IOException {
        Process process = Runtime.getRuntime().
                exec("ipconfig");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return reader.lines().filter(i->i.trim().startsWith("Subnet Mask")).map(i->i.split(":")[1].trim()).findFirst();
    }

}
