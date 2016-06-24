package connection;

import java.util.Enumeration;

import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;

public class MyConnectionMetaData implements ConnectionMetaData {

	private int jmsMajorVersion;
	private int jmsMinorVersion;
	private int providerMajorVersion;
	private int providerMinorVersion;
	private String jmsProviderName;
	private String jmsVersion;
	private String providerVersion;
	
	@Override
	public int getJMSMajorVersion() throws JMSException {
		return jmsMajorVersion;
	}

	@Override
	public int getJMSMinorVersion() throws JMSException {
		return jmsMinorVersion;
	}

	@Override
	public String getJMSProviderName() throws JMSException {
		return jmsProviderName;
	}

	@Override
	public String getJMSVersion() throws JMSException {
		return jmsVersion;
	}

	@Override
	public Enumeration getJMSXPropertyNames() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getProviderMajorVersion() throws JMSException {
		return providerMajorVersion;
	}

	@Override
	public int getProviderMinorVersion() throws JMSException {
		return providerMinorVersion;
	}

	@Override
	public String getProviderVersion() throws JMSException {
		return providerVersion;
	}

}
