package com.ceadeal.javafxboot.service;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ysh
 * @date 2022/11/21
 */

@Service
public class CaptureServiceImpl implements CaptureService {

    /**
     * @Description:获取网络适配器,当返回List<PcapIf>为空时，说明未获取到网卡
     * @author:hutao
     * @mail:hutao_2017@aliyun.com
     * @date:2021年9月2日
     */
    @Override
    public List<PcapIf> getPcapIf() {
        StringBuilder errbuf = new StringBuilder();
        //定义网卡列表
        List<PcapIf> ifs = new ArrayList<PcapIf>();
        /* 返回值是一个整数结果代码，就像在 C 计数器部分一样。
         * ifs 列表中填充了从 C 函数调用 findAllDevs 返回的相应 C 结构 pcap_if 链表中找到的所有网络设备。
         */
        int statusCode = Pcap.findAllDevs(ifs, errbuf);
        if (statusCode != Pcap.OK) {
            System.err.println("获取网卡失败：" + errbuf.toString());
        }
        return ifs;
    }

    /**
     * @param
     * @Description:开始捕获数据包
     * @author:hutao
     * @mail:hutao_2017@aliyun.com
     * @date:2021年9月2日
     */
    @Override
    public void capturePcap(PcapIf device) {
        //截断此大小的数据包
        int snaplen = Pcap.DEFAULT_JPACKET_BUFFER_SIZE;
        //混杂模式
        int promiscous = Pcap.MODE_PROMISCUOUS;  // capture all packets

        // 参数：timeout 这个参数使得捕获报后等待一定的时间，来捕获更多的数据包，
        // 然后一次操作读多个包，不过不是所有的平台都支持，不支持的会自动忽略这个参数
        int timeout = 60 * 1000;
        //如果发生错误，它将保存一个错误字符串。 错误打开 Live 将返回 null
        StringBuilder errbuf = new StringBuilder();

        Pcap pcap = Pcap.openLive(device.getName(), snaplen, promiscous, timeout, errbuf);
        if (pcap == null) {
            System.err.println("获取数据包失败：" + errbuf.toString());
        }
    }
}
