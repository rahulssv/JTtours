package com.jt;

import org.springframework.boot.test.context.SpringBootTest;

import com.jt.controller.AuthControllerTest;
import com.jt.controller.BatchController;
import com.jt.controller.BookingControllerTest;
import com.jt.controller.TourController;
import com.jt.service.BatchService;
import com.jt.service.BookingServiceTest;
import com.jt.service.TourService;
import com.jt.service.UserServicesTest;


@SpringBootTest(classes = 
{BatchController.class , 
TourController.class , 
BatchService.class , 
TourService.class,
AuthControllerTest.class,
BookingControllerTest.class,
BookingServiceTest.class,
UserServicesTest.class
})
class JttoursApplicationTests {
}
