package com.legou.fastdfstest;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class TestFastDfs {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, MyException {
		
		ClientGlobal.init("D:\\legou\\legou\\legou-manager-web\\src\\main\\resources\\config\\fastdfs.properties");
		
		TrackerClient trackerClient = new TrackerClient();
		
		TrackerServer trackserver = trackerClient.getConnection();
		
		
		StorageServer storageServer = null; //华东机房的 ip 端口 111.111.23.12
		
		StorageClient storageClient = new StorageClient(trackserver, storageServer);
		
		
		String[] upload_file = storageClient.upload_file("D:\\image\\yz.jpg", "jpg", null);
		
		for (String string : upload_file) {
			System.out.println(string);
		}
		

		
		
		
	}

}
