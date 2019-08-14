package com.jisucloud.clawler.regagent.service;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class TaskStatus {

	private final String id;
	
	public TaskStatus(String id) {
		this.id = id;
	}
	
	@JSONField(serialize = false)
	private Set<String> papaClzs = new HashSet<>();
	
	private float papaNums;
	
	public void addPapaClzName(String papaClz) {
		if (papaClz != null && !papaClz.isEmpty()) {
			papaClzs.add(papaClz);
			int size = papaClzs.size();
			if (size > papaNums) {
				papaNums = size;
			}
		}
	}
	
	public void costPapaClzName(String papaClz) {
		if (papaClz != null && !papaClz.isEmpty()) {
			papaClzs.remove(papaClz);
		}
	}
	
	public float getProgress() {
		float progress = 1.0f - (papaClzs.size() / papaNums);
		return progress;
	}
	
	public Set<String> getLimitTask() {
		return papaClzs;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskStatus other = (TaskStatus) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
}
