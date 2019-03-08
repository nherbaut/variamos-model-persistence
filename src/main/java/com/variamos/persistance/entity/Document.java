package com.variamos.persistance.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@org.springframework.data.mongodb.core.mapping.Document
public class Document {
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Binary getFile() {
		return file;
	}

	public void setFile(Binary file) {
		this.file = file;
	}

	@Id
	@Field
	private String path;

	@Field
	private Binary file;
}