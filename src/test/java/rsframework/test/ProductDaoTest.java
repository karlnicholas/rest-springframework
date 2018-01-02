package rsframework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import rsframework.model.Product;
import rsframework.repo.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfiguration.class)
public class ProductDaoTest {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testFind() {
		Product product = productRepository.findOne(1L);
		assertNotNull(product);
		product = productRepository.findOne(2L);
		assertNotNull(product);
		product = productRepository.findOne(3L);
		assertNotNull(product);
		product = productRepository.findOne(4L);
		assertNotNull(product);
		product = productRepository.findOne(5L);
		assertNull(product);
	}

	@Test
    public void testGetCatalog()
    {
        List<Product> catalog = new ArrayList<>();
        productRepository.findAll().forEach(catalog::add);
        assertEquals(4, catalog.size());
    }
     
	@Test
	@Transactional
	public void testInsert() {

		Product product = new Product();

		product.setName("Celina do Sim");
		product.setDescription("Celina do Sim");
		product.setSku("44657688");
		product.setCategory("F");

		productRepository.save(product);
	}

	@Test
	@Transactional
	public void testUpdate() {

		Product product = productRepository.findOne(1L);
		product.setSku("44657688");
		productRepository.save(product);
	}

	@Test
	@Transactional
	public void testRemove() {
		Product product = productRepository.findOne(1L);
		productRepository.delete(product);
	}

}