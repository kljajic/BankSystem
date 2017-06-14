package com.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebService {
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "statementService")
	public DefaultWsdl11Definition statementWsdl11Definition(@Qualifier("statement") XsdSchema statement) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("StatementPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(statement);
		return wsdl11Definition;
	}
	
	@Bean(name = "accountStatementSectionService")
	public DefaultWsdl11Definition accountStatementSectionWsdl11Definition(@Qualifier("accountStatementSection") XsdSchema accountStatementSection) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("AccountStatementSectionPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(accountStatementSection);
		return wsdl11Definition;
	}
	
	@Bean(name = "accountStatementRequestService")
	public DefaultWsdl11Definition AccountStatementRequestWsdl11Definition(@Qualifier("accountStatementRequest") XsdSchema accountStatementRequest) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("AccountStatementSectionPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(accountStatementRequest);
		return wsdl11Definition;
	}
	
	
	@Bean
	@Qualifier("accountStatementRequest")
	public XsdSchema accountStatementRequest() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/accountStatementRequest.xsd"));
	}
	
	@Bean
	@Qualifier("accountStatementSection")
	public XsdSchema accountStatementSection() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/accountStatementSection.xsd"));
	}
	
	@Bean
	@Qualifier("accountStatementSectionItem")
	public XsdSchema accountStatementSectionItem() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/accountStatementSectionItem.xsd"));
	}
	
	
	@Bean
	@Qualifier("invoice")
	public XsdSchema invoice() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/invoice.xsd"));
	}
	
	@Bean
	@Qualifier("invoiceItem")
	public XsdSchema invoiceItem() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/invoiceItem.xsd"));
	}
	
	
	@Bean
	@Qualifier("types")
	public XsdSchema types() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/types.xsd"));
	}
	
	@Bean
	@Qualifier("statement")
	public XsdSchema statement() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/statement.xsd"));
	}
}
