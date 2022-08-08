package com.eaton.example;

import com.eaton.example.dtos.DtoDevice;
import com.eaton.example.dtos.DtoMessage;
import com.eaton.example.model.Device;
import com.eaton.example.model.DeviceMessage;
import com.eaton.example.model.ResponseError;
import com.eaton.example.rest_controller.MessageRestController;
import com.eaton.example.service.DeviceMessageService;
import com.eaton.example.service.DeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class ExampleApplicationTests {

	private static final Logger logger = LogManager.getLogger(ExampleApplicationTests.class);
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private DeviceService deviceService;

	private DeviceMessageService deviceMessageService;

	@Autowired
	public ExampleApplicationTests(DeviceService deviceService, DeviceMessageService deviceMessageService) {
		this.deviceService = deviceService;
		this.deviceMessageService = deviceMessageService;
	}

	@Test
	void contextLoads() {
		assertThat(deviceService).isNotNull();
		assertThat(deviceMessageService).isNotNull();
	}

	@Test
	void testCreateDevice() throws URISyntaxException, JsonProcessingException {
		final String baseUrl = "http://localhost:"+port+"/api/device";
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		DtoDevice dtoDevice = new DtoDevice();
		dtoDevice.setDeviceName("uniqueDevice1");
		HttpEntity<DtoDevice> request = new HttpEntity<>(dtoDevice,headers);
		ResponseEntity<Device> result =this.restTemplate.exchange(uri,
				HttpMethod.POST,
				request,
				Device.class);

		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(201);
		try {
			Device createdDevice = deviceService.getDeviceById(result.getBody().getId());
			assertThat(createdDevice).isNotNull();
		}catch (EatonException ea){
			logger.warn(ea.getMessage());
		}
	}

	@Test
	void testGetDeviceNotExist() throws  URISyntaxException{
		final String baseUrl = "http://localhost:" + port + "/api/device/" + 100;
		URI uri = new URI(baseUrl);
		ResponseEntity<ResponseError> result = this.restTemplate.getForEntity(uri, ResponseError.class);
		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(409);
		assertThat(result.getBody().getDesc()).isEqualTo("Device not found!!");
	}

	@Test
	void testLoginDevice() throws URISyntaxException, EatonException {
		Device device = new Device();
		device.setActive(false);
		device.setName("Test2Device");

		Device createdDevice = deviceService.createDevice(device);
		final String baseUrl = "http://localhost:" + port + "/api/device/login/" + createdDevice.getId();
		URI uri = new URI(baseUrl);
		ResponseEntity<Device> result = this.restTemplate.getForEntity(uri, Device.class);
		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	void testGetDevice() throws URISyntaxException, EatonException {
		Device device = new Device();
		device.setActive(false);
		device.setName("Test3Device");

		Device createdDevice = deviceService.createDevice(device);
		final String baseUrl = "http://localhost:" + port + "/api/device/" + createdDevice.getId();
		URI uri = new URI(baseUrl);
		ResponseEntity<Device> result = this.restTemplate.getForEntity(uri, Device.class);
		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
		assertThat(result.getBody().getName()).isEqualTo("Test3Device");
	}

	@Test
	void testCreateMessageForDevice() throws URISyntaxException, EatonException {
		Device device = new Device();
		device.setActive(true);
		device.setName("Test4Device");

		Device createdDevice = deviceService.createDevice(device);
		DtoMessage dtoMessage = new DtoMessage("testTitle","TestBody");
		final String baseUrl = "http://localhost:" + port + "/api/message/"+ createdDevice.getId();
		URI uri = new URI(baseUrl);
		ResponseEntity<Device> result = this.restTemplate.postForEntity(uri,dtoMessage ,Device.class);
		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(201);
		assertThat(result.getBody().getDeviceMessageList().get(0).getMessageBody()).isEqualTo("TestBody");
		assertThat(result.getBody().getDeviceMessageList().get(0).getMessageTitle()).isEqualTo("testTitle");
	}

	@Test
	void testFailedCreateMessageForNotActiveDevice() throws URISyntaxException, EatonException {
		Device device = new Device();
		device.setActive(false);
		device.setName("Test5Device");

		Device createdDevice = deviceService.createDevice(device);
		DtoMessage dtoMessage = new DtoMessage("testTitle","TestBody");
		final String baseUrl = "http://localhost:" + port + "/api/message/"+ createdDevice.getId();
		URI uri = new URI(baseUrl);
		ResponseEntity<ResponseError> result = this.restTemplate.postForEntity(uri,dtoMessage ,ResponseError.class);
		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(409);
		assertThat(result.getBody().getDesc()).isEqualTo("Device should be active!!");
	}

	@Test
	void testGetMessageCountForDevice() throws URISyntaxException, EatonException {
		Device device = prepareMessagesForDevice();

		Device createdDevice = deviceService.createDevice(device);
		final String baseUrl = "http://localhost:" + port + "/api/message/count/" + createdDevice.getId();
		URI uri = new URI(baseUrl);
		ResponseEntity<Integer> result = this.restTemplate.getForEntity(uri, Integer.class);
		//Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
		assertThat(result.getBody()).isEqualTo(device.getDeviceMessageList().size());
	}

	private Device prepareMessagesForDevice(){
		List<DeviceMessage> deviceMessageList = new ArrayList<>();
		Device device = new Device();
		device.setActive(true);
		device.setName("Test6Device");
		DeviceMessage deviceMessage1 = new DeviceMessage("title1","body1");
		DeviceMessage deviceMessage2 = new DeviceMessage("title2","body2");
		DeviceMessage deviceMessage3 = new DeviceMessage("title3","body3");
		DeviceMessage deviceMessage4 = new DeviceMessage("title4","body4");
		deviceMessageList.add(deviceMessage1);
		deviceMessageList.add(deviceMessage2);
		deviceMessageList.add(deviceMessage3);
		deviceMessageList.add(deviceMessage4);
		device.setDeviceMessageList(deviceMessageList);
		return device;
	}

}
