
package jreactive.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import jreactive.application.WebMvcConfiguration;
import jreactive.dao.ProductDao;
import jreactive.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={WebMvcConfiguration.class})
@WebAppConfiguration
public class TestChallengeDaos
{
     
    @Autowired
    private ProductDao productDao;
     
	@Test
    public void testGetCatalog()
    {
        List<Product> catalog = new ArrayList<>();
        productDao.findAll().forEach(catalog::add);
        assertEquals(4, catalog.size());
    }
     
    @Test
    public void testGetProduct()
    {
        Product product = productDao.findOne(1L);
        assertEquals("TEST ID", new Long(1), product.getId() );
        assertEquals("TEST SKU", "111-AA", product.getSku() );
        assertEquals("TEST NAME", "Widget", product.getName() );
        assertEquals("TEST DESCRIPTION", "Cool Widget", product.getDescription() );
        assertEquals("TEST CATEGORY", "Widget", product.getCategory());
    }

}
