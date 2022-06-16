package com.ndourcodeur.springdatamongodbexample;

import com.ndourcodeur.springdatamongodbexample.entity.Product;
import com.ndourcodeur.springdatamongodbexample.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableMongoAuditing
public class SpringDataMongodbExampleApplication {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {

		SpringApplication.run(SpringDataMongodbExampleApplication.class, args);

		System.out.print("Server started.....");
	}

	/**
	 * @NdourCodeur
	 * Method to save all products in the database.
	 * @param productRepository
	 * @return
	 */
	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository){
		return args -> {
			// Save one product by one
			productRepository.save(new Product(null, "Hp laptop", 1200.00, true, "The Description", new Date(), new Date()));
			productRepository.save(new Product(null, "Acer laptop", 1000.00, true, "The Description", new Date(), new Date()));
			productRepository.save(new Product(null, "Lenovo", 890.00, true, "The Description", new Date(), new Date()));
			// Save all products together
			Product p1 = new Product(null, "MacBook Pro", 1500.00, true, "The description", new Date(), new Date());
			Product p2 = new Product(null, "Techno Camon 18", 100.85, true, "The description", new Date(), new Date());
			Product p3 = new Product(null, "Iphone Xpro", 999.99, true, "The description", new Date(), new Date());
			productRepository.saveAll(List.of(p1, p2, p3));
			productRepository.findAll().forEach(product -> {System.out.println(product.getName());});
		};
	}

}
