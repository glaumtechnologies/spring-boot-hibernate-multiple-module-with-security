package com.glaum;

import com.glaum.dao.GmanageTestDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class GmanageTestController {

    private static Logger logger = LoggerFactory.getLogger(GmanageTestController.class);

    @Autowired
    private GmanageTestDaoImpl gmanageTestDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void base(HttpServletResponse response) throws IOException {
        response.getWriter().write("Welcome to Gmanage Base");
    }

    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public String html() throws IOException {
        logger.info("Html page load working fine");
        return "test_home";
    }

    @RequestMapping(value = "/database", method = RequestMethod.GET)
    public void database(HttpServletResponse response) throws IOException {
        gmanageTestDao.updateGmanageTest();
        gmanageTestDao.getGmanageTest();
        response.getWriter().write("Database integration working fine");
    }
}
