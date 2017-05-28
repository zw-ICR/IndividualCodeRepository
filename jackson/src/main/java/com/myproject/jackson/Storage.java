package com.myproject.jackson;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
@JsonRootName("storage")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Storage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("storage_name")
	private String storageName;//后端存储名称
	@JsonProperty("storage_device")
	private String storageDevice;//对应存储设备uuid
	@JsonProperty("metadatas")
	private Metadatas metadatas;
	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getStorageDevice() {
		return storageDevice;
	}

	public void setStorageDevice(String storageDevice) {
		this.storageDevice = storageDevice;
	}

	public Metadatas getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(Metadatas metadatas) {
		this.metadatas = metadatas;
	}

	public class Metadatas{
		@JsonProperty("rest_url")
		private String restURL;//REST URL项，设备类型为huawei时必填
		@JsonProperty("storage_pool_name")
		private String storagePoolName;//存储资源池名称
		@JsonProperty("user_name")
		private String userName;//用户名
		@JsonProperty("password")
		private String password;//密码
		@JsonProperty("controller_ip")
		private String controllerIp;//控制器IP地址，设备类型为netapp时必填
		@JsonProperty("storage_ip")
		private String storageIp;//存储ip地址，设备类型为huawei时必填
		@JsonProperty("nfs_path")
		private String nfsPath;//NFS共享路径，选择nfs协议时必填
		@JsonProperty("storage_protocol")
		private StorageProtocol storageProtocol;//存储协议:iscsi，fc，nfs
		
		public String getRestURL() {
			return restURL;
		}
		public void setRestURL(String restURL) {
			this.restURL = restURL;
		}
		public String getStoragePoolName() {
			return storagePoolName;
		}
		public void setStoragePoolName(String storagePoolName) {
			this.storagePoolName = storagePoolName;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getControllerIp() {
			return controllerIp;
		}
		public void setControllerIp(String controllerIp) {
			this.controllerIp = controllerIp;
		}
		public String getStorageIp() {
			return storageIp;
		}
		public void setStorageIp(String storageIp) {
			this.storageIp = storageIp;
		}
		public String getNfsPath() {
			return nfsPath;
		}
		public void setNfsPath(String nfsPath) {
			this.nfsPath = nfsPath;
		}
		public StorageProtocol getStorageProtocol() {
			return storageProtocol;
		}
		public void setStorageProtocol(StorageProtocol storageProtocol) {
			this.storageProtocol = storageProtocol;
		}
		
	}
	
	public enum StorageProtocol{
		ISCSI("iscsi"),FC("fc"),NFS("nfs"),UNRECOGNIZED("unrecognized");
		private String protocol;
		StorageProtocol(String protocol){
			this.protocol = protocol;
		}
		
		@JsonValue
		public String getProtocol(){
			return this.protocol;
		}
		
		@JsonCreator
		public static StorageProtocol value(String protocol){
			for(StorageProtocol storageProtocol:StorageProtocol.values()){
				if(storageProtocol.getProtocol().equals(protocol)){
					return storageProtocol;
				}
			}
			
			return UNRECOGNIZED;
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		String str = "{"
						+ "\"storage_name\": \"存储名称\",\"storage_device\":\"存储设备\""
						+ ",\"metadatas\":{"
							+ "\"rest_url\": \"www.baidu.com\",\"storage_pool_name\":\"pool\""
							+ ",\"user_name\": \"root\",\"password\":\"123456\""
							+ ",\"controller_ip\": \"192.168.0.1\",\"storage_ip\":\"192.168.25.68\""
							+ ",\"nfs_path\": \"nfs\",\"storage_protocol\":\"fc\""
							+"}"
						+ "}";
		ObjectMapper objectMapper = new ObjectMapper();
		Storage storage = objectMapper.readValue(str, Storage.class);
		System.out.println(storage.getStorageDevice());
		System.out.println(storage.getMetadatas().getRestURL());
		System.out.println(storage.getMetadatas().getPassword());
		System.out.println(storage.getMetadatas().getStorageIp());
		System.out.println(storage.getMetadatas().getStorageProtocol());
	}

}
