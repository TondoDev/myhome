package org.tondo.myhome.svc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.tondo.myhome.svc.data.Price;
import org.tondo.myhome.svc.data.PriceEnvelope;
import org.tondo.myhome.svc.service.FondPriceProviderCSOB;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {FondPriceProviderCSOB.class, MyHomeSvcConfig.class})
@TestPropertySource(properties = {"csob.url=xxx"})
public class ExternalSvcCallTest {

	@Autowired
	private FondPriceProviderCSOB fondPriceProvider;
	
	private RestTemplate restTemplate;
	
	 @Value("${csob.url}")
	 private String foo;
	
	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Test
	public void testUrlSet() {
		System.out.println(this.fondPriceProvider.getUrl());
		System.out.println(foo);
	}
	
		 
	@Test
	public void testCallSvc() {
		String url = this.foo + "?ID=CSOB00000013";
		ResponseEntity<PriceEnvelope> response = restTemplate.getForEntity(url, PriceEnvelope.class);
		System.out.println(response.getStatusCode());
		PriceEnvelope value = response.getBody();
		System.out.println(value.getList().get(0).getDate());
	}
	
	
	@Test
	public void testCsobProvider() {
		Price price = this.fondPriceProvider.getPrices("CSOB00000013").get(0);
		System.out.println(price.getDate());
	}
	
	
	
	
}
