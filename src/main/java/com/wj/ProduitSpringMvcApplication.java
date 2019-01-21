package com.wj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.wj.dao.ProduitRepository;
import com.wj.metier.Produit;

@SpringBootApplication
public class ProduitSpringMvcApplication {

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(ProduitSpringMvcApplication.class, args);
		
		/*
		 * Je demande à Spring de me donner un objet de type ProduitRepository
		 * afin de pouvoir gérer les produits
		*/
		
		/*ProduitRepository  produitRepository = ctx.getBean(ProduitRepository.class);
	
		produitRepository.save(new Produit("LX 4352", 670, 90));
		produitRepository.save(new Produit("Ord HP 432", 670, 90));
		produitRepository.save(new Produit("Imprimante Epson", 450, 12));
		produitRepository.save(new Produit("Imprimante HP 5400", 45, 10));
		
		produitRepository.findAll().forEach(p -> System.out.println(p.getId() + " : " +  p.getDesignation()));
*/
	}
}
