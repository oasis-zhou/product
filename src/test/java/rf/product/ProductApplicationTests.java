package rf.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rf.product.conf.AppContext;
import rf.product.exception.GenericException;
import rf.product.local.LocalResourceBundleMessageSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {

	@Test
	public void contextLoads() {
		GenericException e = new GenericException(10001L);

		System.out.print("msg==" + e.getMessage());
	}

}
