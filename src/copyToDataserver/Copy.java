package copyToDataserver;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Copy {
	
	private String sourceAddress;
	private String destinationAddress;
	private Date lastModitifyTime;
	private Date nowTime;
	private Logger log = Logger.getLogger("SoftwareLog.Log");
	
	
	/**
	 * @param sourceAddress
	 * @param destinationAddress
	 * @param lastModitifyTime
	 * @param nowTime
	 */
	public Copy(String sourceAddress, String destinationAddress, Date lastModitifyTime, Date nowTime) {
		super();
		log.info("Copy构造函数");
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.lastModitifyTime = lastModitifyTime;
		this.nowTime = nowTime;
	}
	
	public void start() {
		log.info("Copy.start()");
		startCopy(sourceAddress, destinationAddress);
	}
	
	public void startCopy(String source, String dest) {
		File sourcefile = new File(source);
		File destinationfile = new File(dest);
		if(!destinationfile.exists()) {
			destinationfile.mkdir();
		}
		
		for(File childFile:sourcefile.listFiles()) {
			if(childFile.isFile()) {
				if(childFile.lastModified()>lastModitifyTime.getTime()) {
					syncCopy(childFile.getPath(),dest+'\\'+childFile.getName());
				}
				else {
					log.info(String.format("%s不需要更新", childFile.getPath()));
				}
			}
			else {
				startCopy(childFile.getPath(),dest+'\\'+childFile.getName());
			}
		}
	}
	
	public void syncCopy(String source, String dest) {
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					log.info(String.format("%s开始复制", source));
					Files.copy(Paths.get(source), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
					System.out.println(String.format("%s复制完成", source));
					log.info(String.format("%s复制完成", source));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		); 
		t.start();
	}
	
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public Date getLastModitifyTime() {
		return lastModitifyTime;
	}
	public void setLastModitifyTime(Date lastModitifyTime) {
		this.lastModitifyTime = lastModitifyTime;
	}
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	
}
