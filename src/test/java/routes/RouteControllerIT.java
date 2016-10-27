package routes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/direct?dep_sid=3&arr_sid=4");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<Response> response = template.getForEntity(base.toString(),
                Response.class);
        assertThat(response.getBody().getDep_sid(), equalTo(3));
        assertThat(response.getBody().getArr_sid(), equalTo(4));
        assertThat(response.getBody().isDirect_bus_route(), equalTo(false));
    }
}
