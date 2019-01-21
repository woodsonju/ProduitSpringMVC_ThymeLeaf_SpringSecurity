package com.wj.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wj.dao.ProduitRepository;
import com.wj.metier.Produit;

@Controller
public class ProduitController {
	/*On demande à spring d'injecter un objet de type produit
	 *  repository. 
	 *  On demande à Spring de chercher une des classe qu'il a déjà
	 *  instancier et qui implemente cette interface, et l'injecter 
	 */
	@Autowired  
	private ProduitRepository produitRepository;
	
	/*
	 * Chaque méthode retourne le nom de la vue qu'il va afficher.
	 * 
	 */
	
	@RequestMapping(value="/user/index", method=RequestMethod.GET)
	public String index(Model model, 
						@RequestParam(name="page", defaultValue="0")int p, 
						@RequestParam(name="size", defaultValue="5")int s, 
						@RequestParam(name="mc", defaultValue="")String mc) 
	{
		//Page<Produit> pageProduits  = produitRepository.findAll(new PageRequest(p, s));
		Page<Produit> pageProduits  = produitRepository.chercher("%"+mc+"%", 
				new PageRequest(p, s));
		
		model.addAttribute("listProduits", pageProduits.getContent()); 
		
		//Recuperation du nombre de pages
		int[] pages = new int[pageProduits.getTotalPages()];
		model.addAttribute("pages", pages);
		
		//recuperation de la taille de chaque page 
		model.addAttribute("size", s);
		
		//Ajout de la page courante
		model.addAttribute("pageCourante", p);
		
		//recuperation du mot clé
		model.addAttribute("mc", mc);
		
		return "produits";    //produits represente le nom de la vue
	}
	
	@RequestMapping(value="/admin/delete", method=RequestMethod.GET)
	public String delete(Long id, String mc, int page, int size) {
		produitRepository.delete(id);
		return "redirect:/user/index?page="+page
								+"&size="+size
								+"&mc="+mc; /*On fait une redirection vers 
												index?page="+page
															+"&size="+size
															+"&mc="+mc
		 				Si on avait mis return "produits" ce serait
		 							une forward*/
	}
	
	
	@RequestMapping(value="/admin/form", method=RequestMethod.GET)
	public String formProduit(Model model) {
		model.addAttribute("produit", new Produit());
		return "formProduit";  //fait un forward vers la vue formProduit.html
	}
	
	
	@RequestMapping(value="/admin/saveProduit", method=RequestMethod.POST)
	/*	La notation @valid : quand Spring va lire ou stoquer les données dans les
	 *  entités il doit faire la validation, en verifiant si les données respectent
	 *  les paramètres definis dans les annotations(classe entité).
	 *  S'il y a des messages d'erreurs, il va les stoquer dans un 
	 *  objet BindingResult
	 *  BindingResult : C'est une collection dans laquelle il stock les erreurs
	 *					Il fait la validation, il récupère s'il y a des erreurs
	 *	S'il y  a des erreurs, on revient vers le formulaire, ici formProduit
	 *	Dans formProduit on utiliser th:errors  de thymeleaf pour afficher 
	 * 	l'erreur
	 */
	public String saveProduit(Model model, 
			@Valid Produit produit,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors())
			return "formProduit";  
		
		produitRepository.save(produit);
		model.addAttribute("produit", produit);
		
		return "confirmation"; //fait un forward vers confirmation.html
	}
	
	
	@RequestMapping(value="/admin/edit", method=RequestMethod.GET)
	public String getProduit(Model model, Long id)
	{
		Produit p = produitRepository.getOne(id);
		model.addAttribute("produit", p);
		return "editProduit";
	}
	
	/*
	 * Dans notre controleur on ajoute l'action par défaut /login
	 */
	@RequestMapping(value="/")  //Si on a juste http://localhost:8080 il nous redirige vers /index
	public String home() {
		return "redirect:/user/index";
	}
	
	@RequestMapping(value="/403")  
	public String accessDenied() {
		return "403";
	}
	
	@RequestMapping(value="/login")  
	public String login() {
		return "login";
	}
	
}
