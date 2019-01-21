package com.wj.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/*
	 * On demande à Spring d'utiliser le même datasource que celle definie 
	 * dans le application.properties
	 */
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*
		 * Avec l'objet AuthenticationManagerBuilder; dans cette methode
		 * on va specifier quels sont les utilisateurs qui ont le droit d'acceder
		 * à l'application
		 */
		
		
		/*On donne le rôle user et admin à cet utilisateur
		 * inMemoryAuthentication(): Utilisateurs sont en memoire pour le moment
		 * après on va basculer vers la base de données
		 *	withUser() : On spécifie les utilisateurs avec withUser
		 *	password(): On spécifie le mot de passe
		 *	roles() : Quels sont les rôles à cette utilisateur
		 */
	
	/*
		auth.inMemoryAuthentication().withUser("admin").password("1234").roles("USER", "ADMIN");
		
		//On donne le rôle user à cet utilisateur
		auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER");
	 */
		
		
	/*
	 * jdbcAuthentication() : Les utilisateurs seront mémorisées dans une base de données
	 * 	datasource : On demande à Spring d'utiliser le même datasource que celle 
	 *  definie dans le application.properties
	 *  Pour que Spring security sache que login répresente le Username il faut utiliser 
	 *  le mot principal,  et credentials pour le password
	 *  La methode authoritiesByUsernameQuery permet de savoir quels sont les rôles 
	 *  de l'utilisateur.
	 *  la méthode passwordEncoder() permet de coder le password en MD5
	 *  Spécifier le préfix du rôle qui vat être utiliser par spring securité;
	 *  quand il va charger un rôle il ajoutera le prefixe spécifier
	 */
		auth.jdbcAuthentication()
			.dataSource(dataSource) 
			
			//Spring va chercher l'utilisateur et le role de l'utilisateur
			.usersByUsernameQuery("select login as principal, pass as credentials, active from users where login=?")
			.authoritiesByUsernameQuery("select login as principal, role as role from users_roles where login=?")
			
			.passwordEncoder(new Md5PasswordEncoder())
			.rolePrefix("ROLE_");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * 	L'objet HttpSecurity definie les règles de securité
		 * 	formLogin(): L'operation d'authentification passe par un formulaire
		 * d'authentification.
		 * formLogin().loginPage("/login") : permet de personaliser le formulaire
		 * En appelant seulement la méthode formLogin(), Spring security crée
		 * un formulaire préConfiguré par défaut
		 */
		http.formLogin().loginPage("/login");
		
		/*	Une reqûete, un URL neccessite d'avoir un rôle pour acceder 
		*	à une ressource.
		*	Par exemple acceder à l'action, l'url /index, il néccessite juste 
		*	d'avoir le rôle USER pour s'authentifier
		*
		*/
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		/*
		 * Permet de Personaliser une page
		 * Exemple une page qui affiche :
		 * "There was an unexpected error (type=Forbidden, status=403).
		 * Access is denied"
		 * */
		http.exceptionHandling().accessDeniedPage("/403");
	}
	
	
	
}
