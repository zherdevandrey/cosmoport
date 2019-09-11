package com.space.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.space.config.MyWebAppInit;
import com.space.config.WebConfig;
import com.space.controller.utils.ShipInfoTest;
import com.space.controller.utils.TestDataSourceConfig;
import com.space.repository.ShipRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataSourceConfig.class, MyWebAppInit.class, WebConfig.class})
@WebAppConfiguration
@Sql(scripts = "classpath:test.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MyTestTest {

    private WebApplicationContext context;
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    private ShipInfoTest expected;

    private ShipRepository shipRepository;

    private EntityManager entityManager;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test() throws ParseException {

        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("name","Orion III");
//        paramMap.put("planet","Mars");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

        String datebefore = "2000-01-01";
        String dateafter= "3000-01-01";
        Date before = simpleDateFormat.parse(datebefore);
        Date after = simpleDateFormat.parse(dateafter);

        paramMap.put("before",before.getTime()+"");
        paramMap.put("after",after.getTime()+"");

//        paramMap.put("isUsed", "true");
//
//        paramMap.put("minSpeed","0.8");
//        paramMap.put("maxSpeed","0.9");
//        paramMap.put("minCrewSize","600");
//        paramMap.put("maxCrewSize","700");
//        paramMap.put("minRating","1.0");
//        paramMap.put("maxRating","1.5");



        shipRepository.findShips(paramMap);
    }

    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setContext(WebApplicationContext context) {
        this.context = context;
    }
}