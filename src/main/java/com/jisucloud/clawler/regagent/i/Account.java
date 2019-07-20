package com.jisucloud.clawler.regagent.i;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(builderClassName= "AccountBuilder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	private String username;
	
	private String platform;
	
	private String platformName;
	
	private String home;
	
	@JSONField(name = "message")
	private String platformMsg;
	
	private boolean registed;
	
	private Map<String,String> fields;
	
	private Set<String> tags;
	
	public static class AccountBuilder {
		
		private Map<String,String> fields = new HashMap<>();
		
		private Set<String> tags = new HashSet<>();
		
		public AccountBuilder addField(String key,String value) {
			if (StringUtil.isValidString(value)) {
				fields.put(key, value.replaceAll(" ", ""));
			}
			return this;
		}
		
		public AccountBuilder addTag(String ...tags) {
			for (int i = 0; tags != null && i < tags.length; i++) {
				this.tags.add(tags[i]);
			}
			return this;
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (registed != other.registed)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + (registed ? 1231 : 1237);
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	
}
