package com.landg.mfservice.records.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.resource.cci.Record;
import javax.resource.cci.Streamable;

/**
 * 
 * Base implementation class which will be used as an input/output streaming for the CTG call. * 
 *
 */

public class RecordImpl implements Record, Streamable {
	
	private byte[] buffer;
	
	private String recordName;
	
	private String recordShortDescription;
	
	public RecordImpl() {
		this.recordName = null;
		this.recordShortDescription = null;
	}
	
	public RecordImpl(String param1, String param2) {
		this.recordName = param1;
		this.recordShortDescription = param2;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getRecordName() {
		return this.recordName;
	}

	public String getRecordShortDescription() {
		return this.recordShortDescription;
	}

	public void setRecordName(String name) {
		this.recordName = name;
	}

	public void setRecordShortDescription(String description) {
		this.recordShortDescription = description;
	}
	
	/**
	 * @generated
	 * @see javax.resource.cci.Record#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		return (super.clone());
	}

	public void read(InputStream istream) throws IOException {
		byte[] input = new byte[istream.available()];
		istream.read(input);
		buffer = input;
	}

	public void write(OutputStream ostream) throws IOException {
		ostream.write(buffer);//, 0, getSize());
	}
	
	public byte[] getBytes() {
		return (buffer);
	}

	/**
	 * @generated
	 * @see com.ibm.etools.marshall.RecordBytes#setBytes
	 */
	public void setBytes(byte[] bytes) {
		if ((bytes != null) && (bytes.length != 0))
			buffer = bytes;
	}

}
