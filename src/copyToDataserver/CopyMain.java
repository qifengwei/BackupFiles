package copyToDataserver;

import java.util.Date;
import java.util.logging.Logger;

public class CopyMain {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Logger log = Logger.getLogger("SoftwareLog.Log");
		log.info("��ʼִ�г���");
		Configure con = new Configure();
		String [] content = con.getxmlContent();
		log.info(String.format("Դ��ַ��%s��Ŀ���ַ��%s,���һ���޸�ʱ�䣺%s", content[0],content[1],content[2]));
		new Copy(content[0], content[1], new Date(Long.parseLong(content[2])), new Date()).start();
		con.refreshModifyTime(new Date().getTime());
		System.out.println("���߳̽���");
	}

}
