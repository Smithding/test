package com.tempus.gss.product.ift.api.utils;

import com.tempus.gss.bbp.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * <pre>
 * <b>描述信息</b>
 * <b>Description:渠道配置模板适配工具类</b>
 *
 * <b>Author:</b> Luyongjia
 * <b>Date:</b> 2016年11月23日  10:50
 * <b>Copyright:</b> Copyright ©2016 tempus.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                             Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2016年11月23日  10:50   Luyongjia
 *         new file.
 * </pre>
 */
public class IFTChannelConfigTemplateAdapter {

	protected static Logger logger = LogManager.getLogger(IFTChannelConfigTemplateAdapter.class);

	private static Properties ID_TEMPLATE = new Properties();
	private static Properties TYPE_TEMPLATE = new Properties();
	/**
	 * 模板配置路径
	 */
	private static final String ID_TEMPLATE_SETTING_PATH = "dps/template/IDTemplateSetting.properties";
	private static final String TYPE_TEMPLATE_SETTING_PATH = "dps/template/TypeTemplateSetting.properties";

	/**
	 * 模板文件夹
	 */
	public static final String TEMPLATE_DIR = "product/ift/setting/configure/template";
	/**
	 * 没有找到对应模板
	 */
	private static final String TEMPLATE_NOT_FOUND = "ChannelNotFound";

	static {
		try {
			ID_TEMPLATE.load(IFTChannelConfigTemplateAdapter.class.getClassLoader().getResourceAsStream(ID_TEMPLATE_SETTING_PATH));
			TYPE_TEMPLATE.load(IFTChannelConfigTemplateAdapter.class.getClassLoader().getResourceAsStream(TYPE_TEMPLATE_SETTING_PATH));
		} catch (IOException e) {
			logger.error("渠道模板配置文件加载失败！");
		}
	}

	/**
	 * 通过传入的渠道ID 或者渠道类型去查找
	 *
	 * @param channelId 渠道ID
	 * @param type 渠道类型
	 */
	public static String adapt(long channelId, long type) {
		// 查找ID模板
		String address = ID_TEMPLATE.getProperty(String.valueOf(channelId));
		if (StringUtil.isBlank(address)) {
			// ID模板没有查询到，查询类型模板
			address = TYPE_TEMPLATE.getProperty(String.valueOf(type));
		}
		if (StringUtil.isBlank(address)) {
			// 都没有找到,返回错误页面
			address = TEMPLATE_NOT_FOUND;
		}
		return TEMPLATE_DIR + address;
	}

}
