package com.ceadeal.javafxboot.service;

import org.jnetpcap.PcapIf;

import java.util.List;

public interface CaptureService {
	/**
	 * @Description:获取网络适配器,当返回List<PcapIf>为空时，说明未获取到网卡
	 * @author:hutao
	 * @mail:hutao_2017@aliyun.com
	 * @date:2021年9月2日
	 */
	public List<PcapIf> getPcapIf();

	/**
	 * @Description:开始捕获数据包
	 * @param
	 * @author:hutao
	 * @mail:hutao_2017@aliyun.com
	 * @date:2021年9月2日
	 */
	public void capturePcap(PcapIf device);
}
