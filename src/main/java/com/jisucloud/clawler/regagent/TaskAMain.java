package com.jisucloud.clawler.regagent;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.deep007.goniub.util.ReadExcelUtils;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

public class TaskAMain {
	
	private static Set<String> telephoneFilter = new HashSet<String>();

	public static boolean hookMain(String[] args) {
		String filepath = null;
		boolean hasFileArg = false;
		try {
			for (int i = 0; args != null && i < args.length; i++) {
				if ("-file".equalsIgnoreCase(args[i])) {
					hasFileArg = true;
					filepath = args[i + 1];
					break;
				}
			}
			if (filepath != null) {
				File outfilepath = new File(filepath +"_out.txt");
				ReadExcelUtils excelReader = new ReadExcelUtils(filepath);
				Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();
				for (int i = 1; i <= map.size(); i++) {
					Set<String> telephones = getTelephones(map.get(i));
					for (String telephone : telephones) {
						if (telephoneFilter.contains(telephone)) {
							continue;
						}
						telephoneFilter.add(telephone);
						Map<String,Boolean> tempTaskAResult = new TempTaskA(telephone).getResult();
						String outLine = telephone + "\t";
						for (Class<? extends PapaSpider> clz : TempTaskA.taska_clzs) {
							PapaSpiderConfig papaSpiderConfig = clz.getAnnotation(PapaSpiderConfig.class);
							boolean isRegist = tempTaskAResult.getOrDefault(papaSpiderConfig.platform(), false);
							String tag = isRegist ? "已注册" : "未注册";
							outLine += papaSpiderConfig.platformName() + "/" + tag + "\t";
						}
						outLine += "\n";
						FileUtils.write(outfilepath, outLine, true);
					}
				}
			}
		} catch (Exception e) {
		}
		return hasFileArg && filepath != null;
	}
	
	//获取手机号
	private static Set<String> getTelephones(Map<Integer,Object> line) {
		Set<String> result = new HashSet<String>();
		Object value = line.get(1);
		if (value != null && value instanceof String && !value.toString().contains(".")) {
			String[] segments = value.toString().split(",");
			for (int i = 0; segments != null && i < segments.length; i++) {
				if (segments[i] != null && segments[i].startsWith("1") && segments[i].length() == 11) {
					result.add(segments[i]);
				}
			}
		}
		return result;
	}
}
