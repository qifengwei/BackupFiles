package copyToDataserver;

import java.util.Date;
import java.util.logging.Logger;

public class CopyMain {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Logger log = Logger.getLogger("SoftwareLog.Log");
		log.info("开始执行程序");
		Configure con = new Configure();
		String [] content = con.getxmlContent();
		log.info(String.format("源地址：%s，目标地址：%s,最后一次修改时间：%s", content[0],content[1],content[2]));
		new Copy(content[0], content[1], new Date(Long.parseLong(content[2])), new Date()).start();
		con.refreshModifyTime(new Date().getTime());
		System.out.println("主线程结束");
	}

}
