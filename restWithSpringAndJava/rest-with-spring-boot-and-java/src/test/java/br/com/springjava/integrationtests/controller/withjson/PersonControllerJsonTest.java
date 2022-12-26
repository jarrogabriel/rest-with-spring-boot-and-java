package br.com.springjava.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.springjava.configs.TestConfigs;
import br.com.springjava.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.springjava.integrationtests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest{

	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonVO person;
	
	
	@BeforeAll
	public static void setup() {
		
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonVO();
		
	}
	
	
	@Test
	@Order(1)
	void testCreate() throws JsonMappingException, JsonProcessingException {
		
		mockPerson();
		
		
		//mocka a pessoa e depois configura a especificação do CORS
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://localhost:8080")
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
	var content = given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(person)
				.when()
				.post()
		.then()
			.statusCode(200)
		.extract()
			.body()
				.asString();
	
	PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
	person = createdPerson;
	
	assertTrue(createdPerson.getId() > 0);
	
	assertNotNull(createdPerson.getId());
	assertNotNull(createdPerson.getFirstName());
	assertNotNull(createdPerson.getLastName());
	assertNotNull(createdPerson.getAddress());
	assertNotNull(createdPerson.getGender());
	
		
	}
	@Test
	@Order(2)
	void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		
		mockPerson();
		
		
		//mocka a pessoa e depois configura a especificação do CORS
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://localhost:8081")
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(person)
				.when()
				.post()
				.then()
				.statusCode(403)
				.extract()
				.body()
				.asString();
		
		
		assertNotNull(content);
		assertEquals("Invalid CORS request" , content);
		
		
		
		
	}


	private void mockPerson() {

		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New York City, New York, US");
		person.setGender("Male");
		
	}

}
