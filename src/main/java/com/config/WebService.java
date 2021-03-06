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
	public DefaultWsdl11Definition accountStatementRequestWsdl11Definition(@Qualifier("accountStatementRequest") XsdSchema accountStatementRequest) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("AccountStatementRequestPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(accountStatementRequest);
		return wsdl11Definition;
	}
	
	@Bean(name = "rtgsRequestResponseService")
	public DefaultWsdl11Definition rtgsRequestResponseWsdl11Definition(@Qualifier("rtgsRequest") XsdSchema rtgsRequest) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("RtgsRequestResponsePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(rtgsRequest);
		return wsdl11Definition;
	}
	
	@Bean(name = "mt910ResponseService")
	public DefaultWsdl11Definition mt910ResponseWsdl11Definition(@Qualifier("responseReciever") XsdSchema responseReciever) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("Mt910ResponsePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(responseReciever);
		return wsdl11Definition;
	}
	
	@Bean(name = "mt102ResponseService")
	public DefaultWsdl11Definition mt102ResponseWsdl11Definition(@Qualifier("clearingAndSettlement") XsdSchema responseReciever) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("Mt102ResponsePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(responseReciever);
		return wsdl11Definition;
	}
	
	@Bean(name = "userRequestService")
	public DefaultWsdl11Definition userWsdl11Definition(@Qualifier("user") XsdSchema userRequest) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("userPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setSchema(userRequest);
		return wsdl11Definition;
	}
	
	
	@Bean
	@Qualifier("accountStatementRequest")
	public XsdSchema accountStatementRequest() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/accountStatementRequest.xsd"));
	}
	
	@Bean
	@Qualifier("clearingAndSettlement")
	public XsdSchema clearingAndSettlement() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/clearingAndSettlement.xsd"));
	}
	
	@Bean
	@Qualifier("clearingAndSettlementItem")
	public XsdSchema clearingAndSettlementItem() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/clearingAndSettlementItem.xsd"));
	}
	
	@Bean
	@Qualifier("user")
	public XsdSchema user() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/user.xsd"));
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
	
	@Bean
	@Qualifier("rtgsRequest")
	public XsdSchema rtgsRequest() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/rtgsRequest.xsd"));
	}
	
	@Bean
	@Qualifier("responseReciever")
	public XsdSchema responseReciever() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/types/responseReciever.xsd"));
	}
}
